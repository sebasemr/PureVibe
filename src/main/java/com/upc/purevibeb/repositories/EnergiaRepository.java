package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.Energia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergiaRepository extends JpaRepository<Energia, Long> {
    List<Energia> findByActividadId(Long actividadId);

    @Query("SELECT e.tipo, e.unidad, SUM(e.consumo) " +
            "FROM Energia e " +
            "WHERE e.actividad.id = :actividadId " +
            "GROUP BY e.tipo, e.unidad")
    List<Object[]> findEnergiaAgrupadaPorActividad(@Param("actividadId") Long actividadId);
}