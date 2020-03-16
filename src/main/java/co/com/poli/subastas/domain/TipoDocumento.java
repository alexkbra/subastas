package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoDocumento.
 */
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "codigo", length = 100, nullable = false)
    private String codigo;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoDocumento")
    private Set<Propietario> propietarios = new HashSet<>();

    @OneToMany(mappedBy = "tipoDocumento")
    private Set<Cliente> clientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoDocumento codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoDocumento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Propietario> getPropietarios() {
        return propietarios;
    }

    public TipoDocumento propietarios(Set<Propietario> propietarios) {
        this.propietarios = propietarios;
        return this;
    }

    public TipoDocumento addPropietario(Propietario propietario) {
        this.propietarios.add(propietario);
        propietario.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removePropietario(Propietario propietario) {
        this.propietarios.remove(propietario);
        propietario.setTipoDocumento(null);
        return this;
    }

    public void setPropietarios(Set<Propietario> propietarios) {
        this.propietarios = propietarios;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public TipoDocumento clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public TipoDocumento addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setTipoDocumento(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumento)) {
            return false;
        }
        return id != null && id.equals(((TipoDocumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoDocumento{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
