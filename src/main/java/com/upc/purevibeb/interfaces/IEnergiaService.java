package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.EnergiaDTO;

import java.util.List;

public interface IEnergiaService {
    EnergiaDTO crear(EnergiaDTO dto);
    List<EnergiaDTO> listarPorActividad(Long actividadId);
    void eliminar(Long energiaId);
}