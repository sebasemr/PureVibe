package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.FactoresEmisionDTO;

import java.util.List;
import java.util.Optional;

public interface IFactoresEmisionService {
    Optional<FactoresEmisionDTO> buscarVigente(String categoria, String subcategoria, String unidadBase);
    FactoresEmisionDTO crear(FactoresEmisionDTO dto);
    FactoresEmisionDTO actualizar(Long id, FactoresEmisionDTO dto);
    Optional<FactoresEmisionDTO> obtener(Long id);
    List<FactoresEmisionDTO> listar();
    void eliminar(Long id);
}