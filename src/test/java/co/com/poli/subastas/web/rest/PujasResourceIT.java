package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Pujas;
import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.repository.PujasRepository;
import co.com.poli.subastas.repository.SubastasRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PujasResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class PujasResourceIT {

    private static final String DEFAULT_ID_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_ID_EVENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_SUBASTA = "AAAAAAAAAA";
    private static final String UPDATED_ID_SUBASTA = "BBBBBBBBBB";

    private static final String DEFAULT_ID_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_LOTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACEPTADO_GANADOR = false;
    private static final Boolean UPDATED_ACEPTADO_GANADOR = true;

    @Autowired
    private PujasRepository pujasRepository;
    
    @Autowired
    private SubastasRepository subastasRepository;

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

    private MockMvc restPujasMockMvc;

    private Pujas pujas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PujasResource pujasResource = new PujasResource(subastasRepository,pujasRepository);
        this.restPujasMockMvc = MockMvcBuilders.standaloneSetup(pujasResource)
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
    public static Pujas createEntity(EntityManager em) {
        Pujas pujas = new Pujas()
            .idEvento(DEFAULT_ID_EVENTO)
            .idSubasta(DEFAULT_ID_SUBASTA)
            .idLote(DEFAULT_ID_LOTE)
            .valor(DEFAULT_VALOR)
            .fechacreacion(DEFAULT_FECHACREACION)
            .aceptadoGanador(DEFAULT_ACEPTADO_GANADOR);
        // Add required entity
        Pujadores pujadores;
        if (TestUtil.findAll(em, Pujadores.class).isEmpty()) {
            pujadores = PujadoresResourceIT.createEntity(em);
            em.persist(pujadores);
            em.flush();
        } else {
            pujadores = TestUtil.findAll(em, Pujadores.class).get(0);
        }
        pujas.setPujadores(pujadores);
        return pujas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pujas createUpdatedEntity(EntityManager em) {
        Pujas pujas = new Pujas()
            .idEvento(UPDATED_ID_EVENTO)
            .idSubasta(UPDATED_ID_SUBASTA)
            .idLote(UPDATED_ID_LOTE)
            .valor(UPDATED_VALOR)
            .fechacreacion(UPDATED_FECHACREACION)
            .aceptadoGanador(UPDATED_ACEPTADO_GANADOR);
        // Add required entity
        Pujadores pujadores;
        if (TestUtil.findAll(em, Pujadores.class).isEmpty()) {
            pujadores = PujadoresResourceIT.createUpdatedEntity(em);
            em.persist(pujadores);
            em.flush();
        } else {
            pujadores = TestUtil.findAll(em, Pujadores.class).get(0);
        }
        pujas.setPujadores(pujadores);
        return pujas;
    }

    @BeforeEach
    public void initTest() {
        pujas = createEntity(em);
    }

    @Test
    @Transactional
    public void createPujas() throws Exception {
        int databaseSizeBeforeCreate = pujasRepository.findAll().size();

        // Create the Pujas
        restPujasMockMvc.perform(post("/api/pujas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujas)))
            .andExpect(status().isCreated());

        // Validate the Pujas in the database
        List<Pujas> pujasList = pujasRepository.findAll();
        assertThat(pujasList).hasSize(databaseSizeBeforeCreate + 1);
        Pujas testPujas = pujasList.get(pujasList.size() - 1);
        assertThat(testPujas.getIdEvento()).isEqualTo(DEFAULT_ID_EVENTO);
        assertThat(testPujas.getIdSubasta()).isEqualTo(DEFAULT_ID_SUBASTA);
        assertThat(testPujas.getIdLote()).isEqualTo(DEFAULT_ID_LOTE);
        assertThat(testPujas.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testPujas.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
        assertThat(testPujas.isAceptadoGanador()).isEqualTo(DEFAULT_ACEPTADO_GANADOR);
    }

    @Test
    @Transactional
    public void createPujasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pujasRepository.findAll().size();

        // Create the Pujas with an existing ID
        pujas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPujasMockMvc.perform(post("/api/pujas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujas)))
            .andExpect(status().isBadRequest());

        // Validate the Pujas in the database
        List<Pujas> pujasList = pujasRepository.findAll();
        assertThat(pujasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = pujasRepository.findAll().size();
        // set the field null
        pujas.setValor(null);

        // Create the Pujas, which fails.

        restPujasMockMvc.perform(post("/api/pujas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujas)))
            .andExpect(status().isBadRequest());

        List<Pujas> pujasList = pujasRepository.findAll();
        assertThat(pujasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPujas() throws Exception {
        // Initialize the database
        pujasRepository.saveAndFlush(pujas);

        // Get all the pujasList
        restPujasMockMvc.perform(get("/api/pujas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pujas.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEvento").value(hasItem(DEFAULT_ID_EVENTO)))
            .andExpect(jsonPath("$.[*].idSubasta").value(hasItem(DEFAULT_ID_SUBASTA)))
            .andExpect(jsonPath("$.[*].idLote").value(hasItem(DEFAULT_ID_LOTE)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())))
            .andExpect(jsonPath("$.[*].aceptadoGanador").value(hasItem(DEFAULT_ACEPTADO_GANADOR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPujas() throws Exception {
        // Initialize the database
        pujasRepository.saveAndFlush(pujas);

        // Get the pujas
        restPujasMockMvc.perform(get("/api/pujas/{id}", pujas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pujas.getId().intValue()))
            .andExpect(jsonPath("$.idEvento").value(DEFAULT_ID_EVENTO))
            .andExpect(jsonPath("$.idSubasta").value(DEFAULT_ID_SUBASTA))
            .andExpect(jsonPath("$.idLote").value(DEFAULT_ID_LOTE))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()))
            .andExpect(jsonPath("$.aceptadoGanador").value(DEFAULT_ACEPTADO_GANADOR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPujas() throws Exception {
        // Get the pujas
        restPujasMockMvc.perform(get("/api/pujas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePujas() throws Exception {
        // Initialize the database
        pujasRepository.saveAndFlush(pujas);

        int databaseSizeBeforeUpdate = pujasRepository.findAll().size();

        // Update the pujas
        Pujas updatedPujas = pujasRepository.findById(pujas.getId()).get();
        // Disconnect from session so that the updates on updatedPujas are not directly saved in db
        em.detach(updatedPujas);
        updatedPujas
            .idEvento(UPDATED_ID_EVENTO)
            .idSubasta(UPDATED_ID_SUBASTA)
            .idLote(UPDATED_ID_LOTE)
            .valor(UPDATED_VALOR)
            .fechacreacion(UPDATED_FECHACREACION)
            .aceptadoGanador(UPDATED_ACEPTADO_GANADOR);

        restPujasMockMvc.perform(put("/api/pujas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPujas)))
            .andExpect(status().isOk());

        // Validate the Pujas in the database
        List<Pujas> pujasList = pujasRepository.findAll();
        assertThat(pujasList).hasSize(databaseSizeBeforeUpdate);
        Pujas testPujas = pujasList.get(pujasList.size() - 1);
        assertThat(testPujas.getIdEvento()).isEqualTo(UPDATED_ID_EVENTO);
        assertThat(testPujas.getIdSubasta()).isEqualTo(UPDATED_ID_SUBASTA);
        assertThat(testPujas.getIdLote()).isEqualTo(UPDATED_ID_LOTE);
        assertThat(testPujas.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testPujas.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
        assertThat(testPujas.isAceptadoGanador()).isEqualTo(UPDATED_ACEPTADO_GANADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPujas() throws Exception {
        int databaseSizeBeforeUpdate = pujasRepository.findAll().size();

        // Create the Pujas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPujasMockMvc.perform(put("/api/pujas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujas)))
            .andExpect(status().isBadRequest());

        // Validate the Pujas in the database
        List<Pujas> pujasList = pujasRepository.findAll();
        assertThat(pujasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePujas() throws Exception {
        // Initialize the database
        pujasRepository.saveAndFlush(pujas);

        int databaseSizeBeforeDelete = pujasRepository.findAll().size();

        // Delete the pujas
        restPujasMockMvc.perform(delete("/api/pujas/{id}", pujas.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pujas> pujasList = pujasRepository.findAll();
        assertThat(pujasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
