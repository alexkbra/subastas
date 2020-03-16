package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.ClasificacionLote;
import co.com.poli.subastas.repository.ClasificacionLoteRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.ClasificacionLote}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClasificacionLoteResource {

    private final Logger log = LoggerFactory.getLogger(ClasificacionLoteResource.class);

    private static final String ENTITY_NAME = "clasificacionLote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasificacionLoteRepository clasificacionLoteRepository;

    public ClasificacionLoteResource(ClasificacionLoteRepository clasificacionLoteRepository) {
        this.clasificacionLoteRepository = clasificacionLoteRepository;
    }

    /**
     * {@code POST  /clasificacion-lotes} : Create a new clasificacionLote.
     *
     * @param clasificacionLote the clasificacionLote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clasificacionLote, or with status {@code 400 (Bad Request)} if the clasificacionLote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clasificacion-lotes")
    public ResponseEntity<ClasificacionLote> createClasificacionLote(@Valid @RequestBody ClasificacionLote clasificacionLote) throws URISyntaxException {
        log.debug("REST request to save ClasificacionLote : {}", clasificacionLote);
        if (clasificacionLote.getId() != null) {
            throw new BadRequestAlertException("A new clasificacionLote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasificacionLote result = clasificacionLoteRepository.save(clasificacionLote);
        return ResponseEntity.created(new URI("/api/clasificacion-lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clasificacion-lotes} : Updates an existing clasificacionLote.
     *
     * @param clasificacionLote the clasificacionLote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificacionLote,
     * or with status {@code 400 (Bad Request)} if the clasificacionLote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clasificacionLote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clasificacion-lotes")
    public ResponseEntity<ClasificacionLote> updateClasificacionLote(@Valid @RequestBody ClasificacionLote clasificacionLote) throws URISyntaxException {
        log.debug("REST request to update ClasificacionLote : {}", clasificacionLote);
        if (clasificacionLote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClasificacionLote result = clasificacionLoteRepository.save(clasificacionLote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clasificacionLote.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clasificacion-lotes} : get all the clasificacionLotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clasificacionLotes in body.
     */
    @GetMapping("/clasificacion-lotes")
    public ResponseEntity<List<ClasificacionLote>> getAllClasificacionLotes(Pageable pageable) {
        log.debug("REST request to get a page of ClasificacionLotes");
        Page<ClasificacionLote> page = clasificacionLoteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clasificacion-lotes/:id} : get the "id" clasificacionLote.
     *
     * @param id the id of the clasificacionLote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clasificacionLote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clasificacion-lotes/{id}")
    public ResponseEntity<ClasificacionLote> getClasificacionLote(@PathVariable Long id) {
        log.debug("REST request to get ClasificacionLote : {}", id);
        Optional<ClasificacionLote> clasificacionLote = clasificacionLoteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clasificacionLote);
    }

    /**
     * {@code DELETE  /clasificacion-lotes/:id} : delete the "id" clasificacionLote.
     *
     * @param id the id of the clasificacionLote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clasificacion-lotes/{id}")
    public ResponseEntity<Void> deleteClasificacionLote(@PathVariable Long id) {
        log.debug("REST request to delete ClasificacionLote : {}", id);
        clasificacionLoteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
