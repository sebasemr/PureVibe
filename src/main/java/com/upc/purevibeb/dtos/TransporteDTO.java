package com.upc.purevibeb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransporteDTO {

    private Long id;
    private String medio;
    private BigDecimal distanciaKm;
    private String tipoCombustible;
    private BigDecimal consumoLitros100km;
    private BigDecimal kilometrosVolados;
    private Long actividadId;
}