package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Cliente;
import co.com.poli.subastas.domain.Dispositivo;
import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.domain.Subastas;
import org.springframework.security.core.userdetails.User;
import co.com.poli.subastas.domain.enumeration.EstadoPujadores;
import co.com.poli.subastas.repository.ClienteRepository;
import co.com.poli.subastas.repository.DispositivoRepository;
import co.com.poli.subastas.repository.LotesRepository;
import co.com.poli.subastas.repository.PujadoresRepository;
import co.com.poli.subastas.repository.PujasRepository;
import co.com.poli.subastas.repository.SubastasRepository;
import co.com.poli.subastas.repository.UserRepository;
import co.com.poli.subastas.runnable.NotificarPuja;
import co.com.poli.subastas.security.jwt.TokenProvider;
import co.com.poli.subastas.web.rest.errors.BadRequestAlertException;
import co.com.poli.subastas.web.websocket.dto.PujasDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

/**
 * REST controller for managing {@link co.com.poli.subastas.domain.Pujas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PujasResource {

    private final Logger log = LoggerFactory.getLogger(PujasResource.class);

    private static final String ENTITY_NAME = "pujas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private RestTemplate restTemplate;
    
    private final PujasRepository pujasRepository;
    private final SubastasRepository subastasRepository;
    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final PujadoresRepository pujadoresRepository;
    private final TokenProvider tokenProvider;
    private final DispositivoRepository dispositivoRepository;
    private final LotesRepository lotesRepository;
    

    public PujasResource(SubastasRepository subastasRepository,PujasRepository pujasRepository,UserRepository userRepository, ClienteRepository clienteRepository, PujadoresRepository pujadoresRepository,
            TokenProvider tokenProvider,DispositivoRepository dispositivoRepository, RestTemplate restTemplate,LotesRepository lotesRepository) {
        this.pujasRepository = pujasRepository;
        this.subastasRepository = subastasRepository;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.pujadoresRepository = pujadoresRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.restTemplate = restTemplate;
        this.lotesRepository = lotesRepository;
    }

    /**
     * {@code POST  /pujas} : Create a new pujas.
     *
     * @param pujas the pujas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pujas, or with status {@code 400 (Bad Request)} if the pujas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pujas")
    public ResponseEntity<Pujas> createPujas(@Valid @RequestBody Pujas pujas) throws URISyntaxException {
        log.debug("REST request to save Reporte lotes vendidos : {}", pujas);
        if (pujas.getId() != null) {
            throw new BadRequestAlertException("A new pujas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pujas result = pujasRepository.save(pujas);
        return ResponseEntity.created(new URI("/api/pujas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    /**
     * {@code POST  /pujas} : Create a new pujas.
     *
     * @param pujas the pujas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pujas, or with status {@code 400 (Bad Request)} if the pujas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pujar")
    public ResponseEntity<PujasDTO> pujar(@Valid @RequestBody PujasDTO puja) throws URISyntaxException {
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("================= "+puja.toString());
        
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        
        if (determinarSiPuedeLaPUja(puja) ) {
            Authentication auth = tokenProvider.getAuthentication(puja.getToken());
            User user = (User) auth.getPrincipal();

            Cliente cliente = obtenerCliente(user);
            List<Pujadores> pujadoresOld = pujadoresRepository.findByIdEventoAndIdSubastaAndIdLoteAndCliente(
                    puja.getIdEventos(), puja.getIdSubasta(), puja.getIdLote(), cliente);
            
            List<Dispositivo> dispositivoOld = dispositivoRepository.findByIdEventoAndIdSubastaAndIdLoteAndDispositivo(puja.getIdEventos(),puja.getIdSubasta(),puja.getIdLote(), puja.getDispositivoId());
            
            Pujadores pujadoresNew;
            pujadoresNew = determinarNuevoPujador(pujadoresOld, puja, cliente);
            agregarDispositivo(dispositivoOld, puja, user);
            Pujas pujaNew = realizarPuja(puja, pujadoresNew);
            
            NotificarPuja notificar = new NotificarPuja( subastasRepository, pujasRepository, userRepository,  clienteRepository,  pujadoresRepository,
            tokenProvider, dispositivoRepository, restTemplate, pujaNew,lotesRepository);
            new Thread(notificar, "Notificando: ").start();
            log.debug("================== ==========================================");
            log.debug("================== ==========================================");
            log.debug("================== ==========================================");
            log.debug("================== ==========================================");
            
        }
        
        return ResponseEntity.created(new URI("/api/pujas/" + puja.getToken()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, puja.getToken()))
            .body(puja);
    }

    private boolean determinarSiPuedeLaPUja(PujasDTO puja) throws NumberFormatException {
        return puja.getValor() != null && !puja.getValor().isEmpty() && 
                this.subastasRepository.findById(Long.parseLong(puja.getIdSubasta())).get().getFechafinal().compareTo(Instant.now()) > 0;
    }

    private Cliente obtenerCliente(User user) {
        co.com.poli.subastas.domain.User userLogin = userRepository.findOneWithAuthoritiesByLogin(user.getUsername()).get();
        Cliente cliente = clienteRepository.findByIdusuario(user.getUsername()).get(0);
        System.out.println("======================="+user.getUsername());
        System.out.println("=============================================================");
        return cliente;
    }

    private Pujas realizarPuja(PujasDTO puja, Pujadores pujadoresNew) {
        Pujas pujaNew = new Pujas();
        pujaNew.setIdEvento(puja.getIdEventos());
        pujaNew.setIdSubasta(puja.getIdSubasta());
        pujaNew.setIdLote(puja.getIdLote());
        pujaNew.setValor(new BigDecimal(puja.getValor()));
        pujaNew.setFechacreacion(Instant.now());
        pujaNew.setAceptadoGanador(false);
        pujaNew.setPujadores(pujadoresNew);
        pujasRepository.save(pujaNew);
        return pujaNew;
    }

    private Pujadores determinarNuevoPujador(List<Pujadores> pujadoresOld, PujasDTO puja, Cliente cliente) {
        Pujadores pujadoresNew;
        if (pujadoresOld.isEmpty()) {
            pujadoresNew = new Pujadores();
            pujadoresNew.setIdEvento(puja.getIdEventos());
            pujadoresNew.setIdSubasta(puja.getIdSubasta());
            pujadoresNew.setIdLote(puja.getIdLote());
            pujadoresNew.setEstado(EstadoPujadores.ACTIVO);
            pujadoresNew.setPagoAceptado(false);
            pujadoresNew.setCliente(cliente);
            pujadoresRepository.save(pujadoresNew);
        } else {
            pujadoresNew = pujadoresOld.get(0);
        }
        return pujadoresNew;
    }

    private void agregarDispositivo(List<Dispositivo> dispositivoOld, PujasDTO puja, User user) {
        if(dispositivoOld.isEmpty()){
            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setIdEvento(puja.getIdEventos());
            dispositivo.setIdSubasta(puja.getIdSubasta());
            dispositivo.setIdLote(puja.getIdLote());
            dispositivo.activo(Boolean.TRUE);
            dispositivo.setIdcliente(user.getUsername());
            dispositivo.dispositivo(puja.getDispositivoId()); 
            dispositivoRepository.save(dispositivo);
        }
    }
    
    /**
     * {@code POST  /pujas} : Create a new pujas.
     *
     * @param pujas the pujas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pujas, or with status {@code 400 (Bad Request)} if the pujas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pujarInit")
    public ResponseEntity<String> pujarInit(@Valid @RequestBody PujasDTO puja) throws URISyntaxException {
        String result = "0.0";
        List<Pujas> pujass = pujasRepository.findByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(puja.getIdEventos(), puja.getIdSubasta(), puja.getIdLote());
        if(!pujass.isEmpty()){
            result = pujass.get(0).getValor().setScale(0).toString();
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, puja.getToken()))
            .body(result);
    }

    /**
     * {@code PUT  /pujas} : Updates an existing pujas.
     *
     * @param pujas the pujas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pujas,
     * or with status {@code 400 (Bad Request)} if the pujas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pujas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pujas")
    public ResponseEntity<Pujas> updatePujas(@Valid @RequestBody Pujas pujas) throws URISyntaxException {
        log.debug("REST request to update Pujas : {}", pujas);
        if (pujas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pujas result = pujasRepository.save(pujas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pujas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pujas} : get all the pujas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pujas in body.
     */
    @GetMapping("/pujas")
    public ResponseEntity<List<Pujas>> getAllPujas(Pageable pageable) {
        log.debug("REST request to get a page of Pujas");
        List<Subastas> listSubastas = this.subastasRepository.findByEstadoActivo(Boolean.FALSE);
        List<Pujas> listPujas = new ArrayList<>();
        for(Subastas subasta :listSubastas){
            for(Lotes lotes : subasta.getLotes()){
                listPujas.addAll(this.pujasRepository.findByIdLote(lotes.getId().toString()));
            }
        }   
        
        
        Page<Pujas> page = new PageImpl<>(listPujas, pageable, listPujas.size());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pujas/:id} : get the "id" pujas.
     *
     * @param id the id of the pujas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pujas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pujas/{id}")
    public ResponseEntity<Pujas> getPujas(@PathVariable Long id) {
        log.debug("REST request to get Pujas : {}", id);
        Optional<Pujas> pujas = pujasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pujas);
    }

    /**
     * {@code DELETE  /pujas/:id} : delete the "id" pujas.
     *
     * @param id the id of the pujas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pujas/{id}")
    public ResponseEntity<Void> deletePujas(@PathVariable Long id) {
        log.debug("REST request to delete Pujas : {}", id);
        pujasRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
