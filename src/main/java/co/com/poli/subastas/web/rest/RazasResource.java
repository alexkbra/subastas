package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Razas;
import co.com.poli.subastas.repository.RazasRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Razas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RazasResource {

    private final Logger log = LoggerFactory.getLogger(RazasResource.class);

    private static final String ENTITY_NAME = "razas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RazasRepository razasRepository;

    public RazasResource(RazasRepository razasRepository) {
        this.razasRepository = razasRepository;
    }

    /**
     * {@code POST  /razas} : Create a new razas.
     *
     * @param razas the razas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new razas, or with status {@code 400 (Bad Request)} if the razas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/razas")
    public ResponseEntity<Razas> createRazas(@Valid @RequestBody Razas razas) throws URISyntaxException {
        log.debug("REST request to save Razas : {}", razas);
        if (razas.getId() != null) {
            throw new BadRequestAlertException("A new razas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Razas result = razasRepository.save(razas);
        return ResponseEntity.created(new URI("/api/razas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /razas} : Updates an existing razas.
     *
     * @param razas the razas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated razas,
     * or with status {@code 400 (Bad Request)} if the razas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the razas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/razas")
    public ResponseEntity<Razas> updateRazas(@Valid @RequestBody Razas razas) throws URISyntaxException {
        log.debug("REST request to update Razas : {}", razas);
        if (razas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Razas result = razasRepository.save(razas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, razas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /razas} : get all the razas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of razas in body.
     */
    @GetMapping("/razas")
    public ResponseEntity<List<Razas>> getAllRazas(Pageable pageable) {
        log.debug("REST request to get a page of Razas");
        Page<Razas> page = razasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /razas/:id} : get the "id" razas.
     *
     * @param id the id of the razas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the razas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/razas/{id}")
    public ResponseEntity<Razas> getRazas(@PathVariable Long id) {
        log.debug("REST request to get Razas : {}", id);
        Optional<Razas> razas = razasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(razas);
    }

    /**
     * {@code DELETE  /razas/:id} : delete the "id" razas.
     *
     * @param id the id of the razas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/razas/{id}")
    public ResponseEntity<Void> deleteRazas(@PathVariable Long id) {
        log.debug("REST request to delete Razas : {}", id);
        razasRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
