package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Mensajes;
import co.com.poli.subastas.repository.MensajesRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Mensajes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MensajesResource {

    private final Logger log = LoggerFactory.getLogger(MensajesResource.class);

    private static final String ENTITY_NAME = "mensajes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensajesRepository mensajesRepository;

    public MensajesResource(MensajesRepository mensajesRepository) {
        this.mensajesRepository = mensajesRepository;
    }

    /**
     * {@code POST  /mensajes} : Create a new mensajes.
     *
     * @param mensajes the mensajes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensajes, or with status {@code 400 (Bad Request)} if the mensajes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mensajes")
    public ResponseEntity<Mensajes> createMensajes(@Valid @RequestBody Mensajes mensajes) throws URISyntaxException {
        log.debug("REST request to save Mensajes : {}", mensajes);
        if (mensajes.getId() != null) {
            throw new BadRequestAlertException("A new mensajes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mensajes result = mensajesRepository.save(mensajes);
        return ResponseEntity.created(new URI("/api/mensajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mensajes} : Updates an existing mensajes.
     *
     * @param mensajes the mensajes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajes,
     * or with status {@code 400 (Bad Request)} if the mensajes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensajes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mensajes")
    public ResponseEntity<Mensajes> updateMensajes(@Valid @RequestBody Mensajes mensajes) throws URISyntaxException {
        log.debug("REST request to update Mensajes : {}", mensajes);
        if (mensajes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mensajes result = mensajesRepository.save(mensajes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensajes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mensajes} : get all the mensajes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensajes in body.
     */
    @GetMapping("/mensajes")
    public ResponseEntity<List<Mensajes>> getAllMensajes(Pageable pageable) {
        log.debug("REST request to get a page of Mensajes");
        Page<Mensajes> page = mensajesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensajes/:id} : get the "id" mensajes.
     *
     * @param id the id of the mensajes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensajes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mensajes/{id}")
    public ResponseEntity<Mensajes> getMensajes(@PathVariable Long id) {
        log.debug("REST request to get Mensajes : {}", id);
        Optional<Mensajes> mensajes = mensajesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mensajes);
    }

    /**
     * {@code DELETE  /mensajes/:id} : delete the "id" mensajes.
     *
     * @param id the id of the mensajes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> deleteMensajes(@PathVariable Long id) {
        log.debug("REST request to delete Mensajes : {}", id);
        mensajesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
