package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.EnergiaDTO;
import com.upc.purevibeb.interfaces.IEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class EnergiaController {

    @Autowired
    private IEnergiaService energiaService;

    @PostMapping("/energia")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<EnergiaDTO> crear(@RequestBody EnergiaDTO dto) {
        EnergiaDTO creado = energiaService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/por-actividadE/{actividadId}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<List<EnergiaDTO>> listarPorActividad(@PathVariable Long actividadId) {
        List<EnergiaDTO> lista = energiaService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/energia/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        energiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}