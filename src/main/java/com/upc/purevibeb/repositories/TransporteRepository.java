package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {
    List<Transporte> findByActividadId(Long actividadId);

    @Query("SELECT t.medio, SUM(t.distanciaKm), SUM(t.kilometrosVolados) " +
            "FROM Transporte t " +
            "WHERE t.actividad.id = :actividadId " +
            "GROUP BY t.medio")
    List<Object[]> findTransporteAgrupadoPorActividad(@Param("actividadId") Long actividadId);
}