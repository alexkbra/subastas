package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import co.com.poli.subastas.domain.enumeration.EstadoPujadores;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Pujadores.
 */
@Entity
@Table(name = "pujadores")
public class Pujadores implements Serializable {

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

    @Size(min = 5, max = 255)
    @Column(name = "nroconsignacion", length = 255)
    private String nroconsignacion;

    @Size(min = 5, max = 255)
    @Column(name = "nombrebanco", length = 255)
    private String nombrebanco;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPujadores estado;

    @Column(name = "pago_aceptado")
    private Boolean pagoAceptado;
    
    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;
    
    @OneToMany(mappedBy = "pujadores")
    private Set<Pujas> pujas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pujadores")
    private Cliente cliente;

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

    public Pujadores idEvento(String idEvento) {
        this.idEvento = idEvento;
        return this;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdSubasta() {
        return idSubasta;
    }

    public Pujadores idSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
        return this;
    }

    public void setIdSubasta(String idSubasta) {
        this.idSubasta = idSubasta;
    }

    public String getIdLote() {
        return idLote;
    }

    public Pujadores idLote(String idLote) {
        this.idLote = idLote;
        return this;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getNroconsignacion() {
        return nroconsignacion;
    }

    public Pujadores nroconsignacion(String nroconsignacion) {
        this.nroconsignacion = nroconsignacion;
        return this;
    }

    public void setNroconsignacion(String nroconsignacion) {
        this.nroconsignacion = nroconsignacion;
    }

    public String getNombrebanco() {
        return nombrebanco;
    }

    public Pujadores nombrebanco(String nombrebanco) {
        this.nombrebanco = nombrebanco;
        return this;
    }

    public void setNombrebanco(String nombrebanco) {
        this.nombrebanco = nombrebanco;
    }

    public EstadoPujadores getEstado() {
        return estado;
    }

    public Pujadores estado(EstadoPujadores estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoPujadores estado) {
        this.estado = estado;
    }

    public Boolean isPagoAceptado() {
        return pagoAceptado;
    }

    public Pujadores pagoAceptado(Boolean pagoAceptado) {
        this.pagoAceptado = pagoAceptado;
        return this;
    }

    public void setPagoAceptado(Boolean pagoAceptado) {
        this.pagoAceptado = pagoAceptado;
    }

    public Set<Pujas> getPujas() {
        return pujas;
    }

    public Pujadores pujas(Set<Pujas> pujas) {
        this.pujas = pujas;
        return this;
    }

    public Pujadores addPujas(Pujas pujas) {
        this.pujas.add(pujas);
        pujas.setPujadores(this);
        return this;
    }

    public Pujadores removePujas(Pujas pujas) {
        this.pujas.remove(pujas);
        pujas.setPujadores(null);
        return this;
    }

    public void setPujas(Set<Pujas> pujas) {
        this.pujas = pujas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pujadores cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pujadores)) {
            return false;
        }
        return id != null && id.equals(((Pujadores) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pujadores{" +
            "id=" + getId() +
            ", idEvento='" + getIdEvento() + "'" +
            ", idSubasta='" + getIdSubasta() + "'" +
            ", idLote='" + getIdLote() + "'" +
            ", nroconsignacion='" + getNroconsignacion() + "'" +
            ", nombrebanco='" + getNombrebanco() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pagoAceptado='" + isPagoAceptado() + "'" +
            "}";
    }
}
