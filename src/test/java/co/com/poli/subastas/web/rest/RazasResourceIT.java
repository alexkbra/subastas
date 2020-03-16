package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Razas;
import co.com.poli.subastas.domain.Especies;
import co.com.poli.subastas.repository.RazasRepository;
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
 * Integration tests for the {@link RazasResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class RazasResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DECRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private RazasRepository razasRepository;

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

    private MockMvc restRazasMockMvc;

    private Razas razas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RazasResource razasResource = new RazasResource(razasRepository);
        this.restRazasMockMvc = MockMvcBuilders.standaloneSetup(razasResource)
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
    public static Razas createEntity(EntityManager em) {
        Razas razas = new Razas()
            .nombre(DEFAULT_NOMBRE)
            .decripcion(DEFAULT_DECRIPCION)
            .code(DEFAULT_CODE);
        // Add required entity
        Especies especies;
        if (TestUtil.findAll(em, Especies.class).isEmpty()) {
            especies = EspeciesResourceIT.createEntity(em);
            em.persist(especies);
            em.flush();
        } else {
            especies = TestUtil.findAll(em, Especies.class).get(0);
        }
        razas.setEspecies(especies);
        return razas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Razas createUpdatedEntity(EntityManager em) {
        Razas razas = new Razas()
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .code(UPDATED_CODE);
        // Add required entity
        Especies especies;
        if (TestUtil.findAll(em, Especies.class).isEmpty()) {
            especies = EspeciesResourceIT.createUpdatedEntity(em);
            em.persist(especies);
            em.flush();
        } else {
            especies = TestUtil.findAll(em, Especies.class).get(0);
        }
        razas.setEspecies(especies);
        return razas;
    }

    @BeforeEach
    public void initTest() {
        razas = createEntity(em);
    }

    @Test
    @Transactional
    public void createRazas() throws Exception {
        int databaseSizeBeforeCreate = razasRepository.findAll().size();

        // Create the Razas
        restRazasMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(razas)))
            .andExpect(status().isCreated());

        // Validate the Razas in the database
        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeCreate + 1);
        Razas testRazas = razasList.get(razasList.size() - 1);
        assertThat(testRazas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRazas.getDecripcion()).isEqualTo(DEFAULT_DECRIPCION);
        assertThat(testRazas.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createRazasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = razasRepository.findAll().size();

        // Create the Razas with an existing ID
        razas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRazasMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(razas)))
            .andExpect(status().isBadRequest());

        // Validate the Razas in the database
        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = razasRepository.findAll().size();
        // set the field null
        razas.setNombre(null);

        // Create the Razas, which fails.

        restRazasMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(razas)))
            .andExpect(status().isBadRequest());

        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = razasRepository.findAll().size();
        // set the field null
        razas.setCode(null);

        // Create the Razas, which fails.

        restRazasMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(razas)))
            .andExpect(status().isBadRequest());

        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRazas() throws Exception {
        // Initialize the database
        razasRepository.saveAndFlush(razas);

        // Get all the razasList
        restRazasMockMvc.perform(get("/api/razas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(razas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].decripcion").value(hasItem(DEFAULT_DECRIPCION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getRazas() throws Exception {
        // Initialize the database
        razasRepository.saveAndFlush(razas);

        // Get the razas
        restRazasMockMvc.perform(get("/api/razas/{id}", razas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(razas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.decripcion").value(DEFAULT_DECRIPCION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingRazas() throws Exception {
        // Get the razas
        restRazasMockMvc.perform(get("/api/razas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRazas() throws Exception {
        // Initialize the database
        razasRepository.saveAndFlush(razas);

        int databaseSizeBeforeUpdate = razasRepository.findAll().size();

        // Update the razas
        Razas updatedRazas = razasRepository.findById(razas.getId()).get();
        // Disconnect from session so that the updates on updatedRazas are not directly saved in db
        em.detach(updatedRazas);
        updatedRazas
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .code(UPDATED_CODE);

        restRazasMockMvc.perform(put("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRazas)))
            .andExpect(status().isOk());

        // Validate the Razas in the database
        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeUpdate);
        Razas testRazas = razasList.get(razasList.size() - 1);
        assertThat(testRazas.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRazas.getDecripcion()).isEqualTo(UPDATED_DECRIPCION);
        assertThat(testRazas.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRazas() throws Exception {
        int databaseSizeBeforeUpdate = razasRepository.findAll().size();

        // Create the Razas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRazasMockMvc.perform(put("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(razas)))
            .andExpect(status().isBadRequest());

        // Validate the Razas in the database
        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRazas() throws Exception {
        // Initialize the database
        razasRepository.saveAndFlush(razas);

        int databaseSizeBeforeDelete = razasRepository.findAll().size();

        // Delete the razas
        restRazasMockMvc.perform(delete("/api/razas/{id}", razas.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Razas> razasList = razasRepository.findAll();
        assertThat(razasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
