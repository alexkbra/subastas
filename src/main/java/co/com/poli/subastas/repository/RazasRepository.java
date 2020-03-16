package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Razas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Razas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RazasRepository extends JpaRepository<Razas, Long> {

}
