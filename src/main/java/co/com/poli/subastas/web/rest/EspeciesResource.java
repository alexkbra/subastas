package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Especies;
import co.com.poli.subastas.repository.EspeciesRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.Especies}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EspeciesResource {

    private final Logger log = LoggerFactory.getLogger(EspeciesResource.class);

    private static final String ENTITY_NAME = "especies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspeciesRepository especiesRepository;

    public EspeciesResource(EspeciesRepository especiesRepository) {
        this.especiesRepository = especiesRepository;
    }

    /**
     * {@code POST  /especies} : Create a new especies.
     *
     * @param especies the especies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especies, or with status {@code 400 (Bad Request)} if the especies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/especies")
    public ResponseEntity<Especies> createEspecies(@Valid @RequestBody Especies especies) throws URISyntaxException {
        log.debug("REST request to save Especies : {}", especies);
        if (especies.getId() != null) {
            throw new BadRequestAlertException("A new especies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Especies result = especiesRepository.save(especies);
        return ResponseEntity.created(new URI("/api/especies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /especies} : Updates an existing especies.
     *
     * @param especies the especies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especies,
     * or with status {@code 400 (Bad Request)} if the especies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/especies")
    public ResponseEntity<Especies> updateEspecies(@Valid @RequestBody Especies especies) throws URISyntaxException {
        log.debug("REST request to update Especies : {}", especies);
        if (especies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Especies result = especiesRepository.save(especies);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especies.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /especies} : get all the especies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especies in body.
     */
    @GetMapping("/especies")
    public ResponseEntity<List<Especies>> getAllEspecies(Pageable pageable) {
        log.debug("REST request to get a page of Especies");
        Page<Especies> page = especiesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especies/:id} : get the "id" especies.
     *
     * @param id the id of the especies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/especies/{id}")
    public ResponseEntity<Especies> getEspecies(@PathVariable Long id) {
        log.debug("REST request to get Especies : {}", id);
        Optional<Especies> especies = especiesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(especies);
    }

    /**
     * {@code DELETE  /especies/:id} : delete the "id" especies.
     *
     * @param id the id of the especies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/especies/{id}")
    public ResponseEntity<Void> deleteEspecies(@PathVariable Long id) {
        log.debug("REST request to delete Especies : {}", id);
        especiesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
