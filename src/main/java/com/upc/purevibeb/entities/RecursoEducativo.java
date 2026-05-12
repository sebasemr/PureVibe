package com.upc.purevibeb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "recursos_educativos")
@Getter
@Setter
public class RecursoEducativo {

    public enum Tipo { ARTICULO, VIDEO, PODCAST }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String titulo;


    @Column
    private String tema;

    @Column
    private String fuente;

    @Enumerated(EnumType.STRING)
    @Column
    private Tipo tipo;

    @Column(nullable = false, length = 2048)  // suficiente para URLs largas
    private String url;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}