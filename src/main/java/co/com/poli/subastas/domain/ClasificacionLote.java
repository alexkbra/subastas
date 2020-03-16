package co.com.poli.subastas.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClasificacionLote.
 */
@Entity
@Table(name = "clasificacion_lote")
public class ClasificacionLote implements Serializable {

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

    @OneToMany(mappedBy = "clasificacionlote")
    private Set<Lotes> lotes = new HashSet<>();

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

    public ClasificacionLote nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCode() {
        return code;
    }

    public ClasificacionLote code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Lotes> getLotes() {
        return lotes;
    }

    public ClasificacionLote lotes(Set<Lotes> lotes) {
        this.lotes = lotes;
        return this;
    }

    public ClasificacionLote addLotes(Lotes lotes) {
        this.lotes.add(lotes);
        lotes.setClasificacionlote(this);
        return this;
    }

    public ClasificacionLote removeLotes(Lotes lotes) {
        this.lotes.remove(lotes);
        lotes.setClasificacionlote(null);
        return this;
    }

    public void setLotes(Set<Lotes> lotes) {
        this.lotes = lotes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasificacionLote)) {
            return false;
        }
        return id != null && id.equals(((ClasificacionLote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClasificacionLote{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
