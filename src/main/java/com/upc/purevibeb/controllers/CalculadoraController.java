package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.CalculadoraPersonalDTO;
import com.upc.purevibeb.interfaces.ICalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class CalculadoraController {

    @Autowired
    private ICalculadoraService calculadoraService;

    @PostMapping("/calculadora")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<CalculadoraPersonalDTO> calcularPersonal(
            @RequestBody CalculadoraPersonalDTO request) {

        CalculadoraPersonalDTO response = calculadoraService.calcularHuellaPersonal(request);
        return ResponseEntity.ok(response);
    }
}