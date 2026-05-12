package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.ActividadesDiarias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadesDiariasRepository extends JpaRepository<ActividadesDiarias, Long> {
    Optional<ActividadesDiarias> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
    List<ActividadesDiarias> findTop5ByUsuario_FamiliaIdOrderByFechaDesc(Long familiaId);
    List<ActividadesDiarias> findTop5ByUsuario_Institucion_IdOrderByFechaDesc(Long institucionId);
    List<ActividadesDiarias> findByUsuarioId(Long usuarioId);
}