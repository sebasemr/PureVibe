package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.ComparativaPersonalDTO;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ComparativaService {

    @Autowired
    private UserRepository userRepo;

    public ComparativaPersonalDTO obtenerComparativa(Long usuarioId) {
        User user = userRepo.findById(usuarioId).orElseThrow();
        BigDecimal tuHuella = user.getHuellaTotalKgCO2e();
        if (tuHuella == null) tuHuella = BigDecimal.ZERO;

        Double avg = userRepo.obtenerPromedioHuella();
        BigDecimal promedio = (avg != null) ? BigDecimal.valueOf(avg) : BigDecimal.ZERO;

        BigDecimal mejor = userRepo.obtenerMejorHuella();
        if (mejor == null) mejor = BigDecimal.ZERO;

        return new ComparativaPersonalDTO(
                tuHuella.setScale(2, RoundingMode.HALF_UP),
                promedio.setScale(2, RoundingMode.HALF_UP),
                mejor.setScale(2, RoundingMode.HALF_UP)
        );
    }
}