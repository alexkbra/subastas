package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Razas.
 */
@Entity
@Table(name = "razas")
public class Razas implements Serializable {

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

    @OneToMany(mappedBy = "razas")
    private Set<Animales> animales = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("razas")
    private Especies especies;

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

    public Razas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public Razas decripcion(String decripcion) {
        this.decripcion = decripcion;
        return this;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getCode() {
        return code;
    }

    public Razas code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Animales> getAnimales() {
        return animales;
    }

    public Razas animales(Set<Animales> animales) {
        this.animales = animales;
        return this;
    }

    public Razas addAnimales(Animales animales) {
        this.animales.add(animales);
        animales.setRazas(this);
        return this;
    }

    public Razas removeAnimales(Animales animales) {
        this.animales.remove(animales);
        animales.setRazas(null);
        return this;
    }

    public void setAnimales(Set<Animales> animales) {
        this.animales = animales;
    }

    public Especies getEspecies() {
        return especies;
    }

    public Razas especies(Especies especies) {
        this.especies = especies;
        return this;
    }

    public void setEspecies(Especies especies) {
        this.especies = especies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Razas)) {
            return false;
        }
        return id != null && id.equals(((Razas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Razas{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", decripcion='" + getDecripcion() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
