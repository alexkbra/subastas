/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.poli.subastas.runnable;

import co.com.poli.subastas.domain.Dispositivo;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.repository.ClienteRepository;
import co.com.poli.subastas.repository.DispositivoRepository;
import co.com.poli.subastas.repository.LotesRepository;
import co.com.poli.subastas.repository.PujadoresRepository;
import co.com.poli.subastas.repository.PujasRepository;
import co.com.poli.subastas.repository.SubastasRepository;
import co.com.poli.subastas.repository.UserRepository;
import co.com.poli.subastas.runnable.dto.Data;
import co.com.poli.subastas.runnable.dto.Notification;
import co.com.poli.subastas.runnable.dto.NotificationMainRequest;
import co.com.poli.subastas.runnable.dto.NotificationMainResponse;
import co.com.poli.subastas.security.jwt.TokenProvider;
import co.com.poli.subastas.web.websocket.dto.PujasDTO;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 57304
 */
public class NotificarPuja implements Runnable {

    private final PujasRepository pujasRepository;
    private final SubastasRepository subastasRepository;
    private final LotesRepository lotesRepository;
    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final PujadoresRepository pujadoresRepository;
    private final TokenProvider tokenProvider;
    private final DispositivoRepository dispositivoRepository;
    private Pujas puja;
    private final RestTemplate restTemplate;

    public NotificarPuja(SubastasRepository subastasRepository, PujasRepository pujasRepository, UserRepository userRepository, ClienteRepository clienteRepository, PujadoresRepository pujadoresRepository,
            TokenProvider tokenProvider, DispositivoRepository dispositivoRepository, RestTemplate restTemplate, Pujas pujaNew,LotesRepository lotesRepository) {
        this.pujasRepository = pujasRepository;
        this.subastasRepository = subastasRepository;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.pujadoresRepository = pujadoresRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.restTemplate = restTemplate;
        this.puja = pujaNew;
        this.lotesRepository = lotesRepository;
    }

    @Override
    public void run() {
        List<Dispositivo> dispositivos = dispositivoRepository.findByIdEventoAndIdSubastaAndIdLote(this.puja.getIdEvento(), this.puja.getIdSubasta(), this.puja.getIdLote());
        List<Pujas> pujass = pujasRepository.findByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(puja.getIdEvento(), puja.getIdSubasta(), puja.getIdLote());
        System.out.println(pujass.toString());
        System.out.println("=================================== ");
        System.out.println("=================================== ");
        System.out.println("=================================== ");
        if(!pujass.isEmpty()){
            System.out.println("pujass.get(0).getValor().doubleValue() --> "+pujass.get(0).getValor().doubleValue());
            System.out.println("this.puja.getValor().doubleValue() --> "+this.puja.getValor().doubleValue());
            if(pujass.get(0).getValor().doubleValue() > this.puja.getValor().doubleValue()){
                this.puja = pujass.get(0);
            }
        }
        System.out.println("=================================== ");
        System.out.println("=================================== ");
        System.out.println("=================================== ");
        for (Dispositivo dispositivo : dispositivos) {
            // create headers
            HttpHeaders headers = new HttpHeaders();
            // set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
            // set `accept` header
            //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            //headers.add("Authorization", "key=AAAAgpow8YI:APA91bFPtMCO3YZ5H44OZX3mSflX3mrKeb5o92SX0GywpTF_cslFI-lbq3yjnnxEwsEXBgT24lpCm-K_KafEkpCm7sklyxfAKy1NOrCkk590uExaB97tc6h08OuP-opWRvQZFB4OQL2o");
            headers.set("Authorization", "key=AAAAgpow8YI:APA91bFPtMCO3YZ5H44OZX3mSflX3mrKeb5o92SX0GywpTF_cslFI-lbq3yjnnxEwsEXBgT24lpCm-K_KafEkpCm7sklyxfAKy1NOrCkk590uExaB97tc6h08OuP-opWRvQZFB4OQL2o");
            // request body parameters
            NotificationMainRequest request = new NotificationMainRequest();
            
            request.setNotification(new Notification("Subasta", "Puja realizada al lote: "+this.lotesRepository.findById(Long.parseLong(puja.getIdLote())).get().getNombre() +" por valor de: "+this.puja.getValor().toString()));
            request.setPriority("high");
            Data data = new Data();
            data.setClickAction("FLUTTER_NOTIFICATION_CLICK");
            data.setId("1");
            data.setStatus("done");
            data.setPuja(this.puja.getValor().toString());
            data.setIdEventos(this.puja.getIdEvento());
            data.setIdSubastas(this.puja.getIdSubasta());
            data.setIdLotes(this.puja.getIdLote());
            request.setData(data);
            request.setTo(dispositivo.getDispositivo());

            // build the request
            HttpEntity<NotificationMainRequest> entity = new HttpEntity<>(request, headers);
            
            // send POST request
            ResponseEntity<NotificationMainResponse> response = restTemplate.postForEntity("https://fcm.googleapis.com/fcm/send", entity, NotificationMainResponse.class);
            
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Mensaje enviado!!");
                System.out.println(response.getBody());
            } else {
                System.out.println("Mensaje Failed!!!");
                System.out.println(response.getStatusCode());
            }

        }
    }

}
