package co.com.poli.subastas.repository;

import co.com.poli.subastas.domain.Lotes;
import co.com.poli.subastas.domain.Subastas;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotesRepository extends JpaRepository<Lotes, Long> {

    Page<Lotes> findBySubastas(Subastas subastas, Pageable pageable);
    
    List<Lotes> findBySubastas(Subastas subastas);

}
