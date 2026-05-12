package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.ReporteDTO;
import com.upc.purevibeb.interfaces.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class ReporteController {

    @Autowired
    private IReporteService reporteService;

    public record ReporteRequest(Long actividadId) {}

    @PostMapping("/calcular")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<ReporteDTO> calcularReporte(@RequestBody ReporteRequest request) {
        ReporteDTO reporte = reporteService.calcularReporte(request.actividadId());
        return ResponseEntity.ok(reporte);
    }
}