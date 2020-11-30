package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Eventos;
import co.com.poli.subastas.domain.Subastas;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Subastas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubastasRepository extends JpaRepository<Subastas, Long> {
    
    @Query("select a from Subastas a where eventos = ?1 and estadoActivo = ?2 and ?3 BETWEEN a.fechainicio and a.fechafinal")
    Page<Subastas> findByEventosAndEstadoActivoBetweenFechainicioAndFechafinal(Eventos eventos,Boolean estado,Instant now, Pageable pageable);
    
    @Query("select a from Subastas a where a.fechafinal <= :now and a.estadoActivo = :estado and a.estadoGanador = :estadoG ")
    List<Subastas> findAllWithFechafinalBefore(@Param("now") Instant now,@Param("estado") Boolean estado, @Param("estadoG") Boolean estadoG );
    
    List<Subastas> findByEstadoActivo(Boolean estado);
    
    
}
