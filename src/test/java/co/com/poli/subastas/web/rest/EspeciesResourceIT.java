package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Especies;
import co.com.poli.subastas.repository.EspeciesRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EspeciesResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class EspeciesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DECRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private EspeciesRepository especiesRepository;

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

    private MockMvc restEspeciesMockMvc;

    private Especies especies;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspeciesResource especiesResource = new EspeciesResource(especiesRepository);
        this.restEspeciesMockMvc = MockMvcBuilders.standaloneSetup(especiesResource)
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
    public static Especies createEntity(EntityManager em) {
        Especies especies = new Especies()
            .nombre(DEFAULT_NOMBRE)
            .decripcion(DEFAULT_DECRIPCION)
            .code(DEFAULT_CODE);
        return especies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especies createUpdatedEntity(EntityManager em) {
        Especies especies = new Especies()
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .code(UPDATED_CODE);
        return especies;
    }

    @BeforeEach
    public void initTest() {
        especies = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspecies() throws Exception {
        int databaseSizeBeforeCreate = especiesRepository.findAll().size();

        // Create the Especies
        restEspeciesMockMvc.perform(post("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(especies)))
            .andExpect(status().isCreated());

        // Validate the Especies in the database
        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeCreate + 1);
        Especies testEspecies = especiesList.get(especiesList.size() - 1);
        assertThat(testEspecies.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEspecies.getDecripcion()).isEqualTo(DEFAULT_DECRIPCION);
        assertThat(testEspecies.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createEspeciesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especiesRepository.findAll().size();

        // Create the Especies with an existing ID
        especies.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspeciesMockMvc.perform(post("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(especies)))
            .andExpect(status().isBadRequest());

        // Validate the Especies in the database
        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = especiesRepository.findAll().size();
        // set the field null
        especies.setNombre(null);

        // Create the Especies, which fails.

        restEspeciesMockMvc.perform(post("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(especies)))
            .andExpect(status().isBadRequest());

        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = especiesRepository.findAll().size();
        // set the field null
        especies.setCode(null);

        // Create the Especies, which fails.

        restEspeciesMockMvc.perform(post("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(especies)))
            .andExpect(status().isBadRequest());

        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspecies() throws Exception {
        // Initialize the database
        especiesRepository.saveAndFlush(especies);

        // Get all the especiesList
        restEspeciesMockMvc.perform(get("/api/especies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especies.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].decripcion").value(hasItem(DEFAULT_DECRIPCION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getEspecies() throws Exception {
        // Initialize the database
        especiesRepository.saveAndFlush(especies);

        // Get the especies
        restEspeciesMockMvc.perform(get("/api/especies/{id}", especies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especies.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.decripcion").value(DEFAULT_DECRIPCION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingEspecies() throws Exception {
        // Get the especies
        restEspeciesMockMvc.perform(get("/api/especies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspecies() throws Exception {
        // Initialize the database
        especiesRepository.saveAndFlush(especies);

        int databaseSizeBeforeUpdate = especiesRepository.findAll().size();

        // Update the especies
        Especies updatedEspecies = especiesRepository.findById(especies.getId()).get();
        // Disconnect from session so that the updates on updatedEspecies are not directly saved in db
        em.detach(updatedEspecies);
        updatedEspecies
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .code(UPDATED_CODE);

        restEspeciesMockMvc.perform(put("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEspecies)))
            .andExpect(status().isOk());

        // Validate the Especies in the database
        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeUpdate);
        Especies testEspecies = especiesList.get(especiesList.size() - 1);
        assertThat(testEspecies.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEspecies.getDecripcion()).isEqualTo(UPDATED_DECRIPCION);
        assertThat(testEspecies.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingEspecies() throws Exception {
        int databaseSizeBeforeUpdate = especiesRepository.findAll().size();

        // Create the Especies

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspeciesMockMvc.perform(put("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(especies)))
            .andExpect(status().isBadRequest());

        // Validate the Especies in the database
        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEspecies() throws Exception {
        // Initialize the database
        especiesRepository.saveAndFlush(especies);

        int databaseSizeBeforeDelete = especiesRepository.findAll().size();

        // Delete the especies
        restEspeciesMockMvc.perform(delete("/api/especies/{id}", especies.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Especies> especiesList = especiesRepository.findAll();
        assertThat(especiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
