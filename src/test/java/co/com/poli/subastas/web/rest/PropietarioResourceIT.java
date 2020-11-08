package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Propietario;
import co.com.poli.subastas.domain.TipoDocumento;
import co.com.poli.subastas.repository.PropietarioRepository;
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
 * Integration tests for the {@link PropietarioResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class PropietarioResourceIT {

    private static final String DEFAULT_NUMERO_DOCUMENTO = "555555";
    private static final String UPDATED_NUMERO_DOCUMENTO = "66666";

    private static final String DEFAULT_NOMBRE_O_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_O_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOCELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOCELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOFIJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOFIJO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOEMPRESARIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOEMPRESARIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONRESIDENCIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONRESIDENCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONEMPRESARIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONEMPRESARIAL = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    @Autowired
    private PropietarioRepository propietarioRepository;

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

    private MockMvc restPropietarioMockMvc;

    private Propietario propietario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropietarioResource propietarioResource = new PropietarioResource(propietarioRepository);
        this.restPropietarioMockMvc = MockMvcBuilders.standaloneSetup(propietarioResource)
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
    public static Propietario createEntity(EntityManager em) {
        Propietario propietario = new Propietario()
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .nombreORazonSocial(DEFAULT_NOMBRE_O_RAZON_SOCIAL)
            .correo(DEFAULT_CORREO)
            .telefonocelular(DEFAULT_TELEFONOCELULAR)
            .telefonofijo(DEFAULT_TELEFONOFIJO)
            .telefonoempresarial(DEFAULT_TELEFONOEMPRESARIAL)
            .direccionresidencial(DEFAULT_DIRECCIONRESIDENCIAL)
            .direccionempresarial(DEFAULT_DIRECCIONEMPRESARIAL)
            .idusuario(DEFAULT_IDUSUARIO)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .idciudad(DEFAULT_IDCIUDAD);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createEntity(em);
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).get(0);
        }
        propietario.setTipoDocumento(tipoDocumento);
        return propietario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propietario createUpdatedEntity(EntityManager em) {
        Propietario propietario = new Propietario()
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .nombreORazonSocial(UPDATED_NOMBRE_O_RAZON_SOCIAL)
            .correo(UPDATED_CORREO)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonoempresarial(UPDATED_TELEFONOEMPRESARIAL)
            .direccionresidencial(UPDATED_DIRECCIONRESIDENCIAL)
            .direccionempresarial(UPDATED_DIRECCIONEMPRESARIAL)
            .idusuario(UPDATED_IDUSUARIO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .idciudad(UPDATED_IDCIUDAD);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).get(0);
        }
        propietario.setTipoDocumento(tipoDocumento);
        return propietario;
    }

    @BeforeEach
    public void initTest() {
        propietario = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropietario() throws Exception {
        int databaseSizeBeforeCreate = propietarioRepository.findAll().size();

        // Create the Propietario
        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isCreated());

        // Validate the Propietario in the database
        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeCreate + 1);
        Propietario testPropietario = propietarioList.get(propietarioList.size() - 1);
        assertThat(testPropietario.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testPropietario.getNombreORazonSocial()).isEqualTo(DEFAULT_NOMBRE_O_RAZON_SOCIAL);
        assertThat(testPropietario.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testPropietario.getTelefonocelular()).isEqualTo(DEFAULT_TELEFONOCELULAR);
        assertThat(testPropietario.getTelefonofijo()).isEqualTo(DEFAULT_TELEFONOFIJO);
        assertThat(testPropietario.getTelefonoempresarial()).isEqualTo(DEFAULT_TELEFONOEMPRESARIAL);
        assertThat(testPropietario.getDireccionresidencial()).isEqualTo(DEFAULT_DIRECCIONRESIDENCIAL);
        assertThat(testPropietario.getDireccionempresarial()).isEqualTo(DEFAULT_DIRECCIONEMPRESARIAL);
        assertThat(testPropietario.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testPropietario.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testPropietario.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testPropietario.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
    }

    @Test
    @Transactional
    public void createPropietarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propietarioRepository.findAll().size();

        // Create the Propietario with an existing ID
        propietario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        // Validate the Propietario in the database
        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = propietarioRepository.findAll().size();
        // set the field null
        propietario.setNumeroDocumento(null);

        // Create the Propietario, which fails.

        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreORazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = propietarioRepository.findAll().size();
        // set the field null
        propietario.setNombreORazonSocial(null);

        // Create the Propietario, which fails.

        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = propietarioRepository.findAll().size();
        // set the field null
        propietario.setCorreo(null);

        // Create the Propietario, which fails.

        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonocelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = propietarioRepository.findAll().size();
        // set the field null
        propietario.setTelefonocelular(null);

        // Create the Propietario, which fails.

        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionresidencialIsRequired() throws Exception {
        int databaseSizeBeforeTest = propietarioRepository.findAll().size();
        // set the field null
        propietario.setDireccionresidencial(null);

        // Create the Propietario, which fails.

        restPropietarioMockMvc.perform(post("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPropietarios() throws Exception {
        // Initialize the database
        propietarioRepository.saveAndFlush(propietario);

        // Get all the propietarioList
        restPropietarioMockMvc.perform(get("/api/propietarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propietario.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].nombreORazonSocial").value(hasItem(DEFAULT_NOMBRE_O_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].telefonocelular").value(hasItem(DEFAULT_TELEFONOCELULAR)))
            .andExpect(jsonPath("$.[*].telefonofijo").value(hasItem(DEFAULT_TELEFONOFIJO)))
            .andExpect(jsonPath("$.[*].telefonoempresarial").value(hasItem(DEFAULT_TELEFONOEMPRESARIAL)))
            .andExpect(jsonPath("$.[*].direccionresidencial").value(hasItem(DEFAULT_DIRECCIONRESIDENCIAL)))
            .andExpect(jsonPath("$.[*].direccionempresarial").value(hasItem(DEFAULT_DIRECCIONEMPRESARIAL)))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())));
    }
    
    @Test
    @Transactional
    public void getPropietario() throws Exception {
        // Initialize the database
        propietarioRepository.saveAndFlush(propietario);

        // Get the propietario
        restPropietarioMockMvc.perform(get("/api/propietarios/{id}", propietario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(propietario.getId().intValue()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.nombreORazonSocial").value(DEFAULT_NOMBRE_O_RAZON_SOCIAL))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.telefonocelular").value(DEFAULT_TELEFONOCELULAR))
            .andExpect(jsonPath("$.telefonofijo").value(DEFAULT_TELEFONOFIJO))
            .andExpect(jsonPath("$.telefonoempresarial").value(DEFAULT_TELEFONOEMPRESARIAL))
            .andExpect(jsonPath("$.direccionresidencial").value(DEFAULT_DIRECCIONRESIDENCIAL))
            .andExpect(jsonPath("$.direccionempresarial").value(DEFAULT_DIRECCIONEMPRESARIAL))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPropietario() throws Exception {
        // Get the propietario
        restPropietarioMockMvc.perform(get("/api/propietarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropietario() throws Exception {
        // Initialize the database
        propietarioRepository.saveAndFlush(propietario);

        int databaseSizeBeforeUpdate = propietarioRepository.findAll().size();

        // Update the propietario
        Propietario updatedPropietario = propietarioRepository.findById(propietario.getId()).get();
        // Disconnect from session so that the updates on updatedPropietario are not directly saved in db
        em.detach(updatedPropietario);
        updatedPropietario
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .nombreORazonSocial(UPDATED_NOMBRE_O_RAZON_SOCIAL)
            .correo(UPDATED_CORREO)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonoempresarial(UPDATED_TELEFONOEMPRESARIAL)
            .direccionresidencial(UPDATED_DIRECCIONRESIDENCIAL)
            .direccionempresarial(UPDATED_DIRECCIONEMPRESARIAL)
            .idusuario(UPDATED_IDUSUARIO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .idciudad(UPDATED_IDCIUDAD);

        restPropietarioMockMvc.perform(put("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPropietario)))
            .andExpect(status().isOk());

        // Validate the Propietario in the database
        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeUpdate);
        Propietario testPropietario = propietarioList.get(propietarioList.size() - 1);
        assertThat(testPropietario.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testPropietario.getNombreORazonSocial()).isEqualTo(UPDATED_NOMBRE_O_RAZON_SOCIAL);
        assertThat(testPropietario.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testPropietario.getTelefonocelular()).isEqualTo(UPDATED_TELEFONOCELULAR);
        assertThat(testPropietario.getTelefonofijo()).isEqualTo(UPDATED_TELEFONOFIJO);
        assertThat(testPropietario.getTelefonoempresarial()).isEqualTo(UPDATED_TELEFONOEMPRESARIAL);
        assertThat(testPropietario.getDireccionresidencial()).isEqualTo(UPDATED_DIRECCIONRESIDENCIAL);
        assertThat(testPropietario.getDireccionempresarial()).isEqualTo(UPDATED_DIRECCIONEMPRESARIAL);
        assertThat(testPropietario.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testPropietario.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testPropietario.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testPropietario.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingPropietario() throws Exception {
        int databaseSizeBeforeUpdate = propietarioRepository.findAll().size();

        // Create the Propietario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropietarioMockMvc.perform(put("/api/propietarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propietario)))
            .andExpect(status().isBadRequest());

        // Validate the Propietario in the database
        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropietario() throws Exception {
        // Initialize the database
        propietarioRepository.saveAndFlush(propietario);

        int databaseSizeBeforeDelete = propietarioRepository.findAll().size();

        // Delete the propietario
        restPropietarioMockMvc.perform(delete("/api/propietarios/{id}", propietario.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Propietario> propietarioList = propietarioRepository.findAll();
        assertThat(propietarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
