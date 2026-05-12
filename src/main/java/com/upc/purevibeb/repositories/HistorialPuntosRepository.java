package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.HistorialPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialPuntosRepository extends JpaRepository<HistorialPuntos, Long> {
    @Query("SELECT COALESCE(SUM(h.puntos), 0) FROM HistorialPuntos h WHERE h.usuario.id = :usuarioId")
    int getSaldoPuntos(@Param("usuarioId") Long usuarioId);
}