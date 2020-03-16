package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.EstadoCliente;
import co.com.poli.subastas.repository.EstadoClienteRepository;
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
 * REST controller for managing {@link co.com.poli.subastas.domain.EstadoCliente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoClienteResource {

    private final Logger log = LoggerFactory.getLogger(EstadoClienteResource.class);

    private static final String ENTITY_NAME = "estadoCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoClienteRepository estadoClienteRepository;

    public EstadoClienteResource(EstadoClienteRepository estadoClienteRepository) {
        this.estadoClienteRepository = estadoClienteRepository;
    }

    /**
     * {@code POST  /estado-clientes} : Create a new estadoCliente.
     *
     * @param estadoCliente the estadoCliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoCliente, or with status {@code 400 (Bad Request)} if the estadoCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-clientes")
    public ResponseEntity<EstadoCliente> createEstadoCliente(@Valid @RequestBody EstadoCliente estadoCliente) throws URISyntaxException {
        log.debug("REST request to save EstadoCliente : {}", estadoCliente);
        if (estadoCliente.getId() != null) {
            throw new BadRequestAlertException("A new estadoCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoCliente result = estadoClienteRepository.save(estadoCliente);
        return ResponseEntity.created(new URI("/api/estado-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-clientes} : Updates an existing estadoCliente.
     *
     * @param estadoCliente the estadoCliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoCliente,
     * or with status {@code 400 (Bad Request)} if the estadoCliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoCliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-clientes")
    public ResponseEntity<EstadoCliente> updateEstadoCliente(@Valid @RequestBody EstadoCliente estadoCliente) throws URISyntaxException {
        log.debug("REST request to update EstadoCliente : {}", estadoCliente);
        if (estadoCliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoCliente result = estadoClienteRepository.save(estadoCliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoCliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-clientes} : get all the estadoClientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoClientes in body.
     */
    @GetMapping("/estado-clientes")
    public ResponseEntity<List<EstadoCliente>> getAllEstadoClientes(Pageable pageable) {
        log.debug("REST request to get a page of EstadoClientes");
        Page<EstadoCliente> page = estadoClienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-clientes/:id} : get the "id" estadoCliente.
     *
     * @param id the id of the estadoCliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoCliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-clientes/{id}")
    public ResponseEntity<EstadoCliente> getEstadoCliente(@PathVariable Long id) {
        log.debug("REST request to get EstadoCliente : {}", id);
        Optional<EstadoCliente> estadoCliente = estadoClienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoCliente);
    }

    /**
     * {@code DELETE  /estado-clientes/:id} : delete the "id" estadoCliente.
     *
     * @param id the id of the estadoCliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-clientes/{id}")
    public ResponseEntity<Void> deleteEstadoCliente(@PathVariable Long id) {
        log.debug("REST request to delete EstadoCliente : {}", id);
        estadoClienteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
