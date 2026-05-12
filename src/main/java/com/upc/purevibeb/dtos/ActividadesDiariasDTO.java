package com.upc.purevibeb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ActividadesDiariasDTO {

    private Long id;
    private LocalDate fecha;
    private Long usuarioId;
    private String descripcion;
    private String tipoCombustible;
    private BigDecimal distanciaKm;


}