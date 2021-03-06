package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Cliente;
import co.com.poli.subastas.domain.Pujadores;
import co.com.poli.subastas.domain.enumeration.EstadoPujadores;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pujadores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PujadoresRepository extends JpaRepository<Pujadores, Long> {

    List<Pujadores> findByIdEventoAndIdSubastaAndIdLoteAndCliente(String idEvento,String idSubasta,String idLote, Cliente cliente);

    List<Pujadores> findByIdEventoAndIdSubastaAndIdLoteAndEstado(String idEvento,String idSubasta,String idLote, EstadoPujadores estado);
    
    Pujadores findByActivationKey(String activationKey);
    
}
