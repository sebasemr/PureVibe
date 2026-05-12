package com.upc.purevibeb.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReporteTransporteDTO {

    private Long actividadId;
    private Long transporteId;
    private LocalDate fecha;
    private String medio;
    private String tipoCombustible;
    private BigDecimal distanciaKm;
    private BigDecimal consumoLitros100km;
    private String descripcion;
}
