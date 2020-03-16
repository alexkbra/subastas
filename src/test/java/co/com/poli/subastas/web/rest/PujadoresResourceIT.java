package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.domain.Cliente;
import co.com.poli.subastas.repository.PujadoresRepository;
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

import co.com.poli.subastas.domain.enumeration.EstadoPujadores;
/**
 * Integration tests for the {@link PujadoresResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class PujadoresResourceIT {

    private static final String DEFAULT_ID_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_ID_EVENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_SUBASTA = "AAAAAAAAAA";
    private static final String UPDATED_ID_SUBASTA = "BBBBBBBBBB";

    private static final String DEFAULT_ID_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_LOTE = "BBBBBBBBBB";

    private static final String DEFAULT_NROCONSIGNACION = "AAAAAAAAAA";
    private static final String UPDATED_NROCONSIGNACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBREBANCO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBREBANCO = "BBBBBBBBBB";

    private static final EstadoPujadores DEFAULT_ESTADO = EstadoPujadores.ACTIVO;
    private static final EstadoPujadores UPDATED_ESTADO = EstadoPujadores.NOAUTORIZADO;

    private static final Boolean DEFAULT_PAGO_ACEPTADO = false;
    private static final Boolean UPDATED_PAGO_ACEPTADO = true;

    @Autowired
    private PujadoresRepository pujadoresRepository;

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

    private MockMvc restPujadoresMockMvc;

    private Pujadores pujadores;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PujadoresResource pujadoresResource = new PujadoresResource(pujadoresRepository);
        this.restPujadoresMockMvc = MockMvcBuilders.standaloneSetup(pujadoresResource)
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
    public static Pujadores createEntity(EntityManager em) {
        Pujadores pujadores = new Pujadores()
            .idEvento(DEFAULT_ID_EVENTO)
            .idSubasta(DEFAULT_ID_SUBASTA)
            .idLote(DEFAULT_ID_LOTE)
            .nroconsignacion(DEFAULT_NROCONSIGNACION)
            .nombrebanco(DEFAULT_NOMBREBANCO)
            .estado(DEFAULT_ESTADO)
            .pagoAceptado(DEFAULT_PAGO_ACEPTADO);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        pujadores.setCliente(cliente);
        return pujadores;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pujadores createUpdatedEntity(EntityManager em) {
        Pujadores pujadores = new Pujadores()
            .idEvento(UPDATED_ID_EVENTO)
            .idSubasta(UPDATED_ID_SUBASTA)
            .idLote(UPDATED_ID_LOTE)
            .nroconsignacion(UPDATED_NROCONSIGNACION)
            .nombrebanco(UPDATED_NOMBREBANCO)
            .estado(UPDATED_ESTADO)
            .pagoAceptado(UPDATED_PAGO_ACEPTADO);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        pujadores.setCliente(cliente);
        return pujadores;
    }

    @BeforeEach
    public void initTest() {
        pujadores = createEntity(em);
    }

    @Test
    @Transactional
    public void createPujadores() throws Exception {
        int databaseSizeBeforeCreate = pujadoresRepository.findAll().size();

        // Create the Pujadores
        restPujadoresMockMvc.perform(post("/api/pujadores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujadores)))
            .andExpect(status().isCreated());

        // Validate the Pujadores in the database
        List<Pujadores> pujadoresList = pujadoresRepository.findAll();
        assertThat(pujadoresList).hasSize(databaseSizeBeforeCreate + 1);
        Pujadores testPujadores = pujadoresList.get(pujadoresList.size() - 1);
        assertThat(testPujadores.getIdEvento()).isEqualTo(DEFAULT_ID_EVENTO);
        assertThat(testPujadores.getIdSubasta()).isEqualTo(DEFAULT_ID_SUBASTA);
        assertThat(testPujadores.getIdLote()).isEqualTo(DEFAULT_ID_LOTE);
        assertThat(testPujadores.getNroconsignacion()).isEqualTo(DEFAULT_NROCONSIGNACION);
        assertThat(testPujadores.getNombrebanco()).isEqualTo(DEFAULT_NOMBREBANCO);
        assertThat(testPujadores.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPujadores.isPagoAceptado()).isEqualTo(DEFAULT_PAGO_ACEPTADO);
    }

    @Test
    @Transactional
    public void createPujadoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pujadoresRepository.findAll().size();

        // Create the Pujadores with an existing ID
        pujadores.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPujadoresMockMvc.perform(post("/api/pujadores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujadores)))
            .andExpect(status().isBadRequest());

        // Validate the Pujadores in the database
        List<Pujadores> pujadoresList = pujadoresRepository.findAll();
        assertThat(pujadoresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPujadores() throws Exception {
        // Initialize the database
        pujadoresRepository.saveAndFlush(pujadores);

        // Get all the pujadoresList
        restPujadoresMockMvc.perform(get("/api/pujadores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pujadores.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEvento").value(hasItem(DEFAULT_ID_EVENTO)))
            .andExpect(jsonPath("$.[*].idSubasta").value(hasItem(DEFAULT_ID_SUBASTA)))
            .andExpect(jsonPath("$.[*].idLote").value(hasItem(DEFAULT_ID_LOTE)))
            .andExpect(jsonPath("$.[*].nroconsignacion").value(hasItem(DEFAULT_NROCONSIGNACION)))
            .andExpect(jsonPath("$.[*].nombrebanco").value(hasItem(DEFAULT_NOMBREBANCO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].pagoAceptado").value(hasItem(DEFAULT_PAGO_ACEPTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPujadores() throws Exception {
        // Initialize the database
        pujadoresRepository.saveAndFlush(pujadores);

        // Get the pujadores
        restPujadoresMockMvc.perform(get("/api/pujadores/{id}", pujadores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pujadores.getId().intValue()))
            .andExpect(jsonPath("$.idEvento").value(DEFAULT_ID_EVENTO))
            .andExpect(jsonPath("$.idSubasta").value(DEFAULT_ID_SUBASTA))
            .andExpect(jsonPath("$.idLote").value(DEFAULT_ID_LOTE))
            .andExpect(jsonPath("$.nroconsignacion").value(DEFAULT_NROCONSIGNACION))
            .andExpect(jsonPath("$.nombrebanco").value(DEFAULT_NOMBREBANCO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.pagoAceptado").value(DEFAULT_PAGO_ACEPTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPujadores() throws Exception {
        // Get the pujadores
        restPujadoresMockMvc.perform(get("/api/pujadores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePujadores() throws Exception {
        // Initialize the database
        pujadoresRepository.saveAndFlush(pujadores);

        int databaseSizeBeforeUpdate = pujadoresRepository.findAll().size();

        // Update the pujadores
        Pujadores updatedPujadores = pujadoresRepository.findById(pujadores.getId()).get();
        // Disconnect from session so that the updates on updatedPujadores are not directly saved in db
        em.detach(updatedPujadores);
        updatedPujadores
            .idEvento(UPDATED_ID_EVENTO)
            .idSubasta(UPDATED_ID_SUBASTA)
            .idLote(UPDATED_ID_LOTE)
            .nroconsignacion(UPDATED_NROCONSIGNACION)
            .nombrebanco(UPDATED_NOMBREBANCO)
            .estado(UPDATED_ESTADO)
            .pagoAceptado(UPDATED_PAGO_ACEPTADO);

        restPujadoresMockMvc.perform(put("/api/pujadores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPujadores)))
            .andExpect(status().isOk());

        // Validate the Pujadores in the database
        List<Pujadores> pujadoresList = pujadoresRepository.findAll();
        assertThat(pujadoresList).hasSize(databaseSizeBeforeUpdate);
        Pujadores testPujadores = pujadoresList.get(pujadoresList.size() - 1);
        assertThat(testPujadores.getIdEvento()).isEqualTo(UPDATED_ID_EVENTO);
        assertThat(testPujadores.getIdSubasta()).isEqualTo(UPDATED_ID_SUBASTA);
        assertThat(testPujadores.getIdLote()).isEqualTo(UPDATED_ID_LOTE);
        assertThat(testPujadores.getNroconsignacion()).isEqualTo(UPDATED_NROCONSIGNACION);
        assertThat(testPujadores.getNombrebanco()).isEqualTo(UPDATED_NOMBREBANCO);
        assertThat(testPujadores.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPujadores.isPagoAceptado()).isEqualTo(UPDATED_PAGO_ACEPTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPujadores() throws Exception {
        int databaseSizeBeforeUpdate = pujadoresRepository.findAll().size();

        // Create the Pujadores

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPujadoresMockMvc.perform(put("/api/pujadores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pujadores)))
            .andExpect(status().isBadRequest());

        // Validate the Pujadores in the database
        List<Pujadores> pujadoresList = pujadoresRepository.findAll();
        assertThat(pujadoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePujadores() throws Exception {
        // Initialize the database
        pujadoresRepository.saveAndFlush(pujadores);

        int databaseSizeBeforeDelete = pujadoresRepository.findAll().size();

        // Delete the pujadores
        restPujadoresMockMvc.perform(delete("/api/pujadores/{id}", pujadores.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pujadores> pujadoresList = pujadoresRepository.findAll();
        assertThat(pujadoresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
