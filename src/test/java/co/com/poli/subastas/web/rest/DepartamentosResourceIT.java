package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Departamentos;
import co.com.poli.subastas.repository.DepartamentosRepository;
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
 * Integration tests for the {@link DepartamentosResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class DepartamentosResourceIT {

    private static final Long DEFAULT_IDDEPARTAMENTOS = 1L;
    private static final Long UPDATED_IDDEPARTAMENTOS = 2L;

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    @Autowired
    private DepartamentosRepository departamentosRepository;

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

    private MockMvc restDepartamentosMockMvc;

    private Departamentos departamentos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartamentosResource departamentosResource = new DepartamentosResource(departamentosRepository);
        this.restDepartamentosMockMvc = MockMvcBuilders.standaloneSetup(departamentosResource)
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
    public static Departamentos createEntity(EntityManager em) {
        Departamentos departamentos = new Departamentos()
            .iddepartamentos(DEFAULT_IDDEPARTAMENTOS)
            .departamento(DEFAULT_DEPARTAMENTO);
        return departamentos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamentos createUpdatedEntity(EntityManager em) {
        Departamentos departamentos = new Departamentos()
            .iddepartamentos(UPDATED_IDDEPARTAMENTOS)
            .departamento(UPDATED_DEPARTAMENTO);
        return departamentos;
    }

    @BeforeEach
    public void initTest() {
        departamentos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartamentos() throws Exception {
        int databaseSizeBeforeCreate = departamentosRepository.findAll().size();

        // Create the Departamentos
        restDepartamentosMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isCreated());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeCreate + 1);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getIddepartamentos()).isEqualTo(DEFAULT_IDDEPARTAMENTOS);
        assertThat(testDepartamentos.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
    }

    @Test
    @Transactional
    public void createDepartamentosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departamentosRepository.findAll().size();

        // Create the Departamentos with an existing ID
        departamentos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentosMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDepartamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = departamentosRepository.findAll().size();
        // set the field null
        departamentos.setDepartamento(null);

        // Create the Departamentos, which fails.

        restDepartamentosMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        // Get all the departamentosList
        restDepartamentosMockMvc.perform(get("/api/departamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].iddepartamentos").value(hasItem(DEFAULT_IDDEPARTAMENTOS.intValue())))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO)));
    }
    
    @Test
    @Transactional
    public void getDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        // Get the departamentos
        restDepartamentosMockMvc.perform(get("/api/departamentos/{id}", departamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamentos.getId().intValue()))
            .andExpect(jsonPath("$.iddepartamentos").value(DEFAULT_IDDEPARTAMENTOS.intValue()))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO));
    }

    @Test
    @Transactional
    public void getNonExistingDepartamentos() throws Exception {
        // Get the departamentos
        restDepartamentosMockMvc.perform(get("/api/departamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Update the departamentos
        Departamentos updatedDepartamentos = departamentosRepository.findById(departamentos.getId()).get();
        // Disconnect from session so that the updates on updatedDepartamentos are not directly saved in db
        em.detach(updatedDepartamentos);
        updatedDepartamentos
            .iddepartamentos(UPDATED_IDDEPARTAMENTOS)
            .departamento(UPDATED_DEPARTAMENTO);

        restDepartamentosMockMvc.perform(put("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartamentos)))
            .andExpect(status().isOk());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getIddepartamentos()).isEqualTo(UPDATED_IDDEPARTAMENTOS);
        assertThat(testDepartamentos.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Create the Departamentos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc.perform(put("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeDelete = departamentosRepository.findAll().size();

        // Delete the departamentos
        restDepartamentosMockMvc.perform(delete("/api/departamentos/{id}", departamentos.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
