package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Eventos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Eventos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {

}
