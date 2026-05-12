package com.upc.purevibeb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResiduoDTO {

    private Long id;
    private String tipo;
    private BigDecimal pesoKg;
    private Boolean reciclaje;
    private Long actividadId;
}