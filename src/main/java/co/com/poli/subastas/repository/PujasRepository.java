package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Pujas;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pujas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PujasRepository extends JpaRepository<Pujas, Long> {

    List<Pujas> findByIdEventoAndIdSubastaAndIdLoteOrderByValorDesc(String idEvento, String idSubasta, String idLote);

}
