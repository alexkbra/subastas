package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import co.com.poli.subastas.domain.enumeration.Sexos;

/**
 * A Animales.
 */
@Entity
@Table(name = "animales")
public class Animales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pesokg", precision = 21, scale = 2, nullable = false)
    private BigDecimal pesokg;

    
    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexos sexo;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "procedencia", length = 100, nullable = false)
    private String procedencia;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "propietario", length = 100, nullable = false)
    private String propietario;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @Size(max = 500)
    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @OneToMany(mappedBy = "animales")
    private Set<LotesToAnimales> lotestoanimales = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("animales")
    private Razas razas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPesokg() {
        return pesokg;
    }

    public Animales pesokg(BigDecimal pesokg) {
        this.pesokg = pesokg;
        return this;
    }

    public void setPesokg(BigDecimal pesokg) {
        this.pesokg = pesokg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Animales descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Sexos getSexo() {
        return sexo;
    }

    public Animales sexo(Sexos sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexos sexo) {
        this.sexo = sexo;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public Animales procedencia(String procedencia) {
        this.procedencia = procedencia;
        return this;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getPropietario() {
        return propietario;
    }

    public Animales propietario(String propietario) {
        this.propietario = propietario;
        return this;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Animales imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Animales imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Animales videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<LotesToAnimales> getLotestoanimales() {
        return lotestoanimales;
    }

    public Animales lotestoanimales(Set<LotesToAnimales> lotesToAnimales) {
        this.lotestoanimales = lotesToAnimales;
        return this;
    }

    public Animales addLotestoanimales(LotesToAnimales lotesToAnimales) {
        this.lotestoanimales.add(lotesToAnimales);
        lotesToAnimales.setAnimales(this);
        return this;
    }

    public Animales removeLotestoanimales(LotesToAnimales lotesToAnimales) {
        this.lotestoanimales.remove(lotesToAnimales);
        lotesToAnimales.setAnimales(null);
        return this;
    }

    public void setLotestoanimales(Set<LotesToAnimales> lotesToAnimales) {
        this.lotestoanimales = lotesToAnimales;
    }

    public Razas getRazas() {
        return razas;
    }

    public Animales razas(Razas razas) {
        this.razas = razas;
        return this;
    }

    public void setRazas(Razas razas) {
        this.razas = razas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Animales)) {
            return false;
        }
        return id != null && id.equals(((Animales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Animales{" +
            "id=" + getId() +
            ", pesokg=" + getPesokg() +
            ", descripcion='" + getDescripcion() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", procedencia='" + getProcedencia() + "'" +
            ", propietario='" + getPropietario() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            "}";
    }
}
