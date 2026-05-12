package com.upc.purevibeb.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadoGamificacionDTO {
    private int puntosTotales;
    private int puntosGanados;
    private String mensaje;
}