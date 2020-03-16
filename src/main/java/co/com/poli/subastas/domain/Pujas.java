package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A Pujas.
 */
@Entity
@Table(name = "pujas")
public class Pujas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_evento")
    private String idEvento;

    @Column(name = "id_subasta")
    private String idSubasta;

    @Column(name = "id_lote")
    private String idLote;

    @NotNull
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "fechacreacion")
    private Instant fechacreacion;

    @Column(name = "aceptado_ganador")
    private Boolean aceptadoGanador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pujas")
    private Pujadores pujadores;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public Pujas idEvento(String idEvento) {
        this.idEvento = idEvento;
        return this;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdSubasta() {
        return idSubasta;
    }

    public Pujas idSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
        return this;
    }

    public void setIdSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
    }

    public String getIdLote() {
        return idLote;
    }

    public Pujas idLote(String idLote) {
        this.idLote = idLote;
        return this;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Pujas valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public Pujas fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Boolean isAceptadoGanador() {
        return aceptadoGanador;
    }

    public Pujas aceptadoGanador(Boolean aceptadoGanador) {
        this.aceptadoGanador = aceptadoGanador;
        return this;
    }

    public void setAceptadoGanador(Boolean aceptadoGanador) {
        this.aceptadoGanador = aceptadoGanador;
    }

    public Pujadores getPujadores() {
        return pujadores;
    }

    public Pujas pujadores(Pujadores pujadores) {
        this.pujadores = pujadores;
        return this;
    }

    public void setPujadores(Pujadores pujadores) {
        this.pujadores = pujadores;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pujas)) {
            return false;
        }
        return id != null && id.equals(((Pujas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pujas{" +
            "id=" + getId() +
            ", idEvento='" + getIdEvento() + "'" +
            ", idSubasta='" + getIdSubasta() + "'" +
            ", idLote='" + getIdLote() + "'" +
            ", valor=" + getValor() +
            ", fechacreacion='" + getFechacreacion() + "'" +
            ", aceptadoGanador='" + isAceptadoGanador() + "'" +
            "}";
    }
}
