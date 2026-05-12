package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.RecursoEducativoDTO;
import com.upc.purevibeb.entities.RecursoEducativo.Tipo;
import com.upc.purevibeb.interfaces.IRecursoEducativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recursos") 
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class RecursoEducativoController {

    @Autowired
    private IRecursoEducativoService service;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Page<RecursoEducativoDTO>> listar(
            @RequestParam(required = false) Tipo tipo,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(service.listar(tipo, q, page, size));
    }
}