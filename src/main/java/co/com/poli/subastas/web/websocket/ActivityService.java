package co.com.poli.subastas.web.websocket;

import static co.com.poli.subastas.config.WebsocketConfiguration.IP_ADDRESS;

import co.com.poli.subastas.domain.Cliente;
import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.domain.User;
import co.com.poli.subastas.domain.enumeration.EstadoPujadores;
import co.com.poli.subastas.repository.ClienteRepository;
import co.com.poli.subastas.repository.PujadoresRepository;
import co.com.poli.subastas.repository.PujasRepository;
import co.com.poli.subastas.repository.UserRepository;
import co.com.poli.subastas.security.jwt.TokenProvider;
import co.com.poli.subastas.web.websocket.dto.ActivityDTO;
import co.com.poli.subastas.web.websocket.dto.PujasDTO;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class ActivityService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final PujasRepository pujasRepository;
    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final PujadoresRepository pujadoresRepository;
    private final TokenProvider tokenProvider;

    public ActivityService(SimpMessageSendingOperations messagingTemplate, PujasRepository pujasRepository,
    UserRepository userRepository, ClienteRepository clienteRepository, PujadoresRepository pujadoresRepository,
    TokenProvider tokenProvider) {
        this.messagingTemplate = messagingTemplate;
        this.pujasRepository = pujasRepository;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.pujadoresRepository = pujadoresRepository;
    }

    @MessageMapping("/topic/activity")
    @SendTo("/topic/tracker")
    public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        activityDTO.setUserLogin(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        activityDTO.setTime(Instant.now());
        log.debug("Sending user tracking data {}", activityDTO);
        return activityDTO;
    }

    @MessageMapping("/topic/pujas")
    @SendTo("/topic/puja")
    public List<Pujas> sendPuja(@Payload PujasDTO puja, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        List<Pujas> result = new ArrayList<Pujas>();
        List<Pujas> listPujas = pujasRepository.findByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(puja.getIdEvento(),
                puja.getIdSubasta(), puja.getIdLote());
        result.add(listPujas.get(0));
        if (puja.getValor() != null && !puja.getValor().isEmpty()) {
            Authentication auth = tokenProvider.getAuthentication(puja.getToken());
            User user = (User) auth.getPrincipal();

            co.com.poli.subastas.domain.User userLogin = userRepository.findOneWithAuthoritiesByLogin(user.getLogin()).get();
            Cliente cliente = clienteRepository.findByIdusuario(userLogin.getId().toString()).get(0);
            Pujadores pujadoresOld = pujadoresRepository.findByIdEventoAndIdSubastaAndIdLoteAndCliente(
                    puja.getIdEvento(), puja.getIdSubasta(), puja.getIdLote(), cliente).get(0);
            Pujadores pujadoresNew = null;
            if (pujadoresOld == null) {
                pujadoresNew = new Pujadores();
                pujadoresNew.setIdEvento(puja.getIdEvento());
                pujadoresNew.setIdSubasta(puja.getIdSubasta());
                pujadoresNew.setIdLote(puja.getIdLote());
                pujadoresNew.setEstado(EstadoPujadores.NOAUTORIZADO);
                pujadoresNew.setPagoAceptado(false);
                pujadoresNew.setCliente(cliente);
                pujadoresRepository.save(pujadoresNew);
            } else {
                pujadoresNew = pujadoresOld;
            }
            Pujas pujaNew = new Pujas();
            pujaNew.setIdEvento(puja.getIdEvento());
            pujaNew.setIdSubasta(puja.getIdSubasta());
            pujaNew.setIdLote(puja.getIdLote());
            pujaNew.setValor(new BigDecimal(puja.getValor()));
            pujaNew.setFechacreacion(Instant.now());
            pujaNew.setAceptadoGanador(false);
            pujaNew.setPujadores(pujadoresNew);
            pujasRepository.save(pujaNew);
            result.add(pujaNew);
        }
        return result;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSessionId(event.getSessionId());
        activityDTO.setPage("logout");
        messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
    }
}
