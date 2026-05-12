package com.upc.purevibeb.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private String username;
    private String email;
    private BigDecimal huellaTotalKgCO2e;
    private String nombreFamilia;
    private String nombreInstitucion;
}