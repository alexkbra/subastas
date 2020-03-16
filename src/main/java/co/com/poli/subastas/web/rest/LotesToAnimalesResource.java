package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.LotesToAnimales;
import co.com.poli.subastas.repository.LotesRepository;
import co.com.poli.subastas.repository.LotesToAnimalesRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.LotesToAnimales}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LotesToAnimalesResource {

    private final Logger log = LoggerFactory.getLogger(LotesToAnimalesResource.class);

    private static final String ENTITY_NAME = "lotesToAnimales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotesToAnimalesRepository lotesToAnimalesRepository;
    private final LotesRepository lotesRepository;

    public LotesToAnimalesResource(LotesToAnimalesRepository lotesToAnimalesRepository,LotesRepository lotesRepository) {
        this.lotesToAnimalesRepository = lotesToAnimalesRepository;
        this.lotesRepository = lotesRepository;
    }

    /**
     * {@code POST  /lotes-to-animales} : Create a new lotesToAnimales.
     *
     * @param lotesToAnimales the lotesToAnimales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lotesToAnimales, or with status {@code 400 (Bad Request)} if the lotesToAnimales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lotes-to-animales")
    public ResponseEntity<LotesToAnimales> createLotesToAnimales(@Valid @RequestBody LotesToAnimales lotesToAnimales) throws URISyntaxException {
        log.debug("REST request to save LotesToAnimales : {}", lotesToAnimales);
        if (lotesToAnimales.getId() != null) {
            throw new BadRequestAlertException("A new lotesToAnimales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LotesToAnimales result = lotesToAnimalesRepository.save(lotesToAnimales);
        return ResponseEntity.created(new URI("/api/lotes-to-animales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lotes-to-animales} : Updates an existing lotesToAnimales.
     *
     * @param lotesToAnimales the lotesToAnimales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotesToAnimales,
     * or with status {@code 400 (Bad Request)} if the lotesToAnimales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lotesToAnimales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lotes-to-animales")
    public ResponseEntity<LotesToAnimales> updateLotesToAnimales(@Valid @RequestBody LotesToAnimales lotesToAnimales) throws URISyntaxException {
        log.debug("REST request to update LotesToAnimales : {}", lotesToAnimales);
        if (lotesToAnimales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LotesToAnimales result = lotesToAnimalesRepository.save(lotesToAnimales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lotesToAnimales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lotes-to-animales} : get all the lotesToAnimales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotesToAnimales in body.
     */
    @GetMapping("/lotes-to-animales")
    public ResponseEntity<List<LotesToAnimales>> getAllLotesToAnimales(Pageable pageable) {
        log.debug("REST request to get a page of LotesToAnimales");
        Page<LotesToAnimales> page = lotesToAnimalesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lotes-to-animales} : get all the lotesToAnimales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotesToAnimales in body.
     */
    @GetMapping("/lotes-to-animales/id-lotes/{idlotes}")
    public ResponseEntity<List<LotesToAnimales>> getLotesToAnimalesByLotes(@PathVariable Long idlotes,Pageable pageable) {
        log.debug("REST request to get a page of LotesToAnimales");
        Page<LotesToAnimales> page = lotesToAnimalesRepository.findByLotes(lotesRepository.findById(idlotes).get(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lotes-to-animales/:id} : get the "id" lotesToAnimales.
     *
     * @param id the id of the lotesToAnimales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lotesToAnimales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lotes-to-animales/{id}")
    public ResponseEntity<LotesToAnimales> getLotesToAnimales(@PathVariable Long id) {
        log.debug("REST request to get LotesToAnimales : {}", id);
        Optional<LotesToAnimales> lotesToAnimales = lotesToAnimalesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lotesToAnimales);
    }

    /**
     * {@code DELETE  /lotes-to-animales/:id} : delete the "id" lotesToAnimales.
     *
     * @param id the id of the lotesToAnimales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lotes-to-animales/{id}")
    public ResponseEntity<Void> deleteLotesToAnimales(@PathVariable Long id) {
        log.debug("REST request to delete LotesToAnimales : {}", id);
        lotesToAnimalesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
