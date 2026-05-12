package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.ReporteDTO;

public interface IReporteService {
    ReporteDTO calcularReporte(Long actividadId);
}