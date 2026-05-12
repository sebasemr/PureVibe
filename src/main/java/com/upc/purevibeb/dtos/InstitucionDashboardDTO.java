package com.upc.purevibeb.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InstitucionDashboardDTO {
    private String nombre;
    private String tipo;
    private String codigoInvitacion;
    private String adminUsername;
    private int totalMiembros;
    private BigDecimal huellaTotalInstitucion;
    private List<ActividadRecienteDTO> feedActividades;
}
