package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Mensajes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mensajes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MensajesRepository extends JpaRepository<Mensajes, Long> {

}
