package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.ResiduoDTO;

import java.util.List;

public interface IResiduoService {
    ResiduoDTO crear(ResiduoDTO dto);
    List<ResiduoDTO> listarPorActividad(Long actividadId);
    void eliminar(Long residuoId);
}