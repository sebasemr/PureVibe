package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecompensaDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer costoPuntos;
}