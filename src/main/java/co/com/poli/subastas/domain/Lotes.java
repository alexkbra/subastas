package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lotes.
 */
@Entity
@Table(name = "lotes")
public class Lotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "numero", length = 100, nullable = false)
    private String numero;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "decripcion")
    private String decripcion;

    @Column(name = "raza")
    private String raza;

    @NotNull
    @Column(name = "cantidad_animales", nullable = false)
    private Integer cantidadAnimales;

    @Column(name = "pesopromedio", precision = 21, scale = 2)
    private BigDecimal pesopromedio;

    @Column(name = "pesototallote", precision = 21, scale = 2)
    private BigDecimal pesototallote;

    @Column(name = "pesobaseporkg", precision = 21, scale = 2)
    private BigDecimal pesobaseporkg;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @Size(max = 500)
    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "idciudad")
    private Long idciudad;

    @OneToMany(mappedBy = "lotes")
    private Set<LotesToAnimales> lotestoanimales = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotes")
    private Propietario propietario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotes")
    private ClasificacionLote clasificacionlote;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotes")
    private Subastas subastas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Lotes numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public Lotes nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public Lotes decripcion(String decripcion) {
        this.decripcion = decripcion;
        return this;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getRaza() {
        return raza;
    }

    public Lotes raza(String raza) {
        this.raza = raza;
        return this;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getCantidadAnimales() {
        return cantidadAnimales;
    }

    public Lotes cantidadAnimales(Integer cantidadAnimales) {
        this.cantidadAnimales = cantidadAnimales;
        return this;
    }

    public void setCantidadAnimales(Integer cantidadAnimales) {
        this.cantidadAnimales = cantidadAnimales;
    }

    public BigDecimal getPesopromedio() {
        return pesopromedio;
    }

    public Lotes pesopromedio(BigDecimal pesopromedio) {
        this.pesopromedio = pesopromedio;
        return this;
    }

    public void setPesopromedio(BigDecimal pesopromedio) {
        this.pesopromedio = pesopromedio;
    }

    public BigDecimal getPesototallote() {
        return pesototallote;
    }

    public Lotes pesototallote(BigDecimal pesototallote) {
        this.pesototallote = pesototallote;
        return this;
    }

    public void setPesototallote(BigDecimal pesototallote) {
        this.pesototallote = pesototallote;
    }

    public BigDecimal getPesobaseporkg() {
        return pesobaseporkg;
    }

    public Lotes pesobaseporkg(BigDecimal pesobaseporkg) {
        this.pesobaseporkg = pesobaseporkg;
        return this;
    }

    public void setPesobaseporkg(BigDecimal pesobaseporkg) {
        this.pesobaseporkg = pesobaseporkg;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Lotes imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Lotes imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Lotes videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Lotes idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public Set<LotesToAnimales> getLotestoanimales() {
        return lotestoanimales;
    }

    public Lotes lotestoanimales(Set<LotesToAnimales> lotesToAnimales) {
        this.lotestoanimales = lotesToAnimales;
        return this;
    }

    public Lotes addLotestoanimales(LotesToAnimales lotesToAnimales) {
        this.lotestoanimales.add(lotesToAnimales);
        lotesToAnimales.setLotes(this);
        return this;
    }

    public Lotes removeLotestoanimales(LotesToAnimales lotesToAnimales) {
        this.lotestoanimales.remove(lotesToAnimales);
        lotesToAnimales.setLotes(null);
        return this;
    }

    public void setLotestoanimales(Set<LotesToAnimales> lotesToAnimales) {
        this.lotestoanimales = lotesToAnimales;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public Lotes propietario(Propietario propietario) {
        this.propietario = propietario;
        return this;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public ClasificacionLote getClasificacionlote() {
        return clasificacionlote;
    }

    public Lotes clasificacionlote(ClasificacionLote clasificacionLote) {
        this.clasificacionlote = clasificacionLote;
        return this;
    }

    public void setClasificacionlote(ClasificacionLote clasificacionLote) {
        this.clasificacionlote = clasificacionLote;
    }

    public Subastas getSubastas() {
        return subastas;
    }

    public Lotes subastas(Subastas subastas) {
        this.subastas = subastas;
        return this;
    }

    public void setSubastas(Subastas subastas) {
        this.subastas = subastas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lotes)) {
            return false;
        }
        return id != null && id.equals(((Lotes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Lotes{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", decripcion='" + getDecripcion() + "'" +
            ", raza='" + getRaza() + "'" +
            ", cantidadAnimales=" + getCantidadAnimales() +
            ", pesopromedio=" + getPesopromedio() +
            ", pesototallote=" + getPesototallote() +
            ", pesobaseporkg=" + getPesobaseporkg() +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", idciudad=" + getIdciudad() +
            "}";
    }
}
