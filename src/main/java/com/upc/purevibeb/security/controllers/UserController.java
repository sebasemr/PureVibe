package com.upc.purevibeb.security.controllers;

import com.upc.purevibeb.security.dtos.UserDTO;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api/users") // <-- Cambiamos la ruta base a "/api/users"
public class UserController {

    @Autowired
    private UserService userService;

    // (Quitamos el PasswordEncoder, el UserService ya lo tiene)

    /**
     * Endpoint para obtener una lista de todos los usuarios (SOLO ADMIN)
     * URL: GET /api/users
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    /**
     * Endpoint para obtener un usuario por su ID (ADMIN o el PROPIO USUARIO)
     * URL: GET /api/users/1
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.hasId(#id)") // ¡Seguridad avanzada!
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(convertToDto(user));
    }

    // (Los métodos POST /user, /rol y /save... se eliminan
    // porque esa lógica ahora está en AuthController y UserService)

    /**
     * Helper para convertir la Entidad User (con contraseña)
     * a un DTO UserDTO (sin contraseña)
     */
    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                // (Mapeamos los roles a una lista de Strings)
                user.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .collect(Collectors.toList())
        );
    }
}