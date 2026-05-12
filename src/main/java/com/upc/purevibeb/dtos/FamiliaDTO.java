package com.upc.purevibeb.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FamiliaDTO {
    private Long id;
    private String nombre;
    private String codigoInvitacion;
    private String adminUsername;
    private List<String> miembrosUsernames;
}