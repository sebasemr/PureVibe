package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.FamiliaCrearRequest;
import com.upc.purevibeb.dtos.FamiliaDTO;
import com.upc.purevibeb.dtos.FamiliaDashboardDTO;
import com.upc.purevibeb.dtos.FamiliaUnirseRequest;

public interface IFamiliaService {
    FamiliaDTO crearFamilia(FamiliaCrearRequest request, Long usuarioId);
    FamiliaDTO unirseAFamilia(FamiliaUnirseRequest request, Long usuarioId);
    FamiliaDashboardDTO getDashboardFamiliar(Long usuarioId);
}