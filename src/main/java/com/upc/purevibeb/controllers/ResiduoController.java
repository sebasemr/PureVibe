package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.ResiduoDTO;
import com.upc.purevibeb.interfaces.IResiduoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class ResiduoController {

    @Autowired
    private IResiduoService residuoService;

    @PostMapping("/residuo")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<ResiduoDTO> crear(@RequestBody ResiduoDTO dto) {
        ResiduoDTO creado = residuoService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/por-actividadR/{actividadId}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<List<ResiduoDTO>> listarPorActividad(@PathVariable Long actividadId) {
        List<ResiduoDTO> lista = residuoService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/residuo/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        residuoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}