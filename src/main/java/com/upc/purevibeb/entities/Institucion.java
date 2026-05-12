package com.upc.purevibeb.entities;

import com.upc.purevibeb.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "instituciones")
@Getter
@Setter
public class Institucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 100)
    private String tipo;

    @Column(nullable = false, unique = true, length = 10)
    private String codigoInvitacion;

    @OneToOne
    @JoinColumn(name = "admin_user_id", referencedColumnName = "id")
    private User admin;

    @OneToMany(mappedBy = "institucion")
    private List<User> miembros;

    public Institucion() {
        this.codigoInvitacion = "INST-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}