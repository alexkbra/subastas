package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Dispositivo;
import co.com.poli.subastas.repository.DispositivoRepository;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.poli.subastas.domain.Dispositivo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DispositivoResource {

    private final Logger log = LoggerFactory.getLogger(DispositivoResource.class);

    private static final String ENTITY_NAME = "dispositivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositivoRepository dispositivoRepository;

    public DispositivoResource(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    /**
     * {@code POST  /dispositivos} : Create a new dispositivo.
     *
     * @param dispositivo the dispositivo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositivo, or with status {@code 400 (Bad Request)} if the dispositivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispositivos")
    public ResponseEntity<Dispositivo> createDispositivo(@RequestBody Dispositivo dispositivo) throws URISyntaxException {
        log.debug("REST request to save Dispositivo : {}", dispositivo);
        if (dispositivo.getId() != null) {
            throw new BadRequestAlertException("A new dispositivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dispositivo result = dispositivoRepository.save(dispositivo);
        return ResponseEntity.created(new URI("/api/dispositivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispositivos} : Updates an existing dispositivo.
     *
     * @param dispositivo the dispositivo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositivo,
     * or with status {@code 400 (Bad Request)} if the dispositivo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositivo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispositivos")
    public ResponseEntity<Dispositivo> updateDispositivo(@RequestBody Dispositivo dispositivo) throws URISyntaxException {
        log.debug("REST request to update Dispositivo : {}", dispositivo);
        if (dispositivo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dispositivo result = dispositivoRepository.save(dispositivo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositivo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dispositivos} : get all the dispositivos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositivos in body.
     */
    @GetMapping("/dispositivos")
    public ResponseEntity<List<Dispositivo>> getAllDispositivos(Pageable pageable) {
        log.debug("REST request to get a page of Dispositivos");
        Page<Dispositivo> page = dispositivoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispositivos/:id} : get the "id" dispositivo.
     *
     * @param id the id of the dispositivo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositivo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispositivos/{id}")
    public ResponseEntity<Dispositivo> getDispositivo(@PathVariable Long id) {
        log.debug("REST request to get Dispositivo : {}", id);
        Optional<Dispositivo> dispositivo = dispositivoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dispositivo);
    }

    /**
     * {@code DELETE  /dispositivos/:id} : delete the "id" dispositivo.
     *
     * @param id the id of the dispositivo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispositivos/{id}")
    public ResponseEntity<Void> deleteDispositivo(@PathVariable Long id) {
        log.debug("REST request to delete Dispositivo : {}", id);
        dispositivoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
