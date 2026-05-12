package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReporteDTO {
    private Long actividadId;
    private BigDecimal totalKgCO2e;
    private BigDecimal transporteKgCO2e;
    private BigDecimal energiaKgCO2e;
    private BigDecimal residuosKgCO2e;
}