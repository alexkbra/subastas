package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Pujas;
import java.math.BigDecimal;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pujas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PujasRepository extends JpaRepository<Pujas, Long> {
    
    List<Pujas> findByIdLote(String idLote);

    List<Pujas> findByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(String idEvento, String idSubasta, String idLote);
    
    List<Pujas> findByIdEventoAndIdSubastaAndIdLoteAndValorGreaterThanOrderByValorDesc(String idEvento, String idSubasta, String idLote, BigDecimal valor);
    
    Pujas findFirstByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(String idEvento, String idSubasta, String idLote);
    
    List<Pujas> findByIdEventoAndIdSubastaOrderByValorDesc(String idEvento, String idSubasta);
    
  
}
