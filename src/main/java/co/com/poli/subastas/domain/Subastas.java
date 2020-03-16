package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subastas.
 */
@Entity
@Table(name = "subastas")
public class Subastas implements Serializable {

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
    @Column(name = "timpo_reclo_ganador", nullable = false)
    private Duration timpoRecloGanador;

    @NotNull
    @Column(name = "fechacreacion", nullable = false)
    private Instant fechacreacion;

    @NotNull
    @Column(name = "valorinicial", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorinicial;

    @NotNull
    @Column(name = "valoractual", precision = 21, scale = 2, nullable = false)
    private BigDecimal valoractual;

    @NotNull
    @Column(name = "valortope", precision = 21, scale = 2, nullable = false)
    private BigDecimal valortope;

    @NotNull
    @Column(name = "paga_anticipo", nullable = false)
    private Boolean pagaAnticipo;

    @Column(name = "pesobaseporkg", precision = 21, scale = 2)
    private BigDecimal pesobaseporkg;

    @Column(name = "pesototallote", precision = 21, scale = 2)
    private BigDecimal pesototallote;

    @Column(name = "valor_anticipo", precision = 21, scale = 2)
    private BigDecimal valorAnticipo;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @Size(max = 500)
    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @NotNull
    @Column(name = "estado_activo", nullable = false)
    private Boolean estadoActivo;

    @OneToMany(mappedBy = "subastas")
    private Set<Lotes> lotes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("subastas")
    private Eventos eventos;

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

    public Subastas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public Subastas decripcion(String decripcion) {
        this.decripcion = decripcion;
        return this;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public Instant getFechainicio() {
        return fechainicio;
    }

    public Subastas fechainicio(Instant fechainicio) {
        this.fechainicio = fechainicio;
        return this;
    }

    public void setFechainicio(Instant fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Instant getFechafinal() {
        return fechafinal;
    }

    public Subastas fechafinal(Instant fechafinal) {
        this.fechafinal = fechafinal;
        return this;
    }

    public void setFechafinal(Instant fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Duration getTimpoRecloGanador() {
        return timpoRecloGanador;
    }

    public Subastas timpoRecloGanador(Duration timpoRecloGanador) {
        this.timpoRecloGanador = timpoRecloGanador;
        return this;
    }

    public void setTimpoRecloGanador(Duration timpoRecloGanador) {
        this.timpoRecloGanador = timpoRecloGanador;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public Subastas fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public BigDecimal getValorinicial() {
        return valorinicial;
    }

    public Subastas valorinicial(BigDecimal valorinicial) {
        this.valorinicial = valorinicial;
        return this;
    }

    public void setValorinicial(BigDecimal valorinicial) {
        this.valorinicial = valorinicial;
    }

    public BigDecimal getValoractual() {
        return valoractual;
    }

    public Subastas valoractual(BigDecimal valoractual) {
        this.valoractual = valoractual;
        return this;
    }

    public void setValoractual(BigDecimal valoractual) {
        this.valoractual = valoractual;
    }

    public BigDecimal getValortope() {
        return valortope;
    }

    public Subastas valortope(BigDecimal valortope) {
        this.valortope = valortope;
        return this;
    }

    public void setValortope(BigDecimal valortope) {
        this.valortope = valortope;
    }

    public Boolean isPagaAnticipo() {
        return pagaAnticipo;
    }

    public Subastas pagaAnticipo(Boolean pagaAnticipo) {
        this.pagaAnticipo = pagaAnticipo;
        return this;
    }

    public void setPagaAnticipo(Boolean pagaAnticipo) {
        this.pagaAnticipo = pagaAnticipo;
    }

    public BigDecimal getPesobaseporkg() {
        return pesobaseporkg;
    }

    public Subastas pesobaseporkg(BigDecimal pesobaseporkg) {
        this.pesobaseporkg = pesobaseporkg;
        return this;
    }

    public void setPesobaseporkg(BigDecimal pesobaseporkg) {
        this.pesobaseporkg = pesobaseporkg;
    }

    public BigDecimal getPesototallote() {
        return pesototallote;
    }

    public Subastas pesototallote(BigDecimal pesototallote) {
        this.pesototallote = pesototallote;
        return this;
    }

    public void setPesototallote(BigDecimal pesototallote) {
        this.pesototallote = pesototallote;
    }

    public BigDecimal getValorAnticipo() {
        return valorAnticipo;
    }

    public Subastas valorAnticipo(BigDecimal valorAnticipo) {
        this.valorAnticipo = valorAnticipo;
        return this;
    }

    public void setValorAnticipo(BigDecimal valorAnticipo) {
        this.valorAnticipo = valorAnticipo;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Subastas imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Subastas imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Subastas videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Boolean isEstadoActivo() {
        return estadoActivo;
    }

    public Subastas estadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
        return this;
    }

    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Set<Lotes> getLotes() {
        return lotes;
    }

    public Subastas lotes(Set<Lotes> lotes) {
        this.lotes = lotes;
        return this;
    }

    public Subastas addLotes(Lotes lotes) {
        this.lotes.add(lotes);
        lotes.setSubastas(this);
        return this;
    }

    public Subastas removeLotes(Lotes lotes) {
        this.lotes.remove(lotes);
        lotes.setSubastas(null);
        return this;
    }

    public void setLotes(Set<Lotes> lotes) {
        this.lotes = lotes;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public Subastas eventos(Eventos eventos) {
        this.eventos = eventos;
        return this;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subastas)) {
            return false;
        }
        return id != null && id.equals(((Subastas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subastas{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", decripcion='" + getDecripcion() + "'" +
            ", fechainicio='" + getFechainicio() + "'" +
            ", fechafinal='" + getFechafinal() + "'" +
            ", timpoRecloGanador='" + getTimpoRecloGanador() + "'" +
            ", fechacreacion='" + getFechacreacion() + "'" +
            ", valorinicial=" + getValorinicial() +
            ", valoractual=" + getValoractual() +
            ", valortope=" + getValortope() +
            ", pagaAnticipo='" + isPagaAnticipo() + "'" +
            ", pesobaseporkg=" + getPesobaseporkg() +
            ", pesototallote=" + getPesototallote() +
            ", valorAnticipo=" + getValorAnticipo() +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", estadoActivo='" + isEstadoActivo() + "'" +
            "}";
    }
}
