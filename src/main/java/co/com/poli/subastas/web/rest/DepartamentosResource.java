package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Departamentos;
import co.com.poli.subastas.repository.DepartamentosRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Departamentos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DepartamentosResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentosResource.class);

    private static final String ENTITY_NAME = "departamentos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentosRepository departamentosRepository;

    public DepartamentosResource(DepartamentosRepository departamentosRepository) {
        this.departamentosRepository = departamentosRepository;
    }

    /**
     * {@code POST  /departamentos} : Create a new departamentos.
     *
     * @param departamentos the departamentos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentos, or with status {@code 400 (Bad Request)} if the departamentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departamentos")
    public ResponseEntity<Departamentos> createDepartamentos(@Valid @RequestBody Departamentos departamentos) throws URISyntaxException {
        log.debug("REST request to save Departamentos : {}", departamentos);
        if (departamentos.getId() != null) {
            throw new BadRequestAlertException("A new departamentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departamentos result = departamentosRepository.save(departamentos);
        return ResponseEntity.created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departamentos} : Updates an existing departamentos.
     *
     * @param departamentos the departamentos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentos,
     * or with status {@code 400 (Bad Request)} if the departamentos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departamentos")
    public ResponseEntity<Departamentos> updateDepartamentos(@Valid @RequestBody Departamentos departamentos) throws URISyntaxException {
        log.debug("REST request to update Departamentos : {}", departamentos);
        if (departamentos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Departamentos result = departamentosRepository.save(departamentos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /departamentos} : get all the departamentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentos in body.
     */
    @GetMapping("/departamentos")
    public ResponseEntity<List<Departamentos>> getAllDepartamentos(Pageable pageable) {
        log.debug("REST request to get a page of Departamentos");
        Page<Departamentos> page = departamentosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departamentos/:id} : get the "id" departamentos.
     *
     * @param id the id of the departamentos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departamentos/{id}")
    public ResponseEntity<Departamentos> getDepartamentos(@PathVariable Long id) {
        log.debug("REST request to get Departamentos : {}", id);
        Optional<Departamentos> departamentos = departamentosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(departamentos);
    }

    /**
     * {@code DELETE  /departamentos/:id} : delete the "id" departamentos.
     *
     * @param id the id of the departamentos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departamentos/{id}")
    public ResponseEntity<Void> deleteDepartamentos(@PathVariable Long id) {
        log.debug("REST request to delete Departamentos : {}", id);
        departamentosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
