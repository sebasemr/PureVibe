package com.upc.purevibeb.dtos;

import lombok.Data;

@Data
public class InstitucionDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String codigoInvitacion;
    private int cantidadMiembros;
}