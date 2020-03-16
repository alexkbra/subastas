package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.ClasificacionLote;
import co.com.poli.subastas.repository.ClasificacionLoteRepository;
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
 * Integration tests for the {@link ClasificacionLoteResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class ClasificacionLoteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ClasificacionLoteRepository clasificacionLoteRepository;

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

    private MockMvc restClasificacionLoteMockMvc;

    private ClasificacionLote clasificacionLote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClasificacionLoteResource clasificacionLoteResource = new ClasificacionLoteResource(clasificacionLoteRepository);
        this.restClasificacionLoteMockMvc = MockMvcBuilders.standaloneSetup(clasificacionLoteResource)
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
    public static ClasificacionLote createEntity(EntityManager em) {
        ClasificacionLote clasificacionLote = new ClasificacionLote()
            .nombre(DEFAULT_NOMBRE)
            .code(DEFAULT_CODE);
        return clasificacionLote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasificacionLote createUpdatedEntity(EntityManager em) {
        ClasificacionLote clasificacionLote = new ClasificacionLote()
            .nombre(UPDATED_NOMBRE)
            .code(UPDATED_CODE);
        return clasificacionLote;
    }

    @BeforeEach
    public void initTest() {
        clasificacionLote = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasificacionLote() throws Exception {
        int databaseSizeBeforeCreate = clasificacionLoteRepository.findAll().size();

        // Create the ClasificacionLote
        restClasificacionLoteMockMvc.perform(post("/api/clasificacion-lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionLote)))
            .andExpect(status().isCreated());

        // Validate the ClasificacionLote in the database
        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeCreate + 1);
        ClasificacionLote testClasificacionLote = clasificacionLoteList.get(clasificacionLoteList.size() - 1);
        assertThat(testClasificacionLote.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testClasificacionLote.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createClasificacionLoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clasificacionLoteRepository.findAll().size();

        // Create the ClasificacionLote with an existing ID
        clasificacionLote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasificacionLoteMockMvc.perform(post("/api/clasificacion-lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionLote)))
            .andExpect(status().isBadRequest());

        // Validate the ClasificacionLote in the database
        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificacionLoteRepository.findAll().size();
        // set the field null
        clasificacionLote.setNombre(null);

        // Create the ClasificacionLote, which fails.

        restClasificacionLoteMockMvc.perform(post("/api/clasificacion-lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionLote)))
            .andExpect(status().isBadRequest());

        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificacionLoteRepository.findAll().size();
        // set the field null
        clasificacionLote.setCode(null);

        // Create the ClasificacionLote, which fails.

        restClasificacionLoteMockMvc.perform(post("/api/clasificacion-lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionLote)))
            .andExpect(status().isBadRequest());

        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasificacionLotes() throws Exception {
        // Initialize the database
        clasificacionLoteRepository.saveAndFlush(clasificacionLote);

        // Get all the clasificacionLoteList
        restClasificacionLoteMockMvc.perform(get("/api/clasificacion-lotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificacionLote.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getClasificacionLote() throws Exception {
        // Initialize the database
        clasificacionLoteRepository.saveAndFlush(clasificacionLote);

        // Get the clasificacionLote
        restClasificacionLoteMockMvc.perform(get("/api/clasificacion-lotes/{id}", clasificacionLote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clasificacionLote.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingClasificacionLote() throws Exception {
        // Get the clasificacionLote
        restClasificacionLoteMockMvc.perform(get("/api/clasificacion-lotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasificacionLote() throws Exception {
        // Initialize the database
        clasificacionLoteRepository.saveAndFlush(clasificacionLote);

        int databaseSizeBeforeUpdate = clasificacionLoteRepository.findAll().size();

        // Update the clasificacionLote
        ClasificacionLote updatedClasificacionLote = clasificacionLoteRepository.findById(clasificacionLote.getId()).get();
        // Disconnect from session so that the updates on updatedClasificacionLote are not directly saved in db
        em.detach(updatedClasificacionLote);
        updatedClasificacionLote
            .nombre(UPDATED_NOMBRE)
            .code(UPDATED_CODE);

        restClasificacionLoteMockMvc.perform(put("/api/clasificacion-lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClasificacionLote)))
            .andExpect(status().isOk());

        // Validate the ClasificacionLote in the database
        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeUpdate);
        ClasificacionLote testClasificacionLote = clasificacionLoteList.get(clasificacionLoteList.size() - 1);
        assertThat(testClasificacionLote.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testClasificacionLote.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingClasificacionLote() throws Exception {
        int databaseSizeBeforeUpdate = clasificacionLoteRepository.findAll().size();

        // Create the ClasificacionLote

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasificacionLoteMockMvc.perform(put("/api/clasificacion-lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionLote)))
            .andExpect(status().isBadRequest());

        // Validate the ClasificacionLote in the database
        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClasificacionLote() throws Exception {
        // Initialize the database
        clasificacionLoteRepository.saveAndFlush(clasificacionLote);

        int databaseSizeBeforeDelete = clasificacionLoteRepository.findAll().size();

        // Delete the clasificacionLote
        restClasificacionLoteMockMvc.perform(delete("/api/clasificacion-lotes/{id}", clasificacionLote.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClasificacionLote> clasificacionLoteList = clasificacionLoteRepository.findAll();
        assertThat(clasificacionLoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
