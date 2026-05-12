package com.upc.purevibeb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EnergiaDTO {

    private Long id;
    private String tipo;
    private BigDecimal consumo;
    private String unidad;
    private Long actividadId;
}