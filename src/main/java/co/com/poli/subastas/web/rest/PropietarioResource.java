package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Propietario;
import co.com.poli.subastas.repository.PropietarioRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Propietario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PropietarioResource {

    private final Logger log = LoggerFactory.getLogger(PropietarioResource.class);

    private static final String ENTITY_NAME = "propietario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropietarioRepository propietarioRepository;

    public PropietarioResource(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    /**
     * {@code POST  /propietarios} : Create a new propietario.
     *
     * @param propietario the propietario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propietario, or with status {@code 400 (Bad Request)} if the propietario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propietarios")
    public ResponseEntity<Propietario> createPropietario(@Valid @RequestBody Propietario propietario) throws URISyntaxException {
        log.debug("REST request to save Propietario : {}", propietario);
        if (propietario.getId() != null) {
            throw new BadRequestAlertException("A new propietario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Propietario result = propietarioRepository.save(propietario);
        return ResponseEntity.created(new URI("/api/propietarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propietarios} : Updates an existing propietario.
     *
     * @param propietario the propietario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propietario,
     * or with status {@code 400 (Bad Request)} if the propietario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propietario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propietarios")
    public ResponseEntity<Propietario> updatePropietario(@Valid @RequestBody Propietario propietario) throws URISyntaxException {
        log.debug("REST request to update Propietario : {}", propietario);
        if (propietario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Propietario result = propietarioRepository.save(propietario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propietario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propietarios} : get all the propietarios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propietarios in body.
     */
    @GetMapping("/propietarios")
    public ResponseEntity<List<Propietario>> getAllPropietarios(Pageable pageable) {
        log.debug("REST request to get a page of Propietarios");
        Page<Propietario> page = propietarioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /propietarios/:id} : get the "id" propietario.
     *
     * @param id the id of the propietario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propietario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propietarios/{id}")
    public ResponseEntity<Propietario> getPropietario(@PathVariable Long id) {
        log.debug("REST request to get Propietario : {}", id);
        Optional<Propietario> propietario = propietarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(propietario);
    }

    /**
     * {@code DELETE  /propietarios/:id} : delete the "id" propietario.
     *
     * @param id the id of the propietario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propietarios/{id}")
    public ResponseEntity<Void> deletePropietario(@PathVariable Long id) {
        log.debug("REST request to delete Propietario : {}", id);
        propietarioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
