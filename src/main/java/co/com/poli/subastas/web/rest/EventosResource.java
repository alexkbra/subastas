package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Eventos;
import co.com.poli.subastas.repository.EventosRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Eventos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EventosResource {

    private final Logger log = LoggerFactory.getLogger(EventosResource.class);

    private static final String ENTITY_NAME = "eventos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventosRepository eventosRepository;

    public EventosResource(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    /**
     * {@code POST  /eventos} : Create a new eventos.
     *
     * @param eventos the eventos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventos, or with status {@code 400 (Bad Request)} if the eventos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eventos")
    public ResponseEntity<Eventos> createEventos(@Valid @RequestBody Eventos eventos) throws URISyntaxException {
        log.debug("REST request to save Eventos : {}", eventos);
        if (eventos.getId() != null) {
            throw new BadRequestAlertException("A new eventos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Eventos result = eventosRepository.save(eventos);
        return ResponseEntity.created(new URI("/api/eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eventos} : Updates an existing eventos.
     *
     * @param eventos the eventos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventos,
     * or with status {@code 400 (Bad Request)} if the eventos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eventos")
    public ResponseEntity<Eventos> updateEventos(@Valid @RequestBody Eventos eventos) throws URISyntaxException {
        log.debug("REST request to update Eventos : {}", eventos);
        if (eventos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Eventos result = eventosRepository.save(eventos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eventos} : get all the eventos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventos in body.
     */
    @GetMapping("/eventos")
    public ResponseEntity<List<Eventos>> getAllEventos(Pageable pageable) {
        log.debug("REST request to get a page of Eventos");
        Page<Eventos> page = eventosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eventos/:id} : get the "id" eventos.
     *
     * @param id the id of the eventos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eventos/{id}")
    public ResponseEntity<Eventos> getEventos(@PathVariable Long id) {
        log.debug("REST request to get Eventos : {}", id);
        Optional<Eventos> eventos = eventosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eventos);
    }

    /**
     * {@code DELETE  /eventos/:id} : delete the "id" eventos.
     *
     * @param id the id of the eventos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> deleteEventos(@PathVariable Long id) {
        log.debug("REST request to delete Eventos : {}", id);
        eventosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
