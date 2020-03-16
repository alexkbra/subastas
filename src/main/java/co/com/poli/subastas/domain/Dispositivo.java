package co.com.poli.subastas.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Dispositivo.
 */
@Entity
@Table(name = "dispositivo")
public class Dispositivo implements Serializable {

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

    @Column(name = "idusuario")
    private String idusuario;

    @Column(name = "idcliente")
    private String idcliente;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "dispositivo")
    private String dispositivo;

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

    public Dispositivo idEvento(String idEvento) {
        this.idEvento = idEvento;
        return this;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdSubasta() {
        return idSubasta;
    }

    public Dispositivo idSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
        return this;
    }

    public void setIdSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
    }

    public String getIdLote() {
        return idLote;
    }

    public Dispositivo idLote(String idLote) {
        this.idLote = idLote;
        return this;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Dispositivo idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public Dispositivo idcliente(String idcliente) {
        this.idcliente = idcliente;
        return this;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Dispositivo activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public Dispositivo dispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
        return this;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dispositivo)) {
            return false;
        }
        return id != null && id.equals(((Dispositivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
            "id=" + getId() +
            ", idEvento='" + getIdEvento() + "'" +
            ", idSubasta='" + getIdSubasta() + "'" +
            ", idLote='" + getIdLote() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", idcliente='" + getIdcliente() + "'" +
            ", activo='" + isActivo() + "'" +
            ", dispositivo='" + getDispositivo() + "'" +
            "}";
    }
}
