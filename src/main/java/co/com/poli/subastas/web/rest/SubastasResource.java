package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Subastas;
import co.com.poli.subastas.repository.EventosRepository;
import co.com.poli.subastas.repository.SubastasRepository;
import co.com.poli.subastas.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.poli.subastas.domain.Subastas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubastasResource {

    private final Logger log = LoggerFactory.getLogger(SubastasResource.class);

    private static final String ENTITY_NAME = "subastas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubastasRepository subastasRepository;
    private final EventosRepository eventosRepository;

    public SubastasResource(SubastasRepository subastasRepository,EventosRepository eventosRepository) {
        this.subastasRepository = subastasRepository;
        this.eventosRepository = eventosRepository;
    }

    /**
     * {@code POST  /subastas} : Create a new subastas.
     *
     * @param subastas the subastas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subastas, or with status {@code 400 (Bad Request)} if the subastas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subastas")
    public ResponseEntity<Subastas> createSubastas(@Valid @RequestBody Subastas subastas) throws URISyntaxException {
        log.debug("REST request to save Subastas : {}", subastas);
        if (subastas.getId() != null) {
            throw new BadRequestAlertException("A new subastas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(subastas.getFechafinal().compareTo(subastas.getFechainicio()) > 0 ){
            throw new BadRequestAlertException("La fecha final es mayor que la fecha inicial", ENTITY_NAME, "idexists");
        }
        Subastas result = subastasRepository.save(subastas);
        return ResponseEntity.created(new URI("/api/subastas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subastas} : Updates an existing subastas.
     *
     * @param subastas the subastas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subastas,
     * or with status {@code 400 (Bad Request)} if the subastas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subastas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subastas")
    public ResponseEntity<Subastas> updateSubastas(@Valid @RequestBody Subastas subastas) throws URISyntaxException {
        log.debug("REST request to update Subastas : {}", subastas);
        if (subastas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(subastas.getFechafinal().compareTo(subastas.getFechainicio()) > 0 ){
            throw new BadRequestAlertException("La fecha final es mayor que la fecha inicial", ENTITY_NAME, "idexists");
        }
        Subastas result = subastasRepository.save(subastas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subastas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subastas} : get all the subastas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subastas in body.
     */
    @GetMapping("/subastas")
    public ResponseEntity<List<Subastas>> getAllSubastas(Pageable pageable) {
        log.debug("REST request to get a page of Subastas");
        Page<Subastas> page = subastasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

     /**
     * {@code GET  /subastas/:idEvento} : get the "idEvento" subastas.
     *
     * @param id the id of the subastas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subastas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subastas/id-evento/{idevento}")
    public ResponseEntity<List<Subastas>> getSubastasByIdEvento(@PathVariable Long idevento, Pageable pageable) {
        log.debug("REST request to get SubastasByIdEvento : {}", idevento);
        Page<Subastas> page =  subastasRepository.findByEventosAndEstadoActivoBetweenFechainicioAndFechafinal(eventosRepository.findById(idevento).get(),Boolean.TRUE,Instant.now(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /subastas/:id} : get the "id" subastas.
     *
     * @param id the id of the subastas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subastas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subastas/{id}")
    public ResponseEntity<Subastas> getSubastas(@PathVariable Long id) {
        log.debug("REST request to get Subastas : {}", id);
        Optional<Subastas> subastas = subastasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subastas);
    }

    /**
     * {@code DELETE  /subastas/:id} : delete the "id" subastas.
     *
     * @param id the id of the subastas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subastas/{id}")
    public ResponseEntity<Void> deleteSubastas(@PathVariable Long id) {
        log.debug("REST request to delete Subastas : {}", id);
        subastasRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
