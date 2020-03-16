package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Municipios;
import co.com.poli.subastas.domain.Departamentos;
import co.com.poli.subastas.repository.MunicipiosRepository;
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
 * Integration tests for the {@link MunicipiosResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class MunicipiosResourceIT {

    private static final Long DEFAULT_IDMUNICIPIOS = 1L;
    private static final Long UPDATED_IDMUNICIPIOS = 2L;

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    @Autowired
    private MunicipiosRepository municipiosRepository;

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

    private MockMvc restMunicipiosMockMvc;

    private Municipios municipios;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MunicipiosResource municipiosResource = new MunicipiosResource(municipiosRepository);
        this.restMunicipiosMockMvc = MockMvcBuilders.standaloneSetup(municipiosResource)
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
    public static Municipios createEntity(EntityManager em) {
        Municipios municipios = new Municipios()
            .idmunicipios(DEFAULT_IDMUNICIPIOS)
            .municipio(DEFAULT_MUNICIPIO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Departamentos departamentos;
        if (TestUtil.findAll(em, Departamentos.class).isEmpty()) {
            departamentos = DepartamentosResourceIT.createEntity(em);
            em.persist(departamentos);
            em.flush();
        } else {
            departamentos = TestUtil.findAll(em, Departamentos.class).get(0);
        }
        municipios.setDepartamentos(departamentos);
        return municipios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipios createUpdatedEntity(EntityManager em) {
        Municipios municipios = new Municipios()
            .idmunicipios(UPDATED_IDMUNICIPIOS)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Departamentos departamentos;
        if (TestUtil.findAll(em, Departamentos.class).isEmpty()) {
            departamentos = DepartamentosResourceIT.createUpdatedEntity(em);
            em.persist(departamentos);
            em.flush();
        } else {
            departamentos = TestUtil.findAll(em, Departamentos.class).get(0);
        }
        municipios.setDepartamentos(departamentos);
        return municipios;
    }

    @BeforeEach
    public void initTest() {
        municipios = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipios() throws Exception {
        int databaseSizeBeforeCreate = municipiosRepository.findAll().size();

        // Create the Municipios
        restMunicipiosMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isCreated());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeCreate + 1);
        Municipios testMunicipios = municipiosList.get(municipiosList.size() - 1);
        assertThat(testMunicipios.getIdmunicipios()).isEqualTo(DEFAULT_IDMUNICIPIOS);
        assertThat(testMunicipios.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testMunicipios.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createMunicipiosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipiosRepository.findAll().size();

        // Create the Municipios with an existing ID
        municipios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipiosMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isBadRequest());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMunicipioIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipiosRepository.findAll().size();
        // set the field null
        municipios.setMunicipio(null);

        // Create the Municipios, which fails.

        restMunicipiosMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isBadRequest());

        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipiosRepository.findAll().size();
        // set the field null
        municipios.setEstado(null);

        // Create the Municipios, which fails.

        restMunicipiosMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isBadRequest());

        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        // Get all the municipiosList
        restMunicipiosMockMvc.perform(get("/api/municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmunicipios").value(hasItem(DEFAULT_IDMUNICIPIOS.intValue())))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }
    
    @Test
    @Transactional
    public void getMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        // Get the municipios
        restMunicipiosMockMvc.perform(get("/api/municipios/{id}", municipios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(municipios.getId().intValue()))
            .andExpect(jsonPath("$.idmunicipios").value(DEFAULT_IDMUNICIPIOS.intValue()))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipios() throws Exception {
        // Get the municipios
        restMunicipiosMockMvc.perform(get("/api/municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        int databaseSizeBeforeUpdate = municipiosRepository.findAll().size();

        // Update the municipios
        Municipios updatedMunicipios = municipiosRepository.findById(municipios.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipios are not directly saved in db
        em.detach(updatedMunicipios);
        updatedMunicipios
            .idmunicipios(UPDATED_IDMUNICIPIOS)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO);

        restMunicipiosMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMunicipios)))
            .andExpect(status().isOk());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeUpdate);
        Municipios testMunicipios = municipiosList.get(municipiosList.size() - 1);
        assertThat(testMunicipios.getIdmunicipios()).isEqualTo(UPDATED_IDMUNICIPIOS);
        assertThat(testMunicipios.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testMunicipios.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipios() throws Exception {
        int databaseSizeBeforeUpdate = municipiosRepository.findAll().size();

        // Create the Municipios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunicipiosMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipios)))
            .andExpect(status().isBadRequest());

        // Validate the Municipios in the database
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipios() throws Exception {
        // Initialize the database
        municipiosRepository.saveAndFlush(municipios);

        int databaseSizeBeforeDelete = municipiosRepository.findAll().size();

        // Delete the municipios
        restMunicipiosMockMvc.perform(delete("/api/municipios/{id}", municipios.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Municipios> municipiosList = municipiosRepository.findAll();
        assertThat(municipiosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
