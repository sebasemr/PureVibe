package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.RecomendacionDTO;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.services.RecomendacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<List<RecomendacionDTO>> obtenerRecomendaciones(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(recomendacionService.generarRecomendaciones(userDetails.getId()));
    }
}