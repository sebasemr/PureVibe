package com.upc.purevibeb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "energia")
@Getter
@Setter
public class Energia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // "electricidad", "calefaccion", "dispositivos"

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consumo;

    @Column(nullable = false)
    private String unidad; // "kWh", "horas", "rango_horas"

    // Relación: Muchos registros de energía pertenecen a UNA actividad diaria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    private ActividadesDiarias actividad;
}