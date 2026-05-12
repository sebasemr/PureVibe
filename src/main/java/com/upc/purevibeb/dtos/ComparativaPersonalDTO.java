package com.upc.purevibeb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComparativaPersonalDTO {
    private BigDecimal tuHuella;
    private BigDecimal promedioComunidad;
    private BigDecimal mejorHuella;
}