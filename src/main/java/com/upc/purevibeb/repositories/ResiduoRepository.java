package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResiduoRepository extends JpaRepository<Residuo, Long> {
    List<Residuo> findByActividadId(Long actividadId);

    @Query("SELECT r.reciclaje, SUM(r.pesoKg) " +
            "FROM Residuo r " +
            "WHERE r.actividad.id = :actividadId " +
            "GROUP BY r.reciclaje")
    List<Object[]> findResiduosAgrupadosPorActividad(@Param("actividadId") Long actividadId);
}