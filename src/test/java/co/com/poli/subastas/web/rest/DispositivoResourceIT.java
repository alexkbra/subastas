package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Dispositivo;
import co.com.poli.subastas.repository.DispositivoRepository;
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

/**
 * Integration tests for the {@link DispositivoResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class DispositivoResourceIT {

    private static final String DEFAULT_ID_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_ID_EVENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_SUBASTA = "AAAAAAAAAA";
    private static final String UPDATED_ID_SUBASTA = "BBBBBBBBBB";

    private static final String DEFAULT_ID_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_LOTE = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_IDCLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_IDCLIENTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_DISPOSITIVO = "AAAAAAAAAA";
    private static final String UPDATED_DISPOSITIVO = "BBBBBBBBBB";

    @Autowired
    private DispositivoRepository dispositivoRepository;

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

    private MockMvc restDispositivoMockMvc;

    private Dispositivo dispositivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DispositivoResource dispositivoResource = new DispositivoResource(dispositivoRepository);
        this.restDispositivoMockMvc = MockMvcBuilders.standaloneSetup(dispositivoResource)
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
    public static Dispositivo createEntity(EntityManager em) {
        Dispositivo dispositivo = new Dispositivo()
            .idEvento(DEFAULT_ID_EVENTO)
            .idSubasta(DEFAULT_ID_SUBASTA)
            .idLote(DEFAULT_ID_LOTE)
            .idusuario(DEFAULT_IDUSUARIO)
            .idcliente(DEFAULT_IDCLIENTE)
            .activo(DEFAULT_ACTIVO)
            .dispositivo(DEFAULT_DISPOSITIVO);
        return dispositivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispositivo createUpdatedEntity(EntityManager em) {
        Dispositivo dispositivo = new Dispositivo()
            .idEvento(UPDATED_ID_EVENTO)
            .idSubasta(UPDATED_ID_SUBASTA)
            .idLote(UPDATED_ID_LOTE)
            .idusuario(UPDATED_IDUSUARIO)
            .idcliente(UPDATED_IDCLIENTE)
            .activo(UPDATED_ACTIVO)
            .dispositivo(UPDATED_DISPOSITIVO);
        return dispositivo;
    }

    @BeforeEach
    public void initTest() {
        dispositivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispositivo() throws Exception {
        int databaseSizeBeforeCreate = dispositivoRepository.findAll().size();

        // Create the Dispositivo
        restDispositivoMockMvc.perform(post("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispositivo)))
            .andExpect(status().isCreated());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeCreate + 1);
        Dispositivo testDispositivo = dispositivoList.get(dispositivoList.size() - 1);
        assertThat(testDispositivo.getIdEvento()).isEqualTo(DEFAULT_ID_EVENTO);
        assertThat(testDispositivo.getIdSubasta()).isEqualTo(DEFAULT_ID_SUBASTA);
        assertThat(testDispositivo.getIdLote()).isEqualTo(DEFAULT_ID_LOTE);
        assertThat(testDispositivo.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testDispositivo.getIdcliente()).isEqualTo(DEFAULT_IDCLIENTE);
        assertThat(testDispositivo.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testDispositivo.getDispositivo()).isEqualTo(DEFAULT_DISPOSITIVO);
    }

    @Test
    @Transactional
    public void createDispositivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispositivoRepository.findAll().size();

        // Create the Dispositivo with an existing ID
        dispositivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositivoMockMvc.perform(post("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispositivo)))
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDispositivos() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        // Get all the dispositivoList
        restDispositivoMockMvc.perform(get("/api/dispositivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEvento").value(hasItem(DEFAULT_ID_EVENTO)))
            .andExpect(jsonPath("$.[*].idSubasta").value(hasItem(DEFAULT_ID_SUBASTA)))
            .andExpect(jsonPath("$.[*].idLote").value(hasItem(DEFAULT_ID_LOTE)))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)))
            .andExpect(jsonPath("$.[*].idcliente").value(hasItem(DEFAULT_IDCLIENTE)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].dispositivo").value(hasItem(DEFAULT_DISPOSITIVO)));
    }
    
    @Test
    @Transactional
    public void getDispositivo() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        // Get the dispositivo
        restDispositivoMockMvc.perform(get("/api/dispositivos/{id}", dispositivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispositivo.getId().intValue()))
            .andExpect(jsonPath("$.idEvento").value(DEFAULT_ID_EVENTO))
            .andExpect(jsonPath("$.idSubasta").value(DEFAULT_ID_SUBASTA))
            .andExpect(jsonPath("$.idLote").value(DEFAULT_ID_LOTE))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO))
            .andExpect(jsonPath("$.idcliente").value(DEFAULT_IDCLIENTE))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.dispositivo").value(DEFAULT_DISPOSITIVO));
    }

    @Test
    @Transactional
    public void getNonExistingDispositivo() throws Exception {
        // Get the dispositivo
        restDispositivoMockMvc.perform(get("/api/dispositivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispositivo() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        int databaseSizeBeforeUpdate = dispositivoRepository.findAll().size();

        // Update the dispositivo
        Dispositivo updatedDispositivo = dispositivoRepository.findById(dispositivo.getId()).get();
        // Disconnect from session so that the updates on updatedDispositivo are not directly saved in db
        em.detach(updatedDispositivo);
        updatedDispositivo
            .idEvento(UPDATED_ID_EVENTO)
            .idSubasta(UPDATED_ID_SUBASTA)
            .idLote(UPDATED_ID_LOTE)
            .idusuario(UPDATED_IDUSUARIO)
            .idcliente(UPDATED_IDCLIENTE)
            .activo(UPDATED_ACTIVO)
            .dispositivo(UPDATED_DISPOSITIVO);

        restDispositivoMockMvc.perform(put("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDispositivo)))
            .andExpect(status().isOk());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeUpdate);
        Dispositivo testDispositivo = dispositivoList.get(dispositivoList.size() - 1);
        assertThat(testDispositivo.getIdEvento()).isEqualTo(UPDATED_ID_EVENTO);
        assertThat(testDispositivo.getIdSubasta()).isEqualTo(UPDATED_ID_SUBASTA);
        assertThat(testDispositivo.getIdLote()).isEqualTo(UPDATED_ID_LOTE);
        assertThat(testDispositivo.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testDispositivo.getIdcliente()).isEqualTo(UPDATED_IDCLIENTE);
        assertThat(testDispositivo.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testDispositivo.getDispositivo()).isEqualTo(UPDATED_DISPOSITIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingDispositivo() throws Exception {
        int databaseSizeBeforeUpdate = dispositivoRepository.findAll().size();

        // Create the Dispositivo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivoMockMvc.perform(put("/api/dispositivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispositivo)))
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDispositivo() throws Exception {
        // Initialize the database
        dispositivoRepository.saveAndFlush(dispositivo);

        int databaseSizeBeforeDelete = dispositivoRepository.findAll().size();

        // Delete the dispositivo
        restDispositivoMockMvc.perform(delete("/api/dispositivos/{id}", dispositivo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dispositivo> dispositivoList = dispositivoRepository.findAll();
        assertThat(dispositivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
