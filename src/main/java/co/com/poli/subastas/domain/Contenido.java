package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import co.com.poli.subastas.domain.enumeration.TipoContenido;

import co.com.poli.subastas.domain.enumeration.EntidadContenido;

/**
 * A Contenido.
 */
@Entity
@Table(name = "contenido")
public class Contenido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoContenido tipo;

    @Size(max = 500)
    @Column(name = "url", length = 500)
    private String url;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @Lob
    @Column(name = "texto")
    private String texto;

    @Enumerated(EnumType.STRING)
    @Column(name = "entidad")
    private EntidadContenido entidad;

    @Column(name = "id_entidad")
    private String idEntidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoContenido getTipo() {
        return tipo;
    }

    public Contenido tipo(TipoContenido tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoContenido tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public Contenido url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Contenido imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Contenido imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public String getTexto() {
        return texto;
    }

    public Contenido texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public EntidadContenido getEntidad() {
        return entidad;
    }

    public Contenido entidad(EntidadContenido entidad) {
        this.entidad = entidad;
        return this;
    }

    public void setEntidad(EntidadContenido entidad) {
        this.entidad = entidad;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public Contenido idEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
        return this;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contenido)) {
            return false;
        }
        return id != null && id.equals(((Contenido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contenido{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", url='" + getUrl() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", texto='" + getTexto() + "'" +
            ", entidad='" + getEntidad() + "'" +
            ", idEntidad='" + getIdEntidad() + "'" +
            "}";
    }
}
