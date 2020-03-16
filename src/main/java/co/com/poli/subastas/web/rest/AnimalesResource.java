package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Animales;
import co.com.poli.subastas.repository.AnimalesRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Animales}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnimalesResource {

    private final Logger log = LoggerFactory.getLogger(AnimalesResource.class);

    private static final String ENTITY_NAME = "animales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalesRepository animalesRepository;

    public AnimalesResource(AnimalesRepository animalesRepository) {
        this.animalesRepository = animalesRepository;
    }

    /**
     * {@code POST  /animales} : Create a new animales.
     *
     * @param animales the animales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animales, or with status {@code 400 (Bad Request)} if the animales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animales")
    public ResponseEntity<Animales> createAnimales(@Valid @RequestBody Animales animales) throws URISyntaxException {
        log.debug("REST request to save Animales : {}", animales);
        if (animales.getId() != null) {
            throw new BadRequestAlertException("A new animales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Animales result = animalesRepository.save(animales);
        return ResponseEntity.created(new URI("/api/animales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animales} : Updates an existing animales.
     *
     * @param animales the animales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animales,
     * or with status {@code 400 (Bad Request)} if the animales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animales")
    public ResponseEntity<Animales> updateAnimales(@Valid @RequestBody Animales animales) throws URISyntaxException {
        log.debug("REST request to update Animales : {}", animales);
        if (animales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Animales result = animalesRepository.save(animales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animales} : get all the animales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animales in body.
     */
    @GetMapping("/animales")
    public ResponseEntity<List<Animales>> getAllAnimales(Pageable pageable) {
        log.debug("REST request to get a page of Animales");
        Page<Animales> page = animalesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /animales/:id} : get the "id" animales.
     *
     * @param id the id of the animales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animales/{id}")
    public ResponseEntity<Animales> getAnimales(@PathVariable Long id) {
        log.debug("REST request to get Animales : {}", id);
        Optional<Animales> animales = animalesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(animales);
    }

    /**
     * {@code DELETE  /animales/:id} : delete the "id" animales.
     *
     * @param id the id of the animales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animales/{id}")
    public ResponseEntity<Void> deleteAnimales(@PathVariable Long id) {
        log.debug("REST request to delete Animales : {}", id);
        animalesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
