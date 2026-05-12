package com.upc.purevibeb.controllers;

import com.upc.purevibeb.entities.Pregunta;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class QuizController {

    @Autowired private QuizService quizService;

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<List<Pregunta>> obtenerQuiz() {
        return ResponseEntity.ok(quizService.obtenerQuizAleatorio());
    }

    @PostMapping("/reclamar-premio")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<?> reclamarPremio(@RequestBody Map<String, Integer> body, @AuthenticationPrincipal User user) {
        Integer puntos = body.get("puntos");
        if (puntos > 100) puntos = 100;

        quizService.registrarVictoria(user.getId(), puntos);
        return ResponseEntity.ok(Map.of("message", "Puntos otorgados"));
    }
}