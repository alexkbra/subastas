package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import co.com.poli.subastas.domain.enumeration.TipoMensaje;

import co.com.poli.subastas.domain.enumeration.TipoCliente;

/**
 * A Mensajes.
 */
@Entity
@Table(name = "mensajes")
public class Mensajes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    
    @Lob
    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @Column(name = "numero_lote")
    private String numeroLote;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mensaje")
    private TipoMensaje tipoMensaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    private TipoCliente tipoCliente;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "idusuario")
    private String idusuario;

    @Column(name = "idcliente")
    private String idcliente;

    @Column(name = "activo")
    private Boolean activo;

    @NotNull
    @Column(name = "valor_pagar", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorPagar;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Mensajes titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Mensajes cuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
        return this;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public Mensajes numeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
        return this;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public Mensajes tipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
        return this;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public Mensajes tipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
        return this;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public Mensajes direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Mensajes idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public Mensajes idcliente(String idcliente) {
        this.idcliente = idcliente;
        return this;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Mensajes activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public BigDecimal getValorPagar() {
        return valorPagar;
    }

    public Mensajes valorPagar(BigDecimal valorPagar) {
        this.valorPagar = valorPagar;
        return this;
    }

    public void setValorPagar(BigDecimal valorPagar) {
        this.valorPagar = valorPagar;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mensajes)) {
            return false;
        }
        return id != null && id.equals(((Mensajes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mensajes{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", cuerpo='" + getCuerpo() + "'" +
            ", numeroLote='" + getNumeroLote() + "'" +
            ", tipoMensaje='" + getTipoMensaje() + "'" +
            ", tipoCliente='" + getTipoCliente() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", idcliente='" + getIdcliente() + "'" +
            ", activo='" + isActivo() + "'" +
            ", valorPagar=" + getValorPagar() +
            "}";
    }
}
