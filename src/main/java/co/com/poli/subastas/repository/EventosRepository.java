package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Eventos;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Eventos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {
    
    @Query("select a from Eventos a where estadoActivo = :estado and :now BETWEEN a.fechainicio and a.fechafinal")
    Page<Eventos> findByEstadoActivoBetweenFechainicioAndFechafinal(@Param("estado") Boolean estado,@Param("now") Instant now, Pageable pageable);
    
    @Query("select a from Eventos a where a.fechafinal <= :now and a.estadoActivo = :estado")
    List<Eventos> findAllWithFechafinalBefore(@Param("now") Instant now, @Param("estado") Boolean  estado);

}
