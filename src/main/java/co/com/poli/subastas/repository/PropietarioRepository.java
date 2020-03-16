package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Propietario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Propietario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

}
