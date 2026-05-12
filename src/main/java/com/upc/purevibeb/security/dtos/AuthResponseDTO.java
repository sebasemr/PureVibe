package com.upc.purevibeb.security.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AuthResponseDTO {
    private String token;
    private List<String> roles;
    private Long usuarioId;

    public AuthResponseDTO(String token, List<String> roles, Long usuarioId) {
        this.token = token;
        this.roles = roles;
        this.usuarioId = usuarioId;
    }
}