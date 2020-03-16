package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.LotesToAnimales;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LotesToAnimales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotesToAnimalesRepository extends JpaRepository<LotesToAnimales, Long> {

    Page<LotesToAnimales> findByLotes(Lotes lotes,Pageable pageable);

}
