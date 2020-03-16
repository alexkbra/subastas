package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.repository.LotesRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.poli.subastas.domain.Lotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LotesResource {

    private final Logger log = LoggerFactory.getLogger(LotesResource.class);

    private static final String ENTITY_NAME = "lotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotesRepository lotesRepository;
    private final SubastasRepository subastasRepository;

    public LotesResource(LotesRepository lotesRepository, SubastasRepository subastasRepository) {
        this.lotesRepository = lotesRepository;
        this.subastasRepository = subastasRepository;
    }

    /**
     * {@code POST  /lotes} : Create a new lotes.
     *
     * @param lotes the lotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lotes, or with status {@code 400 (Bad Request)} if the lotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lotes")
    public ResponseEntity<Lotes> createLotes(@Valid @RequestBody Lotes lotes) throws URISyntaxException {
        log.debug("REST request to save Lotes : {}", lotes);
        if (lotes.getId() != null) {
            throw new BadRequestAlertException("A new lotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lotes result = lotesRepository.save(lotes);
        return ResponseEntity.created(new URI("/api/lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lotes} : Updates an existing lotes.
     *
     * @param lotes the lotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotes,
     * or with status {@code 400 (Bad Request)} if the lotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lotes")
    public ResponseEntity<Lotes> updateLotes(@Valid @RequestBody Lotes lotes) throws URISyntaxException {
        log.debug("REST request to update Lotes : {}", lotes);
        if (lotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lotes result = lotesRepository.save(lotes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lotes} : get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotes in body.
     */
    @GetMapping("/lotes")
    public ResponseEntity<List<Lotes>> getAllLotes(Pageable pageable) {
        log.debug("REST request to get a page of Lotes");
        Page<Lotes> page = lotesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lotes} : get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotes in body.
     */
    @GetMapping("/lotes/id-subasta/{idsubasta}")
    public ResponseEntity<List<Lotes>> getLotesByIdSubastas(@PathVariable Long idsubasta,Pageable pageable) {
        log.debug("REST request to get a page of Lotes");
        Page<Lotes> page = lotesRepository.findBySubastas(subastasRepository.findById(idsubasta).get(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lotes/:id} : get the "id" lotes.
     *
     * @param id the id of the lotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lotes/{id}")
    public ResponseEntity<Lotes> getLotes(@PathVariable Long id) {
        log.debug("REST request to get Lotes : {}", id);
        Optional<Lotes> lotes = lotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lotes);
    }

    /**
     * {@code DELETE  /lotes/:id} : delete the "id" lotes.
     *
     * @param id the id of the lotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lotes/{id}")
    public ResponseEntity<Void> deleteLotes(@PathVariable Long id) {
        log.debug("REST request to delete Lotes : {}", id);
        lotesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
