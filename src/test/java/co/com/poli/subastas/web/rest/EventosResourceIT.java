package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Eventos;
import co.com.poli.subastas.repository.EventosRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EventosResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class EventosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DECRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHAINICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAINICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHAFINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAFINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO_ACTIVO = false;
    private static final Boolean UPDATED_ESTADO_ACTIVO = true;

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

    private MockMvc restEventosMockMvc;

    private Eventos eventos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventosResource eventosResource = new EventosResource(eventosRepository);
        this.restEventosMockMvc = MockMvcBuilders.standaloneSetup(eventosResource)
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
    public static Eventos createEntity(EntityManager em) {
        Eventos eventos = new Eventos()
            .nombre(DEFAULT_NOMBRE)
            .decripcion(DEFAULT_DECRIPCION)
            .fechainicio(DEFAULT_FECHAINICIO)
            .fechafinal(DEFAULT_FECHAFINAL)
            .fechacreacion(DEFAULT_FECHACREACION)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(DEFAULT_VIDEO_URL)
            .idciudad(DEFAULT_IDCIUDAD)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .estadoActivo(DEFAULT_ESTADO_ACTIVO);
        return eventos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eventos createUpdatedEntity(EntityManager em) {
        Eventos eventos = new Eventos()
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .fechainicio(UPDATED_FECHAINICIO)
            .fechafinal(UPDATED_FECHAFINAL)
            .fechacreacion(UPDATED_FECHACREACION)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .idciudad(UPDATED_IDCIUDAD)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .estadoActivo(UPDATED_ESTADO_ACTIVO);
        return eventos;
    }

    @BeforeEach
    public void initTest() {
        eventos = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventos() throws Exception {
        int databaseSizeBeforeCreate = eventosRepository.findAll().size();

        // Create the Eventos
        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isCreated());

        // Validate the Eventos in the database
        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeCreate + 1);
        Eventos testEventos = eventosList.get(eventosList.size() - 1);
        assertThat(testEventos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEventos.getDecripcion()).isEqualTo(DEFAULT_DECRIPCION);
        assertThat(testEventos.getFechainicio()).isEqualTo(DEFAULT_FECHAINICIO);
        assertThat(testEventos.getFechafinal()).isEqualTo(DEFAULT_FECHAFINAL);
        assertThat(testEventos.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
        assertThat(testEventos.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testEventos.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testEventos.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testEventos.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
        assertThat(testEventos.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testEventos.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testEventos.isEstadoActivo()).isEqualTo(DEFAULT_ESTADO_ACTIVO);
    }

    @Test
    @Transactional
    public void createEventosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventosRepository.findAll().size();

        // Create the Eventos with an existing ID
        eventos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        // Validate the Eventos in the database
        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventosRepository.findAll().size();
        // set the field null
        eventos.setNombre(null);

        // Create the Eventos, which fails.

        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechainicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventosRepository.findAll().size();
        // set the field null
        eventos.setFechainicio(null);

        // Create the Eventos, which fails.

        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechafinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventosRepository.findAll().size();
        // set the field null
        eventos.setFechafinal(null);

        // Create the Eventos, which fails.

        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechacreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventosRepository.findAll().size();
        // set the field null
        eventos.setFechacreacion(null);

        // Create the Eventos, which fails.

        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventosRepository.findAll().size();
        // set the field null
        eventos.setEstadoActivo(null);

        // Create the Eventos, which fails.

        restEventosMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventos() throws Exception {
        // Initialize the database
        eventosRepository.saveAndFlush(eventos);

        // Get all the eventosList
        restEventosMockMvc.perform(get("/api/eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].decripcion").value(hasItem(DEFAULT_DECRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechainicio").value(hasItem(DEFAULT_FECHAINICIO.toString())))
            .andExpect(jsonPath("$.[*].fechafinal").value(hasItem(DEFAULT_FECHAFINAL.toString())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].estadoActivo").value(hasItem(DEFAULT_ESTADO_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEventos() throws Exception {
        // Initialize the database
        eventosRepository.saveAndFlush(eventos);

        // Get the eventos
        restEventosMockMvc.perform(get("/api/eventos/{id}", eventos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.decripcion").value(DEFAULT_DECRIPCION.toString()))
            .andExpect(jsonPath("$.fechainicio").value(DEFAULT_FECHAINICIO.toString()))
            .andExpect(jsonPath("$.fechafinal").value(DEFAULT_FECHAFINAL.toString()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.estadoActivo").value(DEFAULT_ESTADO_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEventos() throws Exception {
        // Get the eventos
        restEventosMockMvc.perform(get("/api/eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventos() throws Exception {
        // Initialize the database
        eventosRepository.saveAndFlush(eventos);

        int databaseSizeBeforeUpdate = eventosRepository.findAll().size();

        // Update the eventos
        Eventos updatedEventos = eventosRepository.findById(eventos.getId()).get();
        // Disconnect from session so that the updates on updatedEventos are not directly saved in db
        em.detach(updatedEventos);
        updatedEventos
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .fechainicio(UPDATED_FECHAINICIO)
            .fechafinal(UPDATED_FECHAFINAL)
            .fechacreacion(UPDATED_FECHACREACION)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .idciudad(UPDATED_IDCIUDAD)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .estadoActivo(UPDATED_ESTADO_ACTIVO);

        restEventosMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventos)))
            .andExpect(status().isOk());

        // Validate the Eventos in the database
        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeUpdate);
        Eventos testEventos = eventosList.get(eventosList.size() - 1);
        assertThat(testEventos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEventos.getDecripcion()).isEqualTo(UPDATED_DECRIPCION);
        assertThat(testEventos.getFechainicio()).isEqualTo(UPDATED_FECHAINICIO);
        assertThat(testEventos.getFechafinal()).isEqualTo(UPDATED_FECHAFINAL);
        assertThat(testEventos.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
        assertThat(testEventos.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testEventos.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testEventos.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testEventos.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
        assertThat(testEventos.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testEventos.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testEventos.isEstadoActivo()).isEqualTo(UPDATED_ESTADO_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingEventos() throws Exception {
        int databaseSizeBeforeUpdate = eventosRepository.findAll().size();

        // Create the Eventos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventosMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventos)))
            .andExpect(status().isBadRequest());

        // Validate the Eventos in the database
        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventos() throws Exception {
        // Initialize the database
        eventosRepository.saveAndFlush(eventos);

        int databaseSizeBeforeDelete = eventosRepository.findAll().size();

        // Delete the eventos
        restEventosMockMvc.perform(delete("/api/eventos/{id}", eventos.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eventos> eventosList = eventosRepository.findAll();
        assertThat(eventosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
