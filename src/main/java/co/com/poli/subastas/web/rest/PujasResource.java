package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.domain.Subastas;
import co.com.poli.subastas.repository.PujasRepository;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageImpl;

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

    private final PujasRepository pujasRepository;
    private final SubastasRepository subastasRepository;

    public PujasResource(SubastasRepository subastasRepository,PujasRepository pujasRepository) {
        this.pujasRepository = pujasRepository;
        this.subastasRepository = subastasRepository;
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
