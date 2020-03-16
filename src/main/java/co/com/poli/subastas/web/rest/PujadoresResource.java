package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.repository.PujadoresRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.poli.subastas.domain.Pujadores}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PujadoresResource {

    private final Logger log = LoggerFactory.getLogger(PujadoresResource.class);

    private static final String ENTITY_NAME = "pujadores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PujadoresRepository pujadoresRepository;

    public PujadoresResource(PujadoresRepository pujadoresRepository) {
        this.pujadoresRepository = pujadoresRepository;
    }

    /**
     * {@code POST  /pujadores} : Create a new pujadores.
     *
     * @param pujadores the pujadores to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pujadores, or with status {@code 400 (Bad Request)} if the pujadores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pujadores")
    public ResponseEntity<Pujadores> createPujadores(@Valid @RequestBody Pujadores pujadores) throws URISyntaxException {
        log.debug("REST request to save Pujadores : {}", pujadores);
        if (pujadores.getId() != null) {
            throw new BadRequestAlertException("A new pujadores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pujadores result = pujadoresRepository.save(pujadores);
        return ResponseEntity.created(new URI("/api/pujadores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pujadores} : Updates an existing pujadores.
     *
     * @param pujadores the pujadores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pujadores,
     * or with status {@code 400 (Bad Request)} if the pujadores is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pujadores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pujadores")
    public ResponseEntity<Pujadores> updatePujadores(@Valid @RequestBody Pujadores pujadores) throws URISyntaxException {
        log.debug("REST request to update Pujadores : {}", pujadores);
        if (pujadores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pujadores result = pujadoresRepository.save(pujadores);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pujadores.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pujadores} : get all the pujadores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pujadores in body.
     */
    @GetMapping("/pujadores")
    public ResponseEntity<List<Pujadores>> getAllPujadores(Pageable pageable) {
        log.debug("REST request to get a page of Pujadores");
        Page<Pujadores> page = pujadoresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pujadores/:id} : get the "id" pujadores.
     *
     * @param id the id of the pujadores to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pujadores, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pujadores/{id}")
    public ResponseEntity<Pujadores> getPujadores(@PathVariable Long id) {
        log.debug("REST request to get Pujadores : {}", id);
        Optional<Pujadores> pujadores = pujadoresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pujadores);
    }

    /**
     * {@code DELETE  /pujadores/:id} : delete the "id" pujadores.
     *
     * @param id the id of the pujadores to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pujadores/{id}")
    public ResponseEntity<Void> deletePujadores(@PathVariable Long id) {
        log.debug("REST request to delete Pujadores : {}", id);
        pujadoresRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
