package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.SubastasApp;
import co.com.poli.subastas.domain.Contenido;
import co.com.poli.subastas.repository.ContenidoRepository;
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

import co.com.poli.subastas.domain.enumeration.TipoContenido;
import co.com.poli.subastas.domain.enumeration.EntidadContenido;
/**
 * Integration tests for the {@link ContenidoResource} REST controller.
 */
@SpringBootTest(classes = SubastasApp.class)
public class ContenidoResourceIT {

    private static final TipoContenido DEFAULT_TIPO = TipoContenido.VIDEO;
    private static final TipoContenido UPDATED_TIPO = TipoContenido.IMAGEN;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final EntidadContenido DEFAULT_ENTIDAD = EntidadContenido.Cliente;
    private static final EntidadContenido UPDATED_ENTIDAD = EntidadContenido.Pujadores;

    private static final String DEFAULT_ID_ENTIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ID_ENTIDAD = "BBBBBBBBBB";

    @Autowired
    private ContenidoRepository contenidoRepository;

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

    private MockMvc restContenidoMockMvc;

    private Contenido contenido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContenidoResource contenidoResource = new ContenidoResource(contenidoRepository);
        this.restContenidoMockMvc = MockMvcBuilders.standaloneSetup(contenidoResource)
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
    public static Contenido createEntity(EntityManager em) {
        Contenido contenido = new Contenido()
            .tipo(DEFAULT_TIPO)
            .url(DEFAULT_URL)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .texto(DEFAULT_TEXTO)
            .entidad(DEFAULT_ENTIDAD)
            .idEntidad(DEFAULT_ID_ENTIDAD);
        return contenido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contenido createUpdatedEntity(EntityManager em) {
        Contenido contenido = new Contenido()
            .tipo(UPDATED_TIPO)
            .url(UPDATED_URL)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .texto(UPDATED_TEXTO)
            .entidad(UPDATED_ENTIDAD)
            .idEntidad(UPDATED_ID_ENTIDAD);
        return contenido;
    }

    @BeforeEach
    public void initTest() {
        contenido = createEntity(em);
    }

    @Test
    @Transactional
    public void createContenido() throws Exception {
        int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

        // Create the Contenido
        restContenidoMockMvc.perform(post("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isCreated());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeCreate + 1);
        Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
        assertThat(testContenido.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testContenido.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testContenido.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testContenido.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testContenido.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testContenido.getEntidad()).isEqualTo(DEFAULT_ENTIDAD);
        assertThat(testContenido.getIdEntidad()).isEqualTo(DEFAULT_ID_ENTIDAD);
    }

    @Test
    @Transactional
    public void createContenidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

        // Create the Contenido with an existing ID
        contenido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContenidoMockMvc.perform(post("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isBadRequest());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContenidos() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        // Get all the contenidoList
        restContenidoMockMvc.perform(get("/api/contenidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenido.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].entidad").value(hasItem(DEFAULT_ENTIDAD.toString())))
            .andExpect(jsonPath("$.[*].idEntidad").value(hasItem(DEFAULT_ID_ENTIDAD)));
    }
    
    @Test
    @Transactional
    public void getContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        // Get the contenido
        restContenidoMockMvc.perform(get("/api/contenidos/{id}", contenido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contenido.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()))
            .andExpect(jsonPath("$.entidad").value(DEFAULT_ENTIDAD.toString()))
            .andExpect(jsonPath("$.idEntidad").value(DEFAULT_ID_ENTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingContenido() throws Exception {
        // Get the contenido
        restContenidoMockMvc.perform(get("/api/contenidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

        // Update the contenido
        Contenido updatedContenido = contenidoRepository.findById(contenido.getId()).get();
        // Disconnect from session so that the updates on updatedContenido are not directly saved in db
        em.detach(updatedContenido);
        updatedContenido
            .tipo(UPDATED_TIPO)
            .url(UPDATED_URL)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .texto(UPDATED_TEXTO)
            .entidad(UPDATED_ENTIDAD)
            .idEntidad(UPDATED_ID_ENTIDAD);

        restContenidoMockMvc.perform(put("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContenido)))
            .andExpect(status().isOk());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
        Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
        assertThat(testContenido.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testContenido.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContenido.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testContenido.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testContenido.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testContenido.getEntidad()).isEqualTo(UPDATED_ENTIDAD);
        assertThat(testContenido.getIdEntidad()).isEqualTo(UPDATED_ID_ENTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingContenido() throws Exception {
        int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

        // Create the Contenido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContenidoMockMvc.perform(put("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isBadRequest());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        int databaseSizeBeforeDelete = contenidoRepository.findAll().size();

        // Delete the contenido
        restContenidoMockMvc.perform(delete("/api/contenidos/{id}", contenido.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
