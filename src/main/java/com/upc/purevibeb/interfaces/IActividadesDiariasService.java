package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.ActividadesDiariasDTO;
import com.upc.purevibeb.dtos.ReporteTransporteDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IActividadesDiariasService {

    ActividadesDiariasDTO crearActividad(Long usuarioId, LocalDate fecha, String descripcion);

    Optional<ActividadesDiariasDTO> obtenerPorId(Long actividadId);

    void eliminar(Long actividadId);

    List<ReporteTransporteDTO> obtenerReporteTransportePorCombustibleYDistancia(
            Long usuarioId,
            String tipoCombustible,
            BigDecimal distanciaMinKm,
            BigDecimal distanciaMaxKm
    );
}