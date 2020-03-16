package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Contenido;
import co.com.poli.subastas.domain.enumeration.EntidadContenido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contenido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    Page<Contenido> findByIdEntidadAndEntidad(String idEntidad, EntidadContenido entidad,Pageable pageable);
}
