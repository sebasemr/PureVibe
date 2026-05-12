package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.RecursoEducativoDTO;
import com.upc.purevibeb.entities.RecursoEducativo.Tipo;
import org.springframework.data.domain.Page;

public interface IRecursoEducativoService {
    Page<RecursoEducativoDTO> listar(Tipo tipo, String q, int page, int size);
}