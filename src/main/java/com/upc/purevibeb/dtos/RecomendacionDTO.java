package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecomendacionDTO {
    private String categoria;
    private String descripcion;
    private String icono;
    private String nivelUrgencia;
}