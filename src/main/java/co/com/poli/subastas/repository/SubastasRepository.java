package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Eventos;
import co.com.poli.subastas.domain.Subastas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Subastas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubastasRepository extends JpaRepository<Subastas, Long> {

    Page<Subastas> findByEventos(Eventos eventos, Pageable pageable);

}
