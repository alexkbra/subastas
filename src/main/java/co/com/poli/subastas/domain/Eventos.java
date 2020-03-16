package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Eventos.
 */
@Entity
@Table(name = "eventos")
public class Eventos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "decripcion")
    private String decripcion;

    @NotNull
    @Column(name = "fechainicio", nullable = false)
    private Instant fechainicio;

    @NotNull
    @Column(name = "fechafinal", nullable = false)
    private Instant fechafinal;

    @NotNull
    @Column(name = "fechacreacion", nullable = false)
    private Instant fechacreacion;

    
    @Lob
    @Column(name = "imagen_url", nullable = false)
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type", nullable = false)
    private String imagenUrlContentType;

    @Size(max = 500)
    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "idciudad")
    private Long idciudad;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @NotNull
    @Column(name = "estado_activo", nullable = false)
    private Boolean estadoActivo;

    @OneToMany(mappedBy = "eventos")
    private Set<Subastas> subastas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Eventos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public Eventos decripcion(String decripcion) {
        this.decripcion = decripcion;
        return this;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public Instant getFechainicio() {
        return fechainicio;
    }

    public Eventos fechainicio(Instant fechainicio) {
        this.fechainicio = fechainicio;
        return this;
    }

    public void setFechainicio(Instant fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Instant getFechafinal() {
        return fechafinal;
    }

    public Eventos fechafinal(Instant fechafinal) {
        this.fechafinal = fechafinal;
        return this;
    }

    public void setFechafinal(Instant fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public Eventos fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Eventos imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Eventos imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Eventos videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Eventos idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public String getLatitud() {
        return latitud;
    }

    public Eventos latitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public Eventos longitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Boolean isEstadoActivo() {
        return estadoActivo;
    }

    public Eventos estadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
        return this;
    }

    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Set<Subastas> getSubastas() {
        return subastas;
    }

    public Eventos subastas(Set<Subastas> subastas) {
        this.subastas = subastas;
        return this;
    }

    public Eventos addSubastas(Subastas subastas) {
        this.subastas.add(subastas);
        subastas.setEventos(this);
        return this;
    }

    public Eventos removeSubastas(Subastas subastas) {
        this.subastas.remove(subastas);
        subastas.setEventos(null);
        return this;
    }

    public void setSubastas(Set<Subastas> subastas) {
        this.subastas = subastas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eventos)) {
            return false;
        }
        return id != null && id.equals(((Eventos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Eventos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", decripcion='" + getDecripcion() + "'" +
            ", fechainicio='" + getFechainicio() + "'" +
            ", fechafinal='" + getFechafinal() + "'" +
            ", fechacreacion='" + getFechacreacion() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", idciudad=" + getIdciudad() +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", estadoActivo='" + isEstadoActivo() + "'" +
            "}";
    }
}
