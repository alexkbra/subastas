package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Dispositivo;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dispositivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    
    List<Dispositivo> findByIdEventoAndIdSubastaAndIdLoteAndDispositivo(String idEvento,String idSubasta,String idLote,String dispositivo);
    
    List<Dispositivo> findByIdEventoAndIdSubastaAndIdLote(String idEvento,String idSubasta,String idLote);
    

}
