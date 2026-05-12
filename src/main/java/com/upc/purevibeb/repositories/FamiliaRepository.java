package com.upc.purevibeb.repositories;

import com.upc.purevibeb.dtos.FamiliaRankingDTO;
import com.upc.purevibeb.entities.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    Optional<Familia> findByCodigoInvitacion(String codigoInvitacion);

    Optional<Familia> findByAdminId(Long adminId);

    @Query("SELECT new com.upc.purevibeb.dtos.FamiliaRankingDTO(" +
            "0L, " +
            "f.nombre, " +
            "CAST(COUNT(u) AS int), " +
            "SUM(u.huellaTotalKgCO2e)) " +
            "FROM Familia f JOIN f.miembros u " +
            "WHERE u.huellaTotalKgCO2e IS NOT NULL " +
            "GROUP BY f.id, f.nombre " +
            "ORDER BY SUM(u.huellaTotalKgCO2e) ASC")
    List<FamiliaRankingDTO> obtenerRankingFamilias();
}