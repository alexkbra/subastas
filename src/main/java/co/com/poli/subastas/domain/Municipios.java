package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Municipios.
 */
@Entity
@Table(name = "municipios")
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idmunicipios")
    private Long idmunicipios;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "municipio", length = 100, nullable = false)
    private String municipio;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "estado", length = 100, nullable = false)
    private String estado;

    @OneToMany(mappedBy = "municipios")
    private Set<Ciudad> ciudads = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("municipios")
    private Departamentos departamentos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdmunicipios() {
        return idmunicipios;
    }

    public Municipios idmunicipios(Long idmunicipios) {
        this.idmunicipios = idmunicipios;
        return this;
    }

    public void setIdmunicipios(Long idmunicipios) {
        this.idmunicipios = idmunicipios;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Municipios municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public Municipios estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<Ciudad> getCiudads() {
        return ciudads;
    }

    public Municipios ciudads(Set<Ciudad> ciudads) {
        this.ciudads = ciudads;
        return this;
    }

    public Municipios addCiudad(Ciudad ciudad) {
        this.ciudads.add(ciudad);
        ciudad.setMunicipios(this);
        return this;
    }

    public Municipios removeCiudad(Ciudad ciudad) {
        this.ciudads.remove(ciudad);
        ciudad.setMunicipios(null);
        return this;
    }

    public void setCiudads(Set<Ciudad> ciudads) {
        this.ciudads = ciudads;
    }

    public Departamentos getDepartamentos() {
        return departamentos;
    }

    public Municipios departamentos(Departamentos departamentos) {
        this.departamentos = departamentos;
        return this;
    }

    public void setDepartamentos(Departamentos departamentos) {
        this.departamentos = departamentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipios)) {
            return false;
        }
        return id != null && id.equals(((Municipios) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Municipios{" +
            "id=" + getId() +
            ", idmunicipios=" + getIdmunicipios() +
            ", municipio='" + getMunicipio() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
