package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Animales;
import co.com.poli.subastas.domain.Razas;
import co.com.poli.subastas.repository.AnimalesRepository;
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

import co.com.poli.subastas.domain.enumeration.Sexos;
/**
 * Integration tests for the {@link AnimalesResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class AnimalesResourceIT {

    private static final BigDecimal DEFAULT_PESOKG = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESOKG = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Sexos DEFAULT_SEXO = Sexos.MASCULINO;
    private static final Sexos UPDATED_SEXO = Sexos.FEMENINO;

    private static final String DEFAULT_PROCEDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_PROPIETARIO = "AAAAAAAAAA";
    private static final String UPDATED_PROPIETARIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    @Autowired
    private AnimalesRepository animalesRepository;

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

    private MockMvc restAnimalesMockMvc;

    private Animales animales;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalesResource animalesResource = new AnimalesResource(animalesRepository);
        this.restAnimalesMockMvc = MockMvcBuilders.standaloneSetup(animalesResource)
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
    public static Animales createEntity(EntityManager em) {
        Animales animales = new Animales()
            .pesokg(DEFAULT_PESOKG)
            .descripcion(DEFAULT_DESCRIPCION)
            .sexo(DEFAULT_SEXO)
            .procedencia(DEFAULT_PROCEDENCIA)
            .propietario(DEFAULT_PROPIETARIO)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(DEFAULT_VIDEO_URL);
        // Add required entity
        Razas razas;
        if (TestUtil.findAll(em, Razas.class).isEmpty()) {
            razas = RazasResourceIT.createEntity(em);
            em.persist(razas);
            em.flush();
        } else {
            razas = TestUtil.findAll(em, Razas.class).get(0);
        }
        animales.setRazas(razas);
        return animales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Animales createUpdatedEntity(EntityManager em) {
        Animales animales = new Animales()
            .pesokg(UPDATED_PESOKG)
            .descripcion(UPDATED_DESCRIPCION)
            .sexo(UPDATED_SEXO)
            .procedencia(UPDATED_PROCEDENCIA)
            .propietario(UPDATED_PROPIETARIO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL);
        // Add required entity
        Razas razas;
        if (TestUtil.findAll(em, Razas.class).isEmpty()) {
            razas = RazasResourceIT.createUpdatedEntity(em);
            em.persist(razas);
            em.flush();
        } else {
            razas = TestUtil.findAll(em, Razas.class).get(0);
        }
        animales.setRazas(razas);
        return animales;
    }

    @BeforeEach
    public void initTest() {
        animales = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimales() throws Exception {
        int databaseSizeBeforeCreate = animalesRepository.findAll().size();

        // Create the Animales
        restAnimalesMockMvc.perform(post("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animales)))
            .andExpect(status().isCreated());

        // Validate the Animales in the database
        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeCreate + 1);
        Animales testAnimales = animalesList.get(animalesList.size() - 1);
        assertThat(testAnimales.getPesokg()).isEqualTo(DEFAULT_PESOKG);
        assertThat(testAnimales.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAnimales.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testAnimales.getProcedencia()).isEqualTo(DEFAULT_PROCEDENCIA);
        assertThat(testAnimales.getPropietario()).isEqualTo(DEFAULT_PROPIETARIO);
        assertThat(testAnimales.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testAnimales.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testAnimales.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
    }

    @Test
    @Transactional
    public void createAnimalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalesRepository.findAll().size();

        // Create the Animales with an existing ID
        animales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalesMockMvc.perform(post("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animales)))
            .andExpect(status().isBadRequest());

        // Validate the Animales in the database
        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPesokgIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalesRepository.findAll().size();
        // set the field null
        animales.setPesokg(null);

        // Create the Animales, which fails.

        restAnimalesMockMvc.perform(post("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animales)))
            .andExpect(status().isBadRequest());

        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcedenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalesRepository.findAll().size();
        // set the field null
        animales.setProcedencia(null);

        // Create the Animales, which fails.

        restAnimalesMockMvc.perform(post("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animales)))
            .andExpect(status().isBadRequest());

        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropietarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalesRepository.findAll().size();
        // set the field null
        animales.setPropietario(null);

        // Create the Animales, which fails.

        restAnimalesMockMvc.perform(post("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animales)))
            .andExpect(status().isBadRequest());

        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnimales() throws Exception {
        // Initialize the database
        animalesRepository.saveAndFlush(animales);

        // Get all the animalesList
        restAnimalesMockMvc.perform(get("/api/animales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animales.getId().intValue())))
            .andExpect(jsonPath("$.[*].pesokg").value(hasItem(DEFAULT_PESOKG.intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].procedencia").value(hasItem(DEFAULT_PROCEDENCIA)))
            .andExpect(jsonPath("$.[*].propietario").value(hasItem(DEFAULT_PROPIETARIO)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)));
    }
    
    @Test
    @Transactional
    public void getAnimales() throws Exception {
        // Initialize the database
        animalesRepository.saveAndFlush(animales);

        // Get the animales
        restAnimalesMockMvc.perform(get("/api/animales/{id}", animales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(animales.getId().intValue()))
            .andExpect(jsonPath("$.pesokg").value(DEFAULT_PESOKG.intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.procedencia").value(DEFAULT_PROCEDENCIA))
            .andExpect(jsonPath("$.propietario").value(DEFAULT_PROPIETARIO))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL));
    }

    @Test
    @Transactional
    public void getNonExistingAnimales() throws Exception {
        // Get the animales
        restAnimalesMockMvc.perform(get("/api/animales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimales() throws Exception {
        // Initialize the database
        animalesRepository.saveAndFlush(animales);

        int databaseSizeBeforeUpdate = animalesRepository.findAll().size();

        // Update the animales
        Animales updatedAnimales = animalesRepository.findById(animales.getId()).get();
        // Disconnect from session so that the updates on updatedAnimales are not directly saved in db
        em.detach(updatedAnimales);
        updatedAnimales
            .pesokg(UPDATED_PESOKG)
            .descripcion(UPDATED_DESCRIPCION)
            .sexo(UPDATED_SEXO)
            .procedencia(UPDATED_PROCEDENCIA)
            .propietario(UPDATED_PROPIETARIO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .videoUrl(UPDATED_VIDEO_URL);

        restAnimalesMockMvc.perform(put("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimales)))
            .andExpect(status().isOk());

        // Validate the Animales in the database
        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeUpdate);
        Animales testAnimales = animalesList.get(animalesList.size() - 1);
        assertThat(testAnimales.getPesokg()).isEqualTo(UPDATED_PESOKG);
        assertThat(testAnimales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAnimales.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testAnimales.getProcedencia()).isEqualTo(UPDATED_PROCEDENCIA);
        assertThat(testAnimales.getPropietario()).isEqualTo(UPDATED_PROPIETARIO);
        assertThat(testAnimales.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testAnimales.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testAnimales.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimales() throws Exception {
        int databaseSizeBeforeUpdate = animalesRepository.findAll().size();

        // Create the Animales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalesMockMvc.perform(put("/api/animales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animales)))
            .andExpect(status().isBadRequest());

        // Validate the Animales in the database
        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimales() throws Exception {
        // Initialize the database
        animalesRepository.saveAndFlush(animales);

        int databaseSizeBeforeDelete = animalesRepository.findAll().size();

        // Delete the animales
        restAnimalesMockMvc.perform(delete("/api/animales/{id}", animales.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Animales> animalesList = animalesRepository.findAll();
        assertThat(animalesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
