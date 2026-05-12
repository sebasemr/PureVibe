package com.upc.purevibeb.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FamiliaDashboardDTO {
    private String nombreFamilia;
    private String codigoInvitacion;
    private String adminUsername;
    private List<String> miembrosUsernames;
    private BigDecimal huellaTotalFamiliaKg;
    private List<ActividadRecienteDTO> feedActividades;
}