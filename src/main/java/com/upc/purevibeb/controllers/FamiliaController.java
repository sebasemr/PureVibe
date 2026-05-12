package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.FamiliaCrearRequest;
import com.upc.purevibeb.dtos.FamiliaDTO;
import com.upc.purevibeb.dtos.FamiliaDashboardDTO;
import com.upc.purevibeb.dtos.FamiliaUnirseRequest;
import com.upc.purevibeb.interfaces.IFamiliaService;
import com.upc.purevibeb.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/familia")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class FamiliaController {

    @Autowired
    private IFamiliaService familiaService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> crearFamilia(
            @RequestBody FamiliaCrearRequest request,
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            // Pasamos el ID del usuario logueado al servicio
            FamiliaDTO dto = familiaService.crearFamilia(request, userDetails.getId());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/unirse")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unirseAFamilia(
            @RequestBody FamiliaUnirseRequest request,
            @AuthenticationPrincipal User userDetails
    ) {
        try {
            FamiliaDTO dto = familiaService.unirseAFamilia(request, userDetails.getId());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/mi-dashboard")
    @PreAuthorize("hasRole('FAMILIAR') or hasRole('USER')")
    public ResponseEntity<?> getMiDashboard(@AuthenticationPrincipal User userDetails) {
        try {
            FamiliaDashboardDTO dto = familiaService.getDashboardFamiliar(userDetails.getId());
            return ResponseEntity.ok(dto);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}