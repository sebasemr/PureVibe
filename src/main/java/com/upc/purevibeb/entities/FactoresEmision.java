package com.upc.purevibeb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "factores_emision")
@Getter
@Setter
public class FactoresEmision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Categoría principal, ej: "transporte", "energia", "residuos"
    @Column(nullable = false)
    private String categoria;

    // Subcategoría, ej: "auto", "moto", "electricidad", "general"
    @Column(nullable = false)
    private String subcategoria;

    // La unidad en la que se mide el dato del usuario, ej: "km", "kWh", "kg"
    @Column(nullable = false)
    private String unidadBase;

    // El valor para multiplicar: (kg de CO2e) / (unidad base)
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal factorKgco2ePerUnidad;

    // De dónde salió este factor (ej: "MINAM", "Estudio XYZ")
    private String fuente;

    // Para saber si debemos usar este factor o es uno antiguo
    @Column(nullable = false)
    private Boolean vigente = true;
}