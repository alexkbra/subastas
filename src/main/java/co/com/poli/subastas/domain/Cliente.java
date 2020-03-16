package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 5)
    @Max(value = 20)
    @Column(name = "numero_documento", nullable = false)
    private Integer numeroDocumento;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255, nullable = false)
    private String correo;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombrerepresentantelegal", length = 100, nullable = false)
    private String nombrerepresentantelegal;

    @Size(min = 5, max = 45)
    @Column(name = "telefonocelular", length = 45)
    private String telefonocelular;

    @Size(min = 5, max = 45)
    @Column(name = "telefonofijo", length = 45)
    private String telefonofijo;

    @Size(min = 5, max = 45)
    @Column(name = "telefonoempresarial", length = 45)
    private String telefonoempresarial;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "telefonorepresentantelegal", length = 45, nullable = false)
    private String telefonorepresentantelegal;

    @Size(min = 5, max = 100)
    @Column(name = "direccionresidencial", length = 100)
    private String direccionresidencial;

    @Size(min = 5, max = 100)
    @Column(name = "direccionempresarial", length = 100)
    private String direccionempresarial;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "direccionrepresentantelegal", length = 100, nullable = false)
    private String direccionrepresentantelegal;

    @NotNull
    @Column(name = "fechanacimiento", nullable = false)
    private LocalDate fechanacimiento;

    @Column(name = "idusuario")
    private String idusuario;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @Column(name = "idciudad")
    private Long idciudad;

    @Column(name = "anonimo")
    private Boolean anonimo;

    @OneToMany(mappedBy = "cliente")
    private Set<Pujadores> pujadores = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientes")
    private TipoDocumento tipoDocumento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientes")
    private EstadoCliente estadocliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public Cliente numeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Cliente apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public Cliente correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombrerepresentantelegal() {
        return nombrerepresentantelegal;
    }

    public Cliente nombrerepresentantelegal(String nombrerepresentantelegal) {
        this.nombrerepresentantelegal = nombrerepresentantelegal;
        return this;
    }

    public void setNombrerepresentantelegal(String nombrerepresentantelegal) {
        this.nombrerepresentantelegal = nombrerepresentantelegal;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public Cliente telefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
        return this;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public Cliente telefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
        return this;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonoempresarial() {
        return telefonoempresarial;
    }

    public Cliente telefonoempresarial(String telefonoempresarial) {
        this.telefonoempresarial = telefonoempresarial;
        return this;
    }

    public void setTelefonoempresarial(String telefonoempresarial) {
        this.telefonoempresarial = telefonoempresarial;
    }

    public String getTelefonorepresentantelegal() {
        return telefonorepresentantelegal;
    }

    public Cliente telefonorepresentantelegal(String telefonorepresentantelegal) {
        this.telefonorepresentantelegal = telefonorepresentantelegal;
        return this;
    }

    public void setTelefonorepresentantelegal(String telefonorepresentantelegal) {
        this.telefonorepresentantelegal = telefonorepresentantelegal;
    }

    public String getDireccionresidencial() {
        return direccionresidencial;
    }

    public Cliente direccionresidencial(String direccionresidencial) {
        this.direccionresidencial = direccionresidencial;
        return this;
    }

    public void setDireccionresidencial(String direccionresidencial) {
        this.direccionresidencial = direccionresidencial;
    }

    public String getDireccionempresarial() {
        return direccionempresarial;
    }

    public Cliente direccionempresarial(String direccionempresarial) {
        this.direccionempresarial = direccionempresarial;
        return this;
    }

    public void setDireccionempresarial(String direccionempresarial) {
        this.direccionempresarial = direccionempresarial;
    }

    public String getDireccionrepresentantelegal() {
        return direccionrepresentantelegal;
    }

    public Cliente direccionrepresentantelegal(String direccionrepresentantelegal) {
        this.direccionrepresentantelegal = direccionrepresentantelegal;
        return this;
    }

    public void setDireccionrepresentantelegal(String direccionrepresentantelegal) {
        this.direccionrepresentantelegal = direccionrepresentantelegal;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public Cliente fechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
        return this;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Cliente idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Cliente imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Cliente imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Cliente idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public Boolean isAnonimo() {
        return anonimo;
    }

    public Cliente anonimo(Boolean anonimo) {
        this.anonimo = anonimo;
        return this;
    }

    public void setAnonimo(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    public Set<Pujadores> getPujadores() {
        return pujadores;
    }

    public Cliente pujadores(Set<Pujadores> pujadores) {
        this.pujadores = pujadores;
        return this;
    }

    public Cliente addPujadores(Pujadores pujadores) {
        this.pujadores.add(pujadores);
        pujadores.setCliente(this);
        return this;
    }

    public Cliente removePujadores(Pujadores pujadores) {
        this.pujadores.remove(pujadores);
        pujadores.setCliente(null);
        return this;
    }

    public void setPujadores(Set<Pujadores> pujadores) {
        this.pujadores = pujadores;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Cliente tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public EstadoCliente getEstadocliente() {
        return estadocliente;
    }

    public Cliente estadocliente(EstadoCliente estadoCliente) {
        this.estadocliente = estadoCliente;
        return this;
    }

    public void setEstadocliente(EstadoCliente estadoCliente) {
        this.estadocliente = estadoCliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", numeroDocumento=" + getNumeroDocumento() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", nombrerepresentantelegal='" + getNombrerepresentantelegal() + "'" +
            ", telefonocelular='" + getTelefonocelular() + "'" +
            ", telefonofijo='" + getTelefonofijo() + "'" +
            ", telefonoempresarial='" + getTelefonoempresarial() + "'" +
            ", telefonorepresentantelegal='" + getTelefonorepresentantelegal() + "'" +
            ", direccionresidencial='" + getDireccionresidencial() + "'" +
            ", direccionempresarial='" + getDireccionempresarial() + "'" +
            ", direccionrepresentantelegal='" + getDireccionrepresentantelegal() + "'" +
            ", fechanacimiento='" + getFechanacimiento() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", idciudad=" + getIdciudad() +
            ", anonimo='" + isAnonimo() + "'" +
            "}";
    }
}
