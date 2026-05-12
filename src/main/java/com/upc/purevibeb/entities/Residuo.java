package com.upc.purevibeb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "residuos")
@Getter
@Setter
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lo mantenemos por flexibilidad, el front puede enviar "general"
    private String tipo;

    @Column(name = "peso_kg", precision = 10, scale = 2)
    private BigDecimal pesoKg; // De "Cantidad de residuos generados"

    @Column(nullable = false)
    private Boolean reciclaje; // De "¿Reciclaste hoy?"

    // Relación: Muchos registros de residuos pertenecen a UNA actividad diaria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    private ActividadesDiarias actividad;
}