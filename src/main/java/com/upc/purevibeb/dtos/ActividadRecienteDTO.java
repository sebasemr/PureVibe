package com.upc.purevibeb.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ActividadRecienteDTO {
    private String username;
    private String descripcion;
    private LocalDate fecha;
}