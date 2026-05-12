package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.FamiliaRankingDTO;
import com.upc.purevibeb.dtos.InstitucionRankingDTO;
import com.upc.purevibeb.dtos.RankingDTO;
import com.upc.purevibeb.interfaces.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class RankingController {

    @Autowired
    private IRankingService rankingService;


    @GetMapping
    public ResponseEntity<List<RankingDTO>> getRanking(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(rankingService.getRankingPersonal(page, size));
    }

    @GetMapping("/familiar")
    @PreAuthorize("hasRole('FAMILIAR') or hasRole('USER')")
    public ResponseEntity<List<FamiliaRankingDTO>> getRankingFamiliar() {
        return ResponseEntity.ok(rankingService.getRankingFamiliar());
    }

    @GetMapping("/institucional")
    @PreAuthorize("hasRole('INSTITUCION') or hasRole('USER')")
    public ResponseEntity<List<InstitucionRankingDTO>> getRankingInstitucional() {
        return ResponseEntity.ok(rankingService.getRankingInstitucional());
    }
}