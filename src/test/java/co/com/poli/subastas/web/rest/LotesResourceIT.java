package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.Propietario;
import co.com.poli.subastas.domain.ClasificacionLote;
import co.com.poli.subastas.domain.Subastas;
import co.com.poli.subastas.repository.LotesRepository;
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
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LotesResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class LotesResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DECRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_RAZA = "AAAAAAAAAA";
    private static final String UPDATED_RAZA = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_ANIMALES = 1;
    private static final Integer UPDATED_CANTIDAD_ANIMALES = 2;

    private static final BigDecimal DEFAULT_PESOPROMEDIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESOPROMEDIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PESOTOTALLOTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESOTOTALLOTE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PESOBASEPORKG = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESOBASEPORKG = new BigDecimal(2);

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    @Autowired
    private LotesRepository lotesRepository;

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

    private MockMvc restLotesMockMvc;

    private Lotes lotes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LotesResource lotesResource = new LotesResource(lotesRepository, subastasRepository);
        this.restLotesMockMvc = MockMvcBuilders.standaloneSetup(lotesResource)
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
    public static Lotes createEntity(EntityManager em) {
        Lotes lotes = new Lotes()
            .numero(DEFAULT_NUMERO)
            .nombre(DEFAULT_NOMBRE)
            .decripcion(DEFAULT_DECRIPCION)
            .raza(DEFAULT_RAZA)
            .cantidadAnimales(DEFAULT_CANTIDAD_ANIMALES)
            .pesopromedio(DEFAULT_PESOPROMEDIO)
            .pesototallote(DEFAULT_PESOTOTALLOTE)
            .pesobaseporkg(DEFAULT_PESOBASEPORKG)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(DEFAULT_VIDEO_URL)
            .idciudad(DEFAULT_IDCIUDAD);
        // Add required entity
        Propietario propietario;
        if (TestUtil.findAll(em, Propietario.class).isEmpty()) {
            propietario = PropietarioResourceIT.createEntity(em);
            em.persist(propietario);
            em.flush();
        } else {
            propietario = TestUtil.findAll(em, Propietario.class).get(0);
        }
        lotes.setPropietario(propietario);
        // Add required entity
        ClasificacionLote clasificacionLote;
        if (TestUtil.findAll(em, ClasificacionLote.class).isEmpty()) {
            clasificacionLote = ClasificacionLoteResourceIT.createEntity(em);
            em.persist(clasificacionLote);
            em.flush();
        } else {
            clasificacionLote = TestUtil.findAll(em, ClasificacionLote.class).get(0);
        }
        lotes.setClasificacionlote(clasificacionLote);
        // Add required entity
        Subastas subastas;
        if (TestUtil.findAll(em, Subastas.class).isEmpty()) {
            subastas = SubastasResourceIT.createEntity(em);
            em.persist(subastas);
            em.flush();
        } else {
            subastas = TestUtil.findAll(em, Subastas.class).get(0);
        }
        lotes.setSubastas(subastas);
        return lotes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lotes createUpdatedEntity(EntityManager em) {
        Lotes lotes = new Lotes()
            .numero(UPDATED_NUMERO)
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .raza(UPDATED_RAZA)
            .cantidadAnimales(UPDATED_CANTIDAD_ANIMALES)
            .pesopromedio(UPDATED_PESOPROMEDIO)
            .pesototallote(UPDATED_PESOTOTALLOTE)
            .pesobaseporkg(UPDATED_PESOBASEPORKG)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .idciudad(UPDATED_IDCIUDAD);
        // Add required entity
        Propietario propietario;
        if (TestUtil.findAll(em, Propietario.class).isEmpty()) {
            propietario = PropietarioResourceIT.createUpdatedEntity(em);
            em.persist(propietario);
            em.flush();
        } else {
            propietario = TestUtil.findAll(em, Propietario.class).get(0);
        }
        lotes.setPropietario(propietario);
        // Add required entity
        ClasificacionLote clasificacionLote;
        if (TestUtil.findAll(em, ClasificacionLote.class).isEmpty()) {
            clasificacionLote = ClasificacionLoteResourceIT.createUpdatedEntity(em);
            em.persist(clasificacionLote);
            em.flush();
        } else {
            clasificacionLote = TestUtil.findAll(em, ClasificacionLote.class).get(0);
        }
        lotes.setClasificacionlote(clasificacionLote);
        // Add required entity
        Subastas subastas;
        if (TestUtil.findAll(em, Subastas.class).isEmpty()) {
            subastas = SubastasResourceIT.createUpdatedEntity(em);
            em.persist(subastas);
            em.flush();
        } else {
            subastas = TestUtil.findAll(em, Subastas.class).get(0);
        }
        lotes.setSubastas(subastas);
        return lotes;
    }

    @BeforeEach
    public void initTest() {
        lotes = createEntity(em);
    }

    @Test
    @Transactional
    public void createLotes() throws Exception {
        int databaseSizeBeforeCreate = lotesRepository.findAll().size();

        // Create the Lotes
        restLotesMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotes)))
            .andExpect(status().isCreated());

        // Validate the Lotes in the database
        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeCreate + 1);
        Lotes testLotes = lotesList.get(lotesList.size() - 1);
        assertThat(testLotes.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLotes.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLotes.getDecripcion()).isEqualTo(DEFAULT_DECRIPCION);
        assertThat(testLotes.getRaza()).isEqualTo(DEFAULT_RAZA);
        assertThat(testLotes.getCantidadAnimales()).isEqualTo(DEFAULT_CANTIDAD_ANIMALES);
        assertThat(testLotes.getPesopromedio()).isEqualTo(DEFAULT_PESOPROMEDIO);
        assertThat(testLotes.getPesototallote()).isEqualTo(DEFAULT_PESOTOTALLOTE);
        assertThat(testLotes.getPesobaseporkg()).isEqualTo(DEFAULT_PESOBASEPORKG);
        assertThat(testLotes.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testLotes.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testLotes.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testLotes.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
    }

    @Test
    @Transactional
    public void createLotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lotesRepository.findAll().size();

        // Create the Lotes with an existing ID
        lotes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotesMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotes)))
            .andExpect(status().isBadRequest());

        // Validate the Lotes in the database
        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = lotesRepository.findAll().size();
        // set the field null
        lotes.setNumero(null);

        // Create the Lotes, which fails.

        restLotesMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotes)))
            .andExpect(status().isBadRequest());

        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = lotesRepository.findAll().size();
        // set the field null
        lotes.setNombre(null);

        // Create the Lotes, which fails.

        restLotesMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotes)))
            .andExpect(status().isBadRequest());

        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadAnimalesIsRequired() throws Exception {
        int databaseSizeBeforeTest = lotesRepository.findAll().size();
        // set the field null
        lotes.setCantidadAnimales(null);

        // Create the Lotes, which fails.

        restLotesMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotes)))
            .andExpect(status().isBadRequest());

        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLotes() throws Exception {
        // Initialize the database
        lotesRepository.saveAndFlush(lotes);

        // Get all the lotesList
        restLotesMockMvc.perform(get("/api/lotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].decripcion").value(hasItem(DEFAULT_DECRIPCION.toString())))
            .andExpect(jsonPath("$.[*].raza").value(hasItem(DEFAULT_RAZA)))
            .andExpect(jsonPath("$.[*].cantidadAnimales").value(hasItem(DEFAULT_CANTIDAD_ANIMALES)))
            .andExpect(jsonPath("$.[*].pesopromedio").value(hasItem(DEFAULT_PESOPROMEDIO.intValue())))
            .andExpect(jsonPath("$.[*].pesototallote").value(hasItem(DEFAULT_PESOTOTALLOTE.intValue())))
            .andExpect(jsonPath("$.[*].pesobaseporkg").value(hasItem(DEFAULT_PESOBASEPORKG.intValue())))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())));
    }
    
    @Test
    @Transactional
    public void getLotes() throws Exception {
        // Initialize the database
        lotesRepository.saveAndFlush(lotes);

        // Get the lotes
        restLotesMockMvc.perform(get("/api/lotes/{id}", lotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lotes.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.decripcion").value(DEFAULT_DECRIPCION.toString()))
            .andExpect(jsonPath("$.raza").value(DEFAULT_RAZA))
            .andExpect(jsonPath("$.cantidadAnimales").value(DEFAULT_CANTIDAD_ANIMALES))
            .andExpect(jsonPath("$.pesopromedio").value(DEFAULT_PESOPROMEDIO.intValue()))
            .andExpect(jsonPath("$.pesototallote").value(DEFAULT_PESOTOTALLOTE.intValue()))
            .andExpect(jsonPath("$.pesobaseporkg").value(DEFAULT_PESOBASEPORKG.intValue()))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLotes() throws Exception {
        // Get the lotes
        restLotesMockMvc.perform(get("/api/lotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLotes() throws Exception {
        // Initialize the database
        lotesRepository.saveAndFlush(lotes);

        int databaseSizeBeforeUpdate = lotesRepository.findAll().size();

        // Update the lotes
        Lotes updatedLotes = lotesRepository.findById(lotes.getId()).get();
        // Disconnect from session so that the updates on updatedLotes are not directly saved in db
        em.detach(updatedLotes);
        updatedLotes
            .numero(UPDATED_NUMERO)
            .nombre(UPDATED_NOMBRE)
            .decripcion(UPDATED_DECRIPCION)
            .raza(UPDATED_RAZA)
            .cantidadAnimales(UPDATED_CANTIDAD_ANIMALES)
            .pesopromedio(UPDATED_PESOPROMEDIO)
            .pesototallote(UPDATED_PESOTOTALLOTE)
            .pesobaseporkg(UPDATED_PESOBASEPORKG)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL)
            .idciudad(UPDATED_IDCIUDAD);

        restLotesMockMvc.perform(put("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLotes)))
            .andExpect(status().isOk());

        // Validate the Lotes in the database
        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeUpdate);
        Lotes testLotes = lotesList.get(lotesList.size() - 1);
        assertThat(testLotes.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLotes.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLotes.getDecripcion()).isEqualTo(UPDATED_DECRIPCION);
        assertThat(testLotes.getRaza()).isEqualTo(UPDATED_RAZA);
        assertThat(testLotes.getCantidadAnimales()).isEqualTo(UPDATED_CANTIDAD_ANIMALES);
        assertThat(testLotes.getPesopromedio()).isEqualTo(UPDATED_PESOPROMEDIO);
        assertThat(testLotes.getPesototallote()).isEqualTo(UPDATED_PESOTOTALLOTE);
        assertThat(testLotes.getPesobaseporkg()).isEqualTo(UPDATED_PESOBASEPORKG);
        assertThat(testLotes.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testLotes.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testLotes.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testLotes.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingLotes() throws Exception {
        int databaseSizeBeforeUpdate = lotesRepository.findAll().size();

        // Create the Lotes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotesMockMvc.perform(put("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotes)))
            .andExpect(status().isBadRequest());

        // Validate the Lotes in the database
        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLotes() throws Exception {
        // Initialize the database
        lotesRepository.saveAndFlush(lotes);

        int databaseSizeBeforeDelete = lotesRepository.findAll().size();

        // Delete the lotes
        restLotesMockMvc.perform(delete("/api/lotes/{id}", lotes.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lotes> lotesList = lotesRepository.findAll();
        assertThat(lotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
