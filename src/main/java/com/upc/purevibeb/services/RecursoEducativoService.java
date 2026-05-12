package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.RecursoEducativoDTO;
import com.upc.purevibeb.entities.RecursoEducativo.Tipo;
import com.upc.purevibeb.interfaces.IRecursoEducativoService;
import com.upc.purevibeb.repositories.RecursoEducativoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RecursoEducativoService implements IRecursoEducativoService {

    @Autowired
    private RecursoEducativoRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<RecursoEducativoDTO> listar(Tipo tipo, String q, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creadoEn"));
        String pattern = (q == null || q.isBlank()) ? null : "%" + q.toLowerCase() + "%";

        return repo.search(tipo, pattern, pageable)
                .map(r -> mapper.map(r, RecursoEducativoDTO.class));
    }
}