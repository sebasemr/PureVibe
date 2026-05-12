package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.RecursoEducativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoEducativoRepository extends JpaRepository<RecursoEducativo, Long> {

    @Query("""
SELECT r FROM RecursoEducativo r
WHERE (:tipo IS NULL OR r.tipo = :tipo)
  AND (:pattern IS NULL OR LOWER(r.titulo) LIKE :pattern)
ORDER BY r.creadoEn DESC
""")
    Page<RecursoEducativo> search(@Param("tipo") RecursoEducativo.Tipo tipo,
                                  @Param("pattern") String pattern,
                                  Pageable pageable);

}