package com.upc.purevibeb.entities;

import com.upc.purevibeb.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "actividades_diarias")
@Getter
@Setter
public class ActividadesDiarias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actividad_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    // Relación: Muchas hojas de actividades pertenecen a un Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    private String descripcion;

    // Relación: Una actividad diaria puede tener MÚLTIPLES registros de transporte
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transporte> transportes;

    // Relación: Una actividad diaria puede tener MÚLTIPLES registros de energía
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Energia> energias;

    // Relación: Una actividad diaria puede tener MÚLTIPLES registros de residuos
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Residuo> residuos;
}