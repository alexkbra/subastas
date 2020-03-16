package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Especies;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Especies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspeciesRepository extends JpaRepository<Especies, Long> {

}
