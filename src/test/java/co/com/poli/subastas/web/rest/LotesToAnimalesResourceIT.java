package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.LotesToAnimales;
import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.Animales;
import co.com.poli.subastas.repository.LotesRepository;
import co.com.poli.subastas.repository.LotesToAnimalesRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LotesToAnimalesResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class LotesToAnimalesResourceIT {

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);

    @Autowired
    private LotesToAnimalesRepository lotesToAnimalesRepository;

    @Autowired
    private LotesRepository lotesRepository; 

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

    private MockMvc restLotesToAnimalesMockMvc;

    private LotesToAnimales lotesToAnimales;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LotesToAnimalesResource lotesToAnimalesResource = new LotesToAnimalesResource(lotesToAnimalesRepository, lotesRepository);
        this.restLotesToAnimalesMockMvc = MockMvcBuilders.standaloneSetup(lotesToAnimalesResource)
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
    public static LotesToAnimales createEntity(EntityManager em) {
        LotesToAnimales lotesToAnimales = new LotesToAnimales()
            .cantidad(DEFAULT_CANTIDAD);
        // Add required entity
        Lotes lotes;
        if (TestUtil.findAll(em, Lotes.class).isEmpty()) {
            lotes = LotesResourceIT.createEntity(em);
            em.persist(lotes);
            em.flush();
        } else {
            lotes = TestUtil.findAll(em, Lotes.class).get(0);
        }
        lotesToAnimales.setLotes(lotes);
        // Add required entity
        Animales animales;
        if (TestUtil.findAll(em, Animales.class).isEmpty()) {
            animales = AnimalesResourceIT.createEntity(em);
            em.persist(animales);
            em.flush();
        } else {
            animales = TestUtil.findAll(em, Animales.class).get(0);
        }
        lotesToAnimales.setAnimales(animales);
        return lotesToAnimales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotesToAnimales createUpdatedEntity(EntityManager em) {
        LotesToAnimales lotesToAnimales = new LotesToAnimales()
            .cantidad(UPDATED_CANTIDAD);
        // Add required entity
        Lotes lotes;
        if (TestUtil.findAll(em, Lotes.class).isEmpty()) {
            lotes = LotesResourceIT.createUpdatedEntity(em);
            em.persist(lotes);
            em.flush();
        } else {
            lotes = TestUtil.findAll(em, Lotes.class).get(0);
        }
        lotesToAnimales.setLotes(lotes);
        // Add required entity
        Animales animales;
        if (TestUtil.findAll(em, Animales.class).isEmpty()) {
            animales = AnimalesResourceIT.createUpdatedEntity(em);
            em.persist(animales);
            em.flush();
        } else {
            animales = TestUtil.findAll(em, Animales.class).get(0);
        }
        lotesToAnimales.setAnimales(animales);
        return lotesToAnimales;
    }

    @BeforeEach
    public void initTest() {
        lotesToAnimales = createEntity(em);
    }

    @Test
    @Transactional
    public void createLotesToAnimales() throws Exception {
        int databaseSizeBeforeCreate = lotesToAnimalesRepository.findAll().size();

        // Create the LotesToAnimales
        restLotesToAnimalesMockMvc.perform(post("/api/lotes-to-animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotesToAnimales)))
            .andExpect(status().isCreated());

        // Validate the LotesToAnimales in the database
        List<LotesToAnimales> lotesToAnimalesList = lotesToAnimalesRepository.findAll();
        assertThat(lotesToAnimalesList).hasSize(databaseSizeBeforeCreate + 1);
        LotesToAnimales testLotesToAnimales = lotesToAnimalesList.get(lotesToAnimalesList.size() - 1);
        assertThat(testLotesToAnimales.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createLotesToAnimalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lotesToAnimalesRepository.findAll().size();

        // Create the LotesToAnimales with an existing ID
        lotesToAnimales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotesToAnimalesMockMvc.perform(post("/api/lotes-to-animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotesToAnimales)))
            .andExpect(status().isBadRequest());

        // Validate the LotesToAnimales in the database
        List<LotesToAnimales> lotesToAnimalesList = lotesToAnimalesRepository.findAll();
        assertThat(lotesToAnimalesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = lotesToAnimalesRepository.findAll().size();
        // set the field null
        lotesToAnimales.setCantidad(null);

        // Create the LotesToAnimales, which fails.

        restLotesToAnimalesMockMvc.perform(post("/api/lotes-to-animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotesToAnimales)))
            .andExpect(status().isBadRequest());

        List<LotesToAnimales> lotesToAnimalesList = lotesToAnimalesRepository.findAll();
        assertThat(lotesToAnimalesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLotesToAnimales() throws Exception {
        // Initialize the database
        lotesToAnimalesRepository.saveAndFlush(lotesToAnimales);

        // Get all the lotesToAnimalesList
        restLotesToAnimalesMockMvc.perform(get("/api/lotes-to-animales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotesToAnimales.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }
    
    @Test
    @Transactional
    public void getLotesToAnimales() throws Exception {
        // Initialize the database
        lotesToAnimalesRepository.saveAndFlush(lotesToAnimales);

        // Get the lotesToAnimales
        restLotesToAnimalesMockMvc.perform(get("/api/lotes-to-animales/{id}", lotesToAnimales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lotesToAnimales.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLotesToAnimales() throws Exception {
        // Get the lotesToAnimales
        restLotesToAnimalesMockMvc.perform(get("/api/lotes-to-animales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLotesToAnimales() throws Exception {
        // Initialize the database
        lotesToAnimalesRepository.saveAndFlush(lotesToAnimales);

        int databaseSizeBeforeUpdate = lotesToAnimalesRepository.findAll().size();

        // Update the lotesToAnimales
        LotesToAnimales updatedLotesToAnimales = lotesToAnimalesRepository.findById(lotesToAnimales.getId()).get();
        // Disconnect from session so that the updates on updatedLotesToAnimales are not directly saved in db
        em.detach(updatedLotesToAnimales);
        updatedLotesToAnimales
            .cantidad(UPDATED_CANTIDAD);

        restLotesToAnimalesMockMvc.perform(put("/api/lotes-to-animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLotesToAnimales)))
            .andExpect(status().isOk());

        // Validate the LotesToAnimales in the database
        List<LotesToAnimales> lotesToAnimalesList = lotesToAnimalesRepository.findAll();
        assertThat(lotesToAnimalesList).hasSize(databaseSizeBeforeUpdate);
        LotesToAnimales testLotesToAnimales = lotesToAnimalesList.get(lotesToAnimalesList.size() - 1);
        assertThat(testLotesToAnimales.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingLotesToAnimales() throws Exception {
        int databaseSizeBeforeUpdate = lotesToAnimalesRepository.findAll().size();

        // Create the LotesToAnimales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotesToAnimalesMockMvc.perform(put("/api/lotes-to-animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotesToAnimales)))
            .andExpect(status().isBadRequest());

        // Validate the LotesToAnimales in the database
        List<LotesToAnimales> lotesToAnimalesList = lotesToAnimalesRepository.findAll();
        assertThat(lotesToAnimalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLotesToAnimales() throws Exception {
        // Initialize the database
        lotesToAnimalesRepository.saveAndFlush(lotesToAnimales);

        int databaseSizeBeforeDelete = lotesToAnimalesRepository.findAll().size();

        // Delete the lotesToAnimales
        restLotesToAnimalesMockMvc.perform(delete("/api/lotes-to-animales/{id}", lotesToAnimales.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LotesToAnimales> lotesToAnimalesList = lotesToAnimalesRepository.findAll();
        assertThat(lotesToAnimalesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
