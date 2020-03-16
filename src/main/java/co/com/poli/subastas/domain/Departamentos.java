package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Departamentos.
 */
@Entity
@Table(name = "departamentos")
public class Departamentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iddepartamentos")
    private Long iddepartamentos;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "departamento", length = 255, nullable = false)
    private String departamento;

    @OneToMany(mappedBy = "departamentos")
    private Set<Municipios> municipios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIddepartamentos() {
        return iddepartamentos;
    }

    public Departamentos iddepartamentos(Long iddepartamentos) {
        this.iddepartamentos = iddepartamentos;
        return this;
    }

    public void setIddepartamentos(Long iddepartamentos) {
        this.iddepartamentos = iddepartamentos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Departamentos departamento(String departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Set<Municipios> getMunicipios() {
        return municipios;
    }

    public Departamentos municipios(Set<Municipios> municipios) {
        this.municipios = municipios;
        return this;
    }

    public Departamentos addMunicipios(Municipios municipios) {
        this.municipios.add(municipios);
        municipios.setDepartamentos(this);
        return this;
    }

    public Departamentos removeMunicipios(Municipios municipios) {
        this.municipios.remove(municipios);
        municipios.setDepartamentos(null);
        return this;
    }

    public void setMunicipios(Set<Municipios> municipios) {
        this.municipios = municipios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departamentos)) {
            return false;
        }
        return id != null && id.equals(((Departamentos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Departamentos{" +
            "id=" + getId() +
            ", iddepartamentos=" + getIddepartamentos() +
            ", departamento='" + getDepartamento() + "'" +
            "}";
    }
}
