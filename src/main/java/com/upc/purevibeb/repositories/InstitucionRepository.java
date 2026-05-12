package com.upc.purevibeb.repositories;

import com.upc.purevibeb.dtos.InstitucionRankingDTO;
import com.upc.purevibeb.entities.Institucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstitucionRepository extends JpaRepository<Institucion, Long> {
    Optional<Institucion> findByCodigoInvitacion(String codigoInvitacion);
    Optional<Institucion> findByAdminId(Long adminId);
    @Query("SELECT new com.upc.purevibeb.dtos.InstitucionRankingDTO(" +
            "0L, " +
            "i.nombre, " +
            "i.tipo, " +
            "CAST(COUNT(u) AS int), " +
            "SUM(u.huellaTotalKgCO2e)) " +
            "FROM Institucion i JOIN i.miembros u " +
            "WHERE u.huellaTotalKgCO2e IS NOT NULL " +
            "GROUP BY i.id, i.nombre, i.tipo " +
            "ORDER BY SUM(u.huellaTotalKgCO2e) ASC")
    List<InstitucionRankingDTO> obtenerRankingInstituciones();
}