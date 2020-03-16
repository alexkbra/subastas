package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Especies.
 */
@Entity
@Table(name = "especies")
public class Especies implements Serializable {

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
    @Size(min = 2, max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @OneToMany(mappedBy = "especies")
    private Set<Razas> razas = new HashSet<>();

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

    public Especies nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public Especies decripcion(String decripcion) {
        this.decripcion = decripcion;
        return this;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getCode() {
        return code;
    }

    public Especies code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Razas> getRazas() {
        return razas;
    }

    public Especies razas(Set<Razas> razas) {
        this.razas = razas;
        return this;
    }

    public Especies addRazas(Razas razas) {
        this.razas.add(razas);
        razas.setEspecies(this);
        return this;
    }

    public Especies removeRazas(Razas razas) {
        this.razas.remove(razas);
        razas.setEspecies(null);
        return this;
    }

    public void setRazas(Set<Razas> razas) {
        this.razas = razas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Especies)) {
            return false;
        }
        return id != null && id.equals(((Especies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Especies{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", decripcion='" + getDecripcion() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
