package co.com.poli.subastas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A LotesToAnimales.
 */
@Entity
@Table(name = "lotes_to_animales")
public class LotesToAnimales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cantidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotestoanimales")
    private Lotes lotes;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotestoanimales")
    private Animales animales;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public LotesToAnimales cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Lotes getLotes() {
        return lotes;
    }

    public LotesToAnimales lotes(Lotes lotes) {
        this.lotes = lotes;
        return this;
    }

    public void setLotes(Lotes lotes) {
        this.lotes = lotes;
    }

    public Animales getAnimales() {
        return animales;
    }

    public LotesToAnimales animales(Animales animales) {
        this.animales = animales;
        return this;
    }

    public void setAnimales(Animales animales) {
        this.animales = animales;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LotesToAnimales)) {
            return false;
        }
        return id != null && id.equals(((LotesToAnimales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LotesToAnimales{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
