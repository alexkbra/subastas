package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Propietario.
 */
@Entity
@Table(name = "propietario")
public class Propietario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "numero_documento", length = 20, nullable = false)
    private String numeroDocumento;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre_o_razon_social", length = 100, nullable = false)
    private String nombreORazonSocial;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255, nullable = false)
    private String correo;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "telefonocelular", length = 45, nullable = false)
    private String telefonocelular;

    @Size(min = 5, max = 45)
    @Column(name = "telefonofijo", length = 45)
    private String telefonofijo;

    @Size(min = 5, max = 45)
    @Column(name = "telefonoempresarial", length = 45)
    private String telefonoempresarial;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "direccionresidencial", length = 100, nullable = false)
    private String direccionresidencial;

    @Size(min = 5, max = 100)
    @Column(name = "direccionempresarial", length = 100)
    private String direccionempresarial;

    @Column(name = "idusuario")
    private String idusuario;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @Column(name = "idciudad")
    private Long idciudad;

    @OneToMany(mappedBy = "propietario")
    private Set<Lotes> lotes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("propietarios")
    private TipoDocumento tipoDocumento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public Propietario numeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }
    
    public String getNombreORazonSocial() {
        return nombreORazonSocial;
    }

    public Propietario nombreORazonSocial(String nombreORazonSocial) {
        this.nombreORazonSocial = nombreORazonSocial;
        return this;
    }

    public void setNombreORazonSocial(String nombreORazonSocial) {
        this.nombreORazonSocial = nombreORazonSocial;
    }

    public String getCorreo() {
        return correo;
    }

    public Propietario correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public Propietario telefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
        return this;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public Propietario telefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
        return this;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonoempresarial() {
        return telefonoempresarial;
    }

    public Propietario telefonoempresarial(String telefonoempresarial) {
        this.telefonoempresarial = telefonoempresarial;
        return this;
    }

    public void setTelefonoempresarial(String telefonoempresarial) {
        this.telefonoempresarial = telefonoempresarial;
    }

    public String getDireccionresidencial() {
        return direccionresidencial;
    }

    public Propietario direccionresidencial(String direccionresidencial) {
        this.direccionresidencial = direccionresidencial;
        return this;
    }

    public void setDireccionresidencial(String direccionresidencial) {
        this.direccionresidencial = direccionresidencial;
    }

    public String getDireccionempresarial() {
        return direccionempresarial;
    }

    public Propietario direccionempresarial(String direccionempresarial) {
        this.direccionempresarial = direccionempresarial;
        return this;
    }

    public void setDireccionempresarial(String direccionempresarial) {
        this.direccionempresarial = direccionempresarial;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Propietario idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Propietario imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Propietario imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Propietario idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public Set<Lotes> getLotes() {
        return lotes;
    }

    public Propietario lotes(Set<Lotes> lotes) {
        this.lotes = lotes;
        return this;
    }

    public Propietario addLotes(Lotes lotes) {
        this.lotes.add(lotes);
        lotes.setPropietario(this);
        return this;
    }

    public Propietario removeLotes(Lotes lotes) {
        this.lotes.remove(lotes);
        lotes.setPropietario(null);
        return this;
    }

    public void setLotes(Set<Lotes> lotes) {
        this.lotes = lotes;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Propietario tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Propietario)) {
            return false;
        }
        return id != null && id.equals(((Propietario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Propietario{" +
            "id=" + getId() +
            ", numeroDocumento=" + getNumeroDocumento() +
            ", nombreORazonSocial='" + getNombreORazonSocial() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefonocelular='" + getTelefonocelular() + "'" +
            ", telefonofijo='" + getTelefonofijo() + "'" +
            ", telefonoempresarial='" + getTelefonoempresarial() + "'" +
            ", direccionresidencial='" + getDireccionresidencial() + "'" +
            ", direccionempresarial='" + getDireccionempresarial() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", idciudad=" + getIdciudad() +
            "}";
    }
}
