package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Cliente;
import co.com.poli.subastas.domain.TipoDocumento;
import co.com.poli.subastas.domain.EstadoCliente;
import co.com.poli.subastas.repository.ClienteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static co.com.poli.subastas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class ClienteResourceIT {

    private static final String DEFAULT_NUMERO_DOCUMENTO = "5";
    private static final String UPDATED_NUMERO_DOCUMENTO = "6";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBREREPRESENTANTELEGAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBREREPRESENTANTELEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOCELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOCELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOFIJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOFIJO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOEMPRESARIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOEMPRESARIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOREPRESENTANTELEGAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOREPRESENTANTELEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONRESIDENCIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONRESIDENCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONEMPRESARIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONEMPRESARIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONREPRESENTANTELEGAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONREPRESENTANTELEGAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHANACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHANACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    private static final Boolean DEFAULT_ANONIMO = false;
    private static final Boolean UPDATED_ANONIMO = true;

    @Autowired
    private ClienteRepository clienteRepository;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteRepository);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .correo(DEFAULT_CORREO)
            .nombrerepresentantelegal(DEFAULT_NOMBREREPRESENTANTELEGAL)
            .telefonocelular(DEFAULT_TELEFONOCELULAR)
            .telefonofijo(DEFAULT_TELEFONOFIJO)
            .telefonoempresarial(DEFAULT_TELEFONOEMPRESARIAL)
            .telefonorepresentantelegal(DEFAULT_TELEFONOREPRESENTANTELEGAL)
            .direccionresidencial(DEFAULT_DIRECCIONRESIDENCIAL)
            .direccionempresarial(DEFAULT_DIRECCIONEMPRESARIAL)
            .direccionrepresentantelegal(DEFAULT_DIRECCIONREPRESENTANTELEGAL)
            .fechanacimiento(DEFAULT_FECHANACIMIENTO)
            .idusuario(DEFAULT_IDUSUARIO)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .idciudad(DEFAULT_IDCIUDAD)
            .anonimo(DEFAULT_ANONIMO);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createEntity(em);
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).get(0);
        }
        cliente.setTipoDocumento(tipoDocumento);
        // Add required entity
        EstadoCliente estadoCliente;
        if (TestUtil.findAll(em, EstadoCliente.class).isEmpty()) {
            estadoCliente = EstadoClienteResourceIT.createEntity(em);
            em.persist(estadoCliente);
            em.flush();
        } else {
            estadoCliente = TestUtil.findAll(em, EstadoCliente.class).get(0);
        }
        cliente.setEstadocliente(estadoCliente);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correo(UPDATED_CORREO)
            .nombrerepresentantelegal(UPDATED_NOMBREREPRESENTANTELEGAL)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonoempresarial(UPDATED_TELEFONOEMPRESARIAL)
            .telefonorepresentantelegal(UPDATED_TELEFONOREPRESENTANTELEGAL)
            .direccionresidencial(UPDATED_DIRECCIONRESIDENCIAL)
            .direccionempresarial(UPDATED_DIRECCIONEMPRESARIAL)
            .direccionrepresentantelegal(UPDATED_DIRECCIONREPRESENTANTELEGAL)
            .fechanacimiento(UPDATED_FECHANACIMIENTO)
            .idusuario(UPDATED_IDUSUARIO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .idciudad(UPDATED_IDCIUDAD)
            .anonimo(UPDATED_ANONIMO);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).get(0);
        }
        cliente.setTipoDocumento(tipoDocumento);
        // Add required entity
        EstadoCliente estadoCliente;
        if (TestUtil.findAll(em, EstadoCliente.class).isEmpty()) {
            estadoCliente = EstadoClienteResourceIT.createUpdatedEntity(em);
            em.persist(estadoCliente);
            em.flush();
        } else {
            estadoCliente = TestUtil.findAll(em, EstadoCliente.class).get(0);
        }
        cliente.setEstadocliente(estadoCliente);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testCliente.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testCliente.getNombrerepresentantelegal()).isEqualTo(DEFAULT_NOMBREREPRESENTANTELEGAL);
        assertThat(testCliente.getTelefonocelular()).isEqualTo(DEFAULT_TELEFONOCELULAR);
        assertThat(testCliente.getTelefonofijo()).isEqualTo(DEFAULT_TELEFONOFIJO);
        assertThat(testCliente.getTelefonoempresarial()).isEqualTo(DEFAULT_TELEFONOEMPRESARIAL);
        assertThat(testCliente.getTelefonorepresentantelegal()).isEqualTo(DEFAULT_TELEFONOREPRESENTANTELEGAL);
        assertThat(testCliente.getDireccionresidencial()).isEqualTo(DEFAULT_DIRECCIONRESIDENCIAL);
        assertThat(testCliente.getDireccionempresarial()).isEqualTo(DEFAULT_DIRECCIONEMPRESARIAL);
        assertThat(testCliente.getDireccionrepresentantelegal()).isEqualTo(DEFAULT_DIRECCIONREPRESENTANTELEGAL);
        assertThat(testCliente.getFechanacimiento()).isEqualTo(DEFAULT_FECHANACIMIENTO);
        assertThat(testCliente.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testCliente.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testCliente.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testCliente.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
        assertThat(testCliente.isAnonimo()).isEqualTo(DEFAULT_ANONIMO);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNumeroDocumento(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombre(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setApellido(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setCorreo(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombrerepresentantelegalIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombrerepresentantelegal(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonorepresentantelegalIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setTelefonorepresentantelegal(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionrepresentantelegalIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setDireccionrepresentantelegal(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechanacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setFechanacimiento(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].nombrerepresentantelegal").value(hasItem(DEFAULT_NOMBREREPRESENTANTELEGAL)))
            .andExpect(jsonPath("$.[*].telefonocelular").value(hasItem(DEFAULT_TELEFONOCELULAR)))
            .andExpect(jsonPath("$.[*].telefonofijo").value(hasItem(DEFAULT_TELEFONOFIJO)))
            .andExpect(jsonPath("$.[*].telefonoempresarial").value(hasItem(DEFAULT_TELEFONOEMPRESARIAL)))
            .andExpect(jsonPath("$.[*].telefonorepresentantelegal").value(hasItem(DEFAULT_TELEFONOREPRESENTANTELEGAL)))
            .andExpect(jsonPath("$.[*].direccionresidencial").value(hasItem(DEFAULT_DIRECCIONRESIDENCIAL)))
            .andExpect(jsonPath("$.[*].direccionempresarial").value(hasItem(DEFAULT_DIRECCIONEMPRESARIAL)))
            .andExpect(jsonPath("$.[*].direccionrepresentantelegal").value(hasItem(DEFAULT_DIRECCIONREPRESENTANTELEGAL)))
            .andExpect(jsonPath("$.[*].fechanacimiento").value(hasItem(DEFAULT_FECHANACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())))
            .andExpect(jsonPath("$.[*].anonimo").value(hasItem(DEFAULT_ANONIMO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.nombrerepresentantelegal").value(DEFAULT_NOMBREREPRESENTANTELEGAL))
            .andExpect(jsonPath("$.telefonocelular").value(DEFAULT_TELEFONOCELULAR))
            .andExpect(jsonPath("$.telefonofijo").value(DEFAULT_TELEFONOFIJO))
            .andExpect(jsonPath("$.telefonoempresarial").value(DEFAULT_TELEFONOEMPRESARIAL))
            .andExpect(jsonPath("$.telefonorepresentantelegal").value(DEFAULT_TELEFONOREPRESENTANTELEGAL))
            .andExpect(jsonPath("$.direccionresidencial").value(DEFAULT_DIRECCIONRESIDENCIAL))
            .andExpect(jsonPath("$.direccionempresarial").value(DEFAULT_DIRECCIONEMPRESARIAL))
            .andExpect(jsonPath("$.direccionrepresentantelegal").value(DEFAULT_DIRECCIONREPRESENTANTELEGAL))
            .andExpect(jsonPath("$.fechanacimiento").value(DEFAULT_FECHANACIMIENTO.toString()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()))
            .andExpect(jsonPath("$.anonimo").value(DEFAULT_ANONIMO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correo(UPDATED_CORREO)
            .nombrerepresentantelegal(UPDATED_NOMBREREPRESENTANTELEGAL)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonoempresarial(UPDATED_TELEFONOEMPRESARIAL)
            .telefonorepresentantelegal(UPDATED_TELEFONOREPRESENTANTELEGAL)
            .direccionresidencial(UPDATED_DIRECCIONRESIDENCIAL)
            .direccionempresarial(UPDATED_DIRECCIONEMPRESARIAL)
            .direccionrepresentantelegal(UPDATED_DIRECCIONREPRESENTANTELEGAL)
            .fechanacimiento(UPDATED_FECHANACIMIENTO)
            .idusuario(UPDATED_IDUSUARIO)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .idciudad(UPDATED_IDCIUDAD)
            .anonimo(UPDATED_ANONIMO);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testCliente.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testCliente.getNombrerepresentantelegal()).isEqualTo(UPDATED_NOMBREREPRESENTANTELEGAL);
        assertThat(testCliente.getTelefonocelular()).isEqualTo(UPDATED_TELEFONOCELULAR);
        assertThat(testCliente.getTelefonofijo()).isEqualTo(UPDATED_TELEFONOFIJO);
        assertThat(testCliente.getTelefonoempresarial()).isEqualTo(UPDATED_TELEFONOEMPRESARIAL);
        assertThat(testCliente.getTelefonorepresentantelegal()).isEqualTo(UPDATED_TELEFONOREPRESENTANTELEGAL);
        assertThat(testCliente.getDireccionresidencial()).isEqualTo(UPDATED_DIRECCIONRESIDENCIAL);
        assertThat(testCliente.getDireccionempresarial()).isEqualTo(UPDATED_DIRECCIONEMPRESARIAL);
        assertThat(testCliente.getDireccionrepresentantelegal()).isEqualTo(UPDATED_DIRECCIONREPRESENTANTELEGAL);
        assertThat(testCliente.getFechanacimiento()).isEqualTo(UPDATED_FECHANACIMIENTO);
        assertThat(testCliente.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testCliente.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testCliente.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testCliente.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
        assertThat(testCliente.isAnonimo()).isEqualTo(UPDATED_ANONIMO);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
