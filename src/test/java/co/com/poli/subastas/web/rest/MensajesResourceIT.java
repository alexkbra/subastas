package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Mensajes;
import co.com.poli.subastas.repository.MensajesRepository;
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

import co.com.poli.subastas.domain.enumeration.TipoMensaje;
import co.com.poli.subastas.domain.enumeration.TipoCliente;
/**
 * Integration tests for the {@link MensajesResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class MensajesResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CUERPO = "AAAAAAAAAA";
    private static final String UPDATED_CUERPO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_LOTE = "BBBBBBBBBB";

    private static final TipoMensaje DEFAULT_TIPO_MENSAJE = TipoMensaje.GANDOR;
    private static final TipoMensaje UPDATED_TIPO_MENSAJE = TipoMensaje.INFO;

    private static final TipoCliente DEFAULT_TIPO_CLIENTE = TipoCliente.COMPRADOR;
    private static final TipoCliente UPDATED_TIPO_CLIENTE = TipoCliente.PUJADOR;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_IDCLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_IDCLIENTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final BigDecimal DEFAULT_VALOR_PAGAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PAGAR = new BigDecimal(2);

    @Autowired
    private MensajesRepository mensajesRepository;

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

    private MockMvc restMensajesMockMvc;

    private Mensajes mensajes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MensajesResource mensajesResource = new MensajesResource(mensajesRepository);
        this.restMensajesMockMvc = MockMvcBuilders.standaloneSetup(mensajesResource)
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
    public static Mensajes createEntity(EntityManager em) {
        Mensajes mensajes = new Mensajes()
            .titulo(DEFAULT_TITULO)
            .cuerpo(DEFAULT_CUERPO)
            .numeroLote(DEFAULT_NUMERO_LOTE)
            .tipoMensaje(DEFAULT_TIPO_MENSAJE)
            .tipoCliente(DEFAULT_TIPO_CLIENTE)
            .direccion(DEFAULT_DIRECCION)
            .idusuario(DEFAULT_IDUSUARIO)
            .idcliente(DEFAULT_IDCLIENTE)
            .activo(DEFAULT_ACTIVO)
            .valorPagar(DEFAULT_VALOR_PAGAR);
        return mensajes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensajes createUpdatedEntity(EntityManager em) {
        Mensajes mensajes = new Mensajes()
            .titulo(UPDATED_TITULO)
            .cuerpo(UPDATED_CUERPO)
            .numeroLote(UPDATED_NUMERO_LOTE)
            .tipoMensaje(UPDATED_TIPO_MENSAJE)
            .tipoCliente(UPDATED_TIPO_CLIENTE)
            .direccion(UPDATED_DIRECCION)
            .idusuario(UPDATED_IDUSUARIO)
            .idcliente(UPDATED_IDCLIENTE)
            .activo(UPDATED_ACTIVO)
            .valorPagar(UPDATED_VALOR_PAGAR);
        return mensajes;
    }

    @BeforeEach
    public void initTest() {
        mensajes = createEntity(em);
    }

    @Test
    @Transactional
    public void createMensajes() throws Exception {
        int databaseSizeBeforeCreate = mensajesRepository.findAll().size();

        // Create the Mensajes
        restMensajesMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajes)))
            .andExpect(status().isCreated());

        // Validate the Mensajes in the database
        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeCreate + 1);
        Mensajes testMensajes = mensajesList.get(mensajesList.size() - 1);
        assertThat(testMensajes.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testMensajes.getCuerpo()).isEqualTo(DEFAULT_CUERPO);
        assertThat(testMensajes.getNumeroLote()).isEqualTo(DEFAULT_NUMERO_LOTE);
        assertThat(testMensajes.getTipoMensaje()).isEqualTo(DEFAULT_TIPO_MENSAJE);
        assertThat(testMensajes.getTipoCliente()).isEqualTo(DEFAULT_TIPO_CLIENTE);
        assertThat(testMensajes.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testMensajes.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testMensajes.getIdcliente()).isEqualTo(DEFAULT_IDCLIENTE);
        assertThat(testMensajes.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testMensajes.getValorPagar()).isEqualTo(DEFAULT_VALOR_PAGAR);
    }

    @Test
    @Transactional
    public void createMensajesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mensajesRepository.findAll().size();

        // Create the Mensajes with an existing ID
        mensajes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajesMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajes)))
            .andExpect(status().isBadRequest());

        // Validate the Mensajes in the database
        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajesRepository.findAll().size();
        // set the field null
        mensajes.setTitulo(null);

        // Create the Mensajes, which fails.

        restMensajesMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajes)))
            .andExpect(status().isBadRequest());

        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajesRepository.findAll().size();
        // set the field null
        mensajes.setDireccion(null);

        // Create the Mensajes, which fails.

        restMensajesMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajes)))
            .andExpect(status().isBadRequest());

        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorPagarIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajesRepository.findAll().size();
        // set the field null
        mensajes.setValorPagar(null);

        // Create the Mensajes, which fails.

        restMensajesMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajes)))
            .andExpect(status().isBadRequest());

        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMensajes() throws Exception {
        // Initialize the database
        mensajesRepository.saveAndFlush(mensajes);

        // Get all the mensajesList
        restMensajesMockMvc.perform(get("/api/mensajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensajes.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].cuerpo").value(hasItem(DEFAULT_CUERPO.toString())))
            .andExpect(jsonPath("$.[*].numeroLote").value(hasItem(DEFAULT_NUMERO_LOTE)))
            .andExpect(jsonPath("$.[*].tipoMensaje").value(hasItem(DEFAULT_TIPO_MENSAJE.toString())))
            .andExpect(jsonPath("$.[*].tipoCliente").value(hasItem(DEFAULT_TIPO_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)))
            .andExpect(jsonPath("$.[*].idcliente").value(hasItem(DEFAULT_IDCLIENTE)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].valorPagar").value(hasItem(DEFAULT_VALOR_PAGAR.intValue())));
    }
    
    @Test
    @Transactional
    public void getMensajes() throws Exception {
        // Initialize the database
        mensajesRepository.saveAndFlush(mensajes);

        // Get the mensajes
        restMensajesMockMvc.perform(get("/api/mensajes/{id}", mensajes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensajes.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.cuerpo").value(DEFAULT_CUERPO.toString()))
            .andExpect(jsonPath("$.numeroLote").value(DEFAULT_NUMERO_LOTE))
            .andExpect(jsonPath("$.tipoMensaje").value(DEFAULT_TIPO_MENSAJE.toString()))
            .andExpect(jsonPath("$.tipoCliente").value(DEFAULT_TIPO_CLIENTE.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO))
            .andExpect(jsonPath("$.idcliente").value(DEFAULT_IDCLIENTE))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.valorPagar").value(DEFAULT_VALOR_PAGAR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMensajes() throws Exception {
        // Get the mensajes
        restMensajesMockMvc.perform(get("/api/mensajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMensajes() throws Exception {
        // Initialize the database
        mensajesRepository.saveAndFlush(mensajes);

        int databaseSizeBeforeUpdate = mensajesRepository.findAll().size();

        // Update the mensajes
        Mensajes updatedMensajes = mensajesRepository.findById(mensajes.getId()).get();
        // Disconnect from session so that the updates on updatedMensajes are not directly saved in db
        em.detach(updatedMensajes);
        updatedMensajes
            .titulo(UPDATED_TITULO)
            .cuerpo(UPDATED_CUERPO)
            .numeroLote(UPDATED_NUMERO_LOTE)
            .tipoMensaje(UPDATED_TIPO_MENSAJE)
            .tipoCliente(UPDATED_TIPO_CLIENTE)
            .direccion(UPDATED_DIRECCION)
            .idusuario(UPDATED_IDUSUARIO)
            .idcliente(UPDATED_IDCLIENTE)
            .activo(UPDATED_ACTIVO)
            .valorPagar(UPDATED_VALOR_PAGAR);

        restMensajesMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMensajes)))
            .andExpect(status().isOk());

        // Validate the Mensajes in the database
        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeUpdate);
        Mensajes testMensajes = mensajesList.get(mensajesList.size() - 1);
        assertThat(testMensajes.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testMensajes.getCuerpo()).isEqualTo(UPDATED_CUERPO);
        assertThat(testMensajes.getNumeroLote()).isEqualTo(UPDATED_NUMERO_LOTE);
        assertThat(testMensajes.getTipoMensaje()).isEqualTo(UPDATED_TIPO_MENSAJE);
        assertThat(testMensajes.getTipoCliente()).isEqualTo(UPDATED_TIPO_CLIENTE);
        assertThat(testMensajes.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testMensajes.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testMensajes.getIdcliente()).isEqualTo(UPDATED_IDCLIENTE);
        assertThat(testMensajes.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testMensajes.getValorPagar()).isEqualTo(UPDATED_VALOR_PAGAR);
    }

    @Test
    @Transactional
    public void updateNonExistingMensajes() throws Exception {
        int databaseSizeBeforeUpdate = mensajesRepository.findAll().size();

        // Create the Mensajes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajesMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajes)))
            .andExpect(status().isBadRequest());

        // Validate the Mensajes in the database
        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMensajes() throws Exception {
        // Initialize the database
        mensajesRepository.saveAndFlush(mensajes);

        int databaseSizeBeforeDelete = mensajesRepository.findAll().size();

        // Delete the mensajes
        restMensajesMockMvc.perform(delete("/api/mensajes/{id}", mensajes.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mensajes> mensajesList = mensajesRepository.findAll();
        assertThat(mensajesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
