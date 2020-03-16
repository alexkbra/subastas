package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.EstadoCliente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoClienteRepository extends JpaRepository<EstadoCliente, Long> {

}
