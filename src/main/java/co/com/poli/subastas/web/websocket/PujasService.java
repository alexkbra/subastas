/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.poli.subastas.web.websocket;

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

/**
 *
 * @author 57304
 */
@Controller
public class PujasService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(PujasService.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final PujasRepository pujasRepository;
    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final PujadoresRepository pujadoresRepository;
    private final TokenProvider tokenProvider;

    public PujasService(SimpMessageSendingOperations messagingTemplate, PujasRepository pujasRepository,
            UserRepository userRepository, ClienteRepository clienteRepository, PujadoresRepository pujadoresRepository,
            TokenProvider tokenProvider) {
        this.messagingTemplate = messagingTemplate;
        this.pujasRepository = pujasRepository;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.pujadoresRepository = pujadoresRepository;
    }

    @MessageMapping("/topic/pujas")
    @SendTo("/topic/puja")
    public List<Pujas> sendPuja(@Payload PujasDTO puja, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        
        return null;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        PujasDTO pujas = new PujasDTO();
        pujas.setSessionId(event.getSessionId());
        messagingTemplate.convertAndSend("/topic/puja", pujas);
    }
}
