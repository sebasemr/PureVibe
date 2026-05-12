package com.upc.purevibeb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FactoresEmisionDTO {

    private Long id;
    private String categoria;
    private String subcategoria;
    private String unidadBase;
    private BigDecimal factorKgco2ePerUnidad;
    private String fuente;
    private Boolean vigente;
}