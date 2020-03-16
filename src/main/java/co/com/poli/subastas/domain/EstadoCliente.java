package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoCliente.
 */
@Entity
@Table(name = "estado_cliente")
public class EstadoCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @OneToMany(mappedBy = "estadocliente")
    private Set<Cliente> clientes = new HashSet<>();

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

    public EstadoCliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCode() {
        return code;
    }

    public EstadoCliente code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public EstadoCliente clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public EstadoCliente addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setEstadocliente(this);
        return this;
    }

    public EstadoCliente removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setEstadocliente(null);
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
        if (!(o instanceof EstadoCliente)) {
            return false;
        }
        return id != null && id.equals(((EstadoCliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoCliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
