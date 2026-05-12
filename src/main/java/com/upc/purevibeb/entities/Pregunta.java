package com.upc.purevibeb.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "preguntas_quiz")
@Data
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String enunciado;

    @Column(nullable = false)
    private String opcionA;

    @Column(nullable = false)
    private String opcionB;

    @Column(nullable = false)
    private String opcionC;

    @Column(nullable = false)
    private String opcionD;

    @Column(nullable = false)
    private String respuestaCorrecta;

    @Column(nullable = false)
    private Integer puntosOtorgados;
}