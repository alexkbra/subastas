package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Animales;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Animales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalesRepository extends JpaRepository<Animales, Long> {

}
