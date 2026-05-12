package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliaRankingDTO {
    private Long rank;
    private String nombreFamilia;
    private Integer cantidadMiembros;
    private BigDecimal huellaTotalFamilia;
}