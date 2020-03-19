package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Subastas;
import co.com.poli.subastas.domain.Eventos;
import co.com.poli.subastas.repository.EventosRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SubastasResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class SubastasResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DECRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHAINICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAINICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHAFINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAFINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TIMPO_RECLO_GANADOR = 6;
    private static final Integer UPDATED_TIMPO_RECLO_GANADOR = 12;

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALORINICIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALORINICIAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALORACTUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALORACTUAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALORTOPE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALORTOPE = new BigDecimal(2);

    private static final Boolean DEFAULT_PAGA_ANTICIPO = false;
    private static final Boolean UPDATED_PAGA_ANTICIPO = true;

    private static final BigDecimal DEFAULT_PESOBASEPORKG = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESOBASEPORKG = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PESOTOTALLOTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESOTOTALLOTE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_ANTICIPO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_ANTICIPO = new BigDecimal(2);

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO_ACTIVO = false;
    private static final Boolean UPDATED_ESTADO_ACTIVO = true;

    @Autowired
    private SubastasRepository subastasRepository;

    @Autowired
    private EventosRepository eventosRepository;

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

    private MockMvc restSubastasMockMvc;

    private Subastas subastas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubastasResource subastasResource = new SubastasResource(subastasRepository,eventosRepository);
        this.restSubastasMockMvc = MockMvcBuilders.standaloneSetup(subastasResource)
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
    public static Subastas createEntity(EntityManager em) {
        Subastas subastas = new Subastas()
            .nombre(DEFAULT_NOMBRE)
            .decripcion(DEFAULT_DECRIPCION)
            .fechainicio(DEFAULT_FECHAINICIO)
            .fechafinal(DEFAULT_FECHAFINAL)
            .timpoRecloGanador(DEFAULT_TIMPO_RECLO_GANADOR)
            .fechacreacion(DEFAULT_FECHACREACION)
            .valorinicial(DEFAULT_VALORINICIAL)
            .valoractual(DEFAULT_VALORACTUAL)
            .valortope(DEFAULT_VALORTOPE)
            .pagaAnticipo(DEFAULT_PAGA_ANTICIPO)
            .pesobaseporkg(DEFAULT_PESOBASEPORKG)
            .pesototallote(DEFAULT_PESOTOTALLOTE)
            .valorAnticipo(DEFAULT_VALOR_ANTICIPO)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(DEFAULT_VIDEO_URL)
            .estadoActivo(DEFAULT_ESTADO_ACTIVO);
        // Add required entity
        Eventos eventos;
        if (TestUtil.findAll(em, Eventos.class).isEmpty()) {
            eventos = EventosResourceIT.createEntity(em);
            em.persist(eventos);
            em.flush();
        } else {
            eventos = TestUtil.findAll(em, Eventos.class).get(0);
        }
        subastas.setEventos(eventos);
        return subastas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subastas createUpdatedEntity(EntityManager em) {
        Subastas subastas = new Subastas()
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .fechainicio(UPDATED_FECHAINICIO)
            .fechafinal(UPDATED_FECHAFINAL)
            .timpoRecloGanador(UPDATED_TIMPO_RECLO_GANADOR)
            .fechacreacion(UPDATED_FECHACREACION)
            .valorinicial(UPDATED_VALORINICIAL)
            .valoractual(UPDATED_VALORACTUAL)
            .valortope(UPDATED_VALORTOPE)
            .pagaAnticipo(UPDATED_PAGA_ANTICIPO)
            .pesobaseporkg(UPDATED_PESOBASEPORKG)
            .pesototallote(UPDATED_PESOTOTALLOTE)
            .valorAnticipo(UPDATED_VALOR_ANTICIPO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .estadoActivo(UPDATED_ESTADO_ACTIVO);
        // Add required entity
        Eventos eventos;
        if (TestUtil.findAll(em, Eventos.class).isEmpty()) {
            eventos = EventosResourceIT.createUpdatedEntity(em);
            em.persist(eventos);
            em.flush();
        } else {
            eventos = TestUtil.findAll(em, Eventos.class).get(0);
        }
        subastas.setEventos(eventos);
        return subastas;
    }

    @BeforeEach
    public void initTest() {
        subastas = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubastas() throws Exception {
        int databaseSizeBeforeCreate = subastasRepository.findAll().size();

        // Create the Subastas
        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isCreated());

        // Validate the Subastas in the database
        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeCreate + 1);
        Subastas testSubastas = subastasList.get(subastasList.size() - 1);
        assertThat(testSubastas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubastas.getDecripcion()).isEqualTo(DEFAULT_DECRIPCION);
        assertThat(testSubastas.getFechainicio()).isEqualTo(DEFAULT_FECHAINICIO);
        assertThat(testSubastas.getFechafinal()).isEqualTo(DEFAULT_FECHAFINAL);
        assertThat(testSubastas.getTimpoRecloGanador()).isEqualTo(DEFAULT_TIMPO_RECLO_GANADOR);
        assertThat(testSubastas.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
        assertThat(testSubastas.getValorinicial()).isEqualTo(DEFAULT_VALORINICIAL);
        assertThat(testSubastas.getValoractual()).isEqualTo(DEFAULT_VALORACTUAL);
        assertThat(testSubastas.getValortope()).isEqualTo(DEFAULT_VALORTOPE);
        assertThat(testSubastas.isPagaAnticipo()).isEqualTo(DEFAULT_PAGA_ANTICIPO);
        assertThat(testSubastas.getPesobaseporkg()).isEqualTo(DEFAULT_PESOBASEPORKG);
        assertThat(testSubastas.getPesototallote()).isEqualTo(DEFAULT_PESOTOTALLOTE);
        assertThat(testSubastas.getValorAnticipo()).isEqualTo(DEFAULT_VALOR_ANTICIPO);
        assertThat(testSubastas.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testSubastas.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testSubastas.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testSubastas.isEstadoActivo()).isEqualTo(DEFAULT_ESTADO_ACTIVO);
    }

    @Test
    @Transactional
    public void createSubastasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subastasRepository.findAll().size();

        // Create the Subastas with an existing ID
        subastas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        // Validate the Subastas in the database
        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setNombre(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechainicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setFechainicio(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechafinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setFechafinal(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimpoRecloGanadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setTimpoRecloGanador(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechacreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setFechacreacion(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorinicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setValorinicial(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValoractualIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setValoractual(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValortopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setValortope(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPagaAnticipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setPagaAnticipo(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = subastasRepository.findAll().size();
        // set the field null
        subastas.setEstadoActivo(null);

        // Create the Subastas, which fails.

        restSubastasMockMvc.perform(post("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubastas() throws Exception {
        // Initialize the database
        subastasRepository.saveAndFlush(subastas);

        // Get all the subastasList
        restSubastasMockMvc.perform(get("/api/subastas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subastas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].decripcion").value(hasItem(DEFAULT_DECRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechainicio").value(hasItem(DEFAULT_FECHAINICIO.toString())))
            .andExpect(jsonPath("$.[*].fechafinal").value(hasItem(DEFAULT_FECHAFINAL.toString())))
            .andExpect(jsonPath("$.[*].timpoRecloGanador").value(hasItem(DEFAULT_TIMPO_RECLO_GANADOR.toString())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())))
            .andExpect(jsonPath("$.[*].valorinicial").value(hasItem(DEFAULT_VALORINICIAL.intValue())))
            .andExpect(jsonPath("$.[*].valoractual").value(hasItem(DEFAULT_VALORACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].valortope").value(hasItem(DEFAULT_VALORTOPE.intValue())))
            .andExpect(jsonPath("$.[*].pagaAnticipo").value(hasItem(DEFAULT_PAGA_ANTICIPO.booleanValue())))
            .andExpect(jsonPath("$.[*].pesobaseporkg").value(hasItem(DEFAULT_PESOBASEPORKG.intValue())))
            .andExpect(jsonPath("$.[*].pesototallote").value(hasItem(DEFAULT_PESOTOTALLOTE.intValue())))
            .andExpect(jsonPath("$.[*].valorAnticipo").value(hasItem(DEFAULT_VALOR_ANTICIPO.intValue())))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].estadoActivo").value(hasItem(DEFAULT_ESTADO_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSubastas() throws Exception {
        // Initialize the database
        subastasRepository.saveAndFlush(subastas);

        // Get the subastas
        restSubastasMockMvc.perform(get("/api/subastas/{id}", subastas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subastas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.decripcion").value(DEFAULT_DECRIPCION.toString()))
            .andExpect(jsonPath("$.fechainicio").value(DEFAULT_FECHAINICIO.toString()))
            .andExpect(jsonPath("$.fechafinal").value(DEFAULT_FECHAFINAL.toString()))
            .andExpect(jsonPath("$.timpoRecloGanador").value(DEFAULT_TIMPO_RECLO_GANADOR.toString()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()))
            .andExpect(jsonPath("$.valorinicial").value(DEFAULT_VALORINICIAL.intValue()))
            .andExpect(jsonPath("$.valoractual").value(DEFAULT_VALORACTUAL.intValue()))
            .andExpect(jsonPath("$.valortope").value(DEFAULT_VALORTOPE.intValue()))
            .andExpect(jsonPath("$.pagaAnticipo").value(DEFAULT_PAGA_ANTICIPO.booleanValue()))
            .andExpect(jsonPath("$.pesobaseporkg").value(DEFAULT_PESOBASEPORKG.intValue()))
            .andExpect(jsonPath("$.pesototallote").value(DEFAULT_PESOTOTALLOTE.intValue()))
            .andExpect(jsonPath("$.valorAnticipo").value(DEFAULT_VALOR_ANTICIPO.intValue()))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.estadoActivo").value(DEFAULT_ESTADO_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSubastas() throws Exception {
        // Get the subastas
        restSubastasMockMvc.perform(get("/api/subastas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubastas() throws Exception {
        // Initialize the database
        subastasRepository.saveAndFlush(subastas);

        int databaseSizeBeforeUpdate = subastasRepository.findAll().size();

        // Update the subastas
        Subastas updatedSubastas = subastasRepository.findById(subastas.getId()).get();
        // Disconnect from session so that the updates on updatedSubastas are not directly saved in db
        em.detach(updatedSubastas);
        updatedSubastas
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .fechainicio(UPDATED_FECHAINICIO)
            .fechafinal(UPDATED_FECHAFINAL)
            .timpoRecloGanador(UPDATED_TIMPO_RECLO_GANADOR)
            .fechacreacion(UPDATED_FECHACREACION)
            .valorinicial(UPDATED_VALORINICIAL)
            .valoractual(UPDATED_VALORACTUAL)
            .valortope(UPDATED_VALORTOPE)
            .pagaAnticipo(UPDATED_PAGA_ANTICIPO)
            .pesobaseporkg(UPDATED_PESOBASEPORKG)
            .pesototallote(UPDATED_PESOTOTALLOTE)
            .valorAnticipo(UPDATED_VALOR_ANTICIPO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .estadoActivo(UPDATED_ESTADO_ACTIVO);

        restSubastasMockMvc.perform(put("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubastas)))
            .andExpect(status().isOk());

        // Validate the Subastas in the database
        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeUpdate);
        Subastas testSubastas = subastasList.get(subastasList.size() - 1);
        assertThat(testSubastas.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubastas.getDecripcion()).isEqualTo(UPDATED_DECRIPCION);
        assertThat(testSubastas.getFechainicio()).isEqualTo(UPDATED_FECHAINICIO);
        assertThat(testSubastas.getFechafinal()).isEqualTo(UPDATED_FECHAFINAL);
        assertThat(testSubastas.getTimpoRecloGanador()).isEqualTo(UPDATED_TIMPO_RECLO_GANADOR);
        assertThat(testSubastas.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
        assertThat(testSubastas.getValorinicial()).isEqualTo(UPDATED_VALORINICIAL);
        assertThat(testSubastas.getValoractual()).isEqualTo(UPDATED_VALORACTUAL);
        assertThat(testSubastas.getValortope()).isEqualTo(UPDATED_VALORTOPE);
        assertThat(testSubastas.isPagaAnticipo()).isEqualTo(UPDATED_PAGA_ANTICIPO);
        assertThat(testSubastas.getPesobaseporkg()).isEqualTo(UPDATED_PESOBASEPORKG);
        assertThat(testSubastas.getPesototallote()).isEqualTo(UPDATED_PESOTOTALLOTE);
        assertThat(testSubastas.getValorAnticipo()).isEqualTo(UPDATED_VALOR_ANTICIPO);
        assertThat(testSubastas.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testSubastas.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testSubastas.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testSubastas.isEstadoActivo()).isEqualTo(UPDATED_ESTADO_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingSubastas() throws Exception {
        int databaseSizeBeforeUpdate = subastasRepository.findAll().size();

        // Create the Subastas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubastasMockMvc.perform(put("/api/subastas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subastas)))
            .andExpect(status().isBadRequest());

        // Validate the Subastas in the database
        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubastas() throws Exception {
        // Initialize the database
        subastasRepository.saveAndFlush(subastas);

        int databaseSizeBeforeDelete = subastasRepository.findAll().size();

        // Delete the subastas
        restSubastasMockMvc.perform(delete("/api/subastas/{id}", subastas.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subastas> subastasList = subastasRepository.findAll();
        assertThat(subastasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
