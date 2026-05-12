package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingDTO {
    private Long rank;
    private String username;
    private BigDecimal huellaKgCO2e;

    private BigDecimal huellaTransporte;
    private BigDecimal huellaEnergia;
    private BigDecimal huellaAlimentacion;
    private BigDecimal huellaResiduos;
}