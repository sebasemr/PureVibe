package com.upc.purevibeb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transporte")
@Getter
@Setter
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medio;

    @Column(precision = 10, scale = 2)
    private BigDecimal distanciaKm;

    private String tipoCombustible;

    @Column(precision = 10, scale = 2)
    private BigDecimal consumoLitros100km;

    @Column(precision = 10, scale = 2)
    private BigDecimal kilometrosVolados;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    private ActividadesDiarias actividad;
}