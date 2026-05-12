package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.TransporteDTO;

import java.util.List;

public interface ITransporteService {
    TransporteDTO crear(TransporteDTO dto);
    List<TransporteDTO> listarPorActividad(Long actividadId);
    void eliminar(Long transporteId);
}