package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.TransporteDTO;
import com.upc.purevibeb.interfaces.ITransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class TransporteController {

    @Autowired
    private ITransporteService transporteService;

    @PostMapping("/transporte")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<TransporteDTO> crear(@RequestBody TransporteDTO dto) {
        TransporteDTO creado = transporteService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/por-actividad/{actividadId}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<List<TransporteDTO>> listarPorActividad(@PathVariable Long actividadId) {
        List<TransporteDTO> lista = transporteService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/transporte/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}