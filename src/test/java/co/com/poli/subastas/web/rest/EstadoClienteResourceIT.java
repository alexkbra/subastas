package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.EstadoCliente;
import co.com.poli.subastas.repository.EstadoClienteRepository;
import co.com.poli.subastas.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EstadoClienteResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class EstadoClienteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private EstadoClienteRepository estadoClienteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEstadoClienteMockMvc;

    private EstadoCliente estadoCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadoClienteResource estadoClienteResource = new EstadoClienteResource(estadoClienteRepository);
        this.restEstadoClienteMockMvc = MockMvcBuilders.standaloneSetup(estadoClienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoCliente createEntity(EntityManager em) {
        EstadoCliente estadoCliente = new EstadoCliente()
            .nombre(DEFAULT_NOMBRE)
            .code(DEFAULT_CODE);
        return estadoCliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoCliente createUpdatedEntity(EntityManager em) {
        EstadoCliente estadoCliente = new EstadoCliente()
            .nombre(UPDATED_NOMBRE)
            .code(UPDATED_CODE);
        return estadoCliente;
    }

    @BeforeEach
    public void initTest() {
        estadoCliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoCliente() throws Exception {
        int databaseSizeBeforeCreate = estadoClienteRepository.findAll().size();

        // Create the EstadoCliente
        restEstadoClienteMockMvc.perform(post("/api/estado-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCliente)))
            .andExpect(status().isCreated());

        // Validate the EstadoCliente in the database
        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoCliente testEstadoCliente = estadoClienteList.get(estadoClienteList.size() - 1);
        assertThat(testEstadoCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstadoCliente.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createEstadoClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoClienteRepository.findAll().size();

        // Create the EstadoCliente with an existing ID
        estadoCliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoClienteMockMvc.perform(post("/api/estado-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCliente)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoCliente in the database
        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoClienteRepository.findAll().size();
        // set the field null
        estadoCliente.setNombre(null);

        // Create the EstadoCliente, which fails.

        restEstadoClienteMockMvc.perform(post("/api/estado-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCliente)))
            .andExpect(status().isBadRequest());

        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoClienteRepository.findAll().size();
        // set the field null
        estadoCliente.setCode(null);

        // Create the EstadoCliente, which fails.

        restEstadoClienteMockMvc.perform(post("/api/estado-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCliente)))
            .andExpect(status().isBadRequest());

        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstadoClientes() throws Exception {
        // Initialize the database
        estadoClienteRepository.saveAndFlush(estadoCliente);

        // Get all the estadoClienteList
        restEstadoClienteMockMvc.perform(get("/api/estado-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getEstadoCliente() throws Exception {
        // Initialize the database
        estadoClienteRepository.saveAndFlush(estadoCliente);

        // Get the estadoCliente
        restEstadoClienteMockMvc.perform(get("/api/estado-clientes/{id}", estadoCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoCliente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoCliente() throws Exception {
        // Get the estadoCliente
        restEstadoClienteMockMvc.perform(get("/api/estado-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoCliente() throws Exception {
        // Initialize the database
        estadoClienteRepository.saveAndFlush(estadoCliente);

        int databaseSizeBeforeUpdate = estadoClienteRepository.findAll().size();

        // Update the estadoCliente
        EstadoCliente updatedEstadoCliente = estadoClienteRepository.findById(estadoCliente.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoCliente are not directly saved in db
        em.detach(updatedEstadoCliente);
        updatedEstadoCliente
            .nombre(UPDATED_NOMBRE)
            .code(UPDATED_CODE);

        restEstadoClienteMockMvc.perform(put("/api/estado-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstadoCliente)))
            .andExpect(status().isOk());

        // Validate the EstadoCliente in the database
        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeUpdate);
        EstadoCliente testEstadoCliente = estadoClienteList.get(estadoClienteList.size() - 1);
        assertThat(testEstadoCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstadoCliente.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoCliente() throws Exception {
        int databaseSizeBeforeUpdate = estadoClienteRepository.findAll().size();

        // Create the EstadoCliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoClienteMockMvc.perform(put("/api/estado-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCliente)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoCliente in the database
        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoCliente() throws Exception {
        // Initialize the database
        estadoClienteRepository.saveAndFlush(estadoCliente);

        int databaseSizeBeforeDelete = estadoClienteRepository.findAll().size();

        // Delete the estadoCliente
        restEstadoClienteMockMvc.perform(delete("/api/estado-clientes/{id}", estadoCliente.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoCliente> estadoClienteList = estadoClienteRepository.findAll();
        assertThat(estadoClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
