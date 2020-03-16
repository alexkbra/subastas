package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.ClasificacionLote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClasificacionLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasificacionLoteRepository extends JpaRepository<ClasificacionLote, Long> {

}
