package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.ComparativaPersonalDTO;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.services.ComparativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comparativa")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class ComparativaController {

    @Autowired
    private ComparativaService comparativaService;

    @GetMapping("/personal")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<ComparativaPersonalDTO> getComparativaPersonal(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(comparativaService.obtenerComparativa(user.getId()));
    }
}