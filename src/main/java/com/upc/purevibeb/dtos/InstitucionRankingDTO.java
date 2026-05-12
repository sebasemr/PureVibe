package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitucionRankingDTO {
    private Long rank;
    private String nombreInstitucion;
    private String tipo;
    private Integer cantidadMiembros;
    private BigDecimal huellaTotal;
}