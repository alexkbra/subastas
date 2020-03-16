package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Municipios;
import co.com.poli.subastas.repository.MunicipiosRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Municipios}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MunicipiosResource {

    private final Logger log = LoggerFactory.getLogger(MunicipiosResource.class);

    private static final String ENTITY_NAME = "municipios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MunicipiosRepository municipiosRepository;

    public MunicipiosResource(MunicipiosRepository municipiosRepository) {
        this.municipiosRepository = municipiosRepository;
    }

    /**
     * {@code POST  /municipios} : Create a new municipios.
     *
     * @param municipios the municipios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new municipios, or with status {@code 400 (Bad Request)} if the municipios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/municipios")
    public ResponseEntity<Municipios> createMunicipios(@Valid @RequestBody Municipios municipios) throws URISyntaxException {
        log.debug("REST request to save Municipios : {}", municipios);
        if (municipios.getId() != null) {
            throw new BadRequestAlertException("A new municipios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Municipios result = municipiosRepository.save(municipios);
        return ResponseEntity.created(new URI("/api/municipios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /municipios} : Updates an existing municipios.
     *
     * @param municipios the municipios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated municipios,
     * or with status {@code 400 (Bad Request)} if the municipios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the municipios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/municipios")
    public ResponseEntity<Municipios> updateMunicipios(@Valid @RequestBody Municipios municipios) throws URISyntaxException {
        log.debug("REST request to update Municipios : {}", municipios);
        if (municipios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Municipios result = municipiosRepository.save(municipios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, municipios.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /municipios} : get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of municipios in body.
     */
    @GetMapping("/municipios")
    public ResponseEntity<List<Municipios>> getAllMunicipios(Pageable pageable) {
        log.debug("REST request to get a page of Municipios");
        Page<Municipios> page = municipiosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /municipios/:id} : get the "id" municipios.
     *
     * @param id the id of the municipios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the municipios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/municipios/{id}")
    public ResponseEntity<Municipios> getMunicipios(@PathVariable Long id) {
        log.debug("REST request to get Municipios : {}", id);
        Optional<Municipios> municipios = municipiosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(municipios);
    }

    /**
     * {@code DELETE  /municipios/:id} : delete the "id" municipios.
     *
     * @param id the id of the municipios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/municipios/{id}")
    public ResponseEntity<Void> deleteMunicipios(@PathVariable Long id) {
        log.debug("REST request to delete Municipios : {}", id);
        municipiosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
