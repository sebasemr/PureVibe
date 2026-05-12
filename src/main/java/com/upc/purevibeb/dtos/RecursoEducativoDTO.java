package com.upc.purevibeb.dtos;

import lombok.Data;

@Data
public class RecursoEducativoDTO {
    private Long id;
    private String titulo;
    private String tipo;
    private String url;
    private String tema;
    private String fuente;
}