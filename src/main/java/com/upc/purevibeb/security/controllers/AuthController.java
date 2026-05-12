package com.upc.purevibeb.security.controllers;

import com.upc.purevibeb.security.dtos.*;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.services.CustomUserDetailsService;
import com.upc.purevibeb.security.services.PasswordService;
import com.upc.purevibeb.security.services.UserService;
import com.upc.purevibeb.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api/auth") // (Tu ruta está bien)
public class AuthController {

    // --- CORRECCIÓN 1: Inyección de Dependencias ---
    // (Movemos todos los servicios aquí)
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordService passwordService;
    private final UserService userService;

    // (Tu constructor original estaba incompleto)
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          CustomUserDetailsService userDetailsService,
                          PasswordService passwordService, // <-- CORRECCIÓN (Añadido)
                          UserService userService) {      // <-- CORRECCIÓN (Añadido)
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordService = passwordService; // <-- CORRECCIÓN (Añadido)
        this.userService = userService;      // <-- CORRECCIÓN (Añadido)
    }
    // --- Fin Corrección 1 ---

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Usuario o contraseña incorrectos", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        // (Tu lógica para obtener ID y Roles es correcta)
        User user = (User) userDetails;
        Long usuarioId = user.getId();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // --- CORRECCIÓN 2: Respuesta de Login ---
        // (Creamos el DTO de respuesta. Tu frontend espera esto en el BODY)
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, roles, usuarioId);

        // (Ya no necesitamos enviar el Header, el frontend lo lee del body)
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            userService.registerUser(registerRequest);
            return ResponseEntity.ok("¡Usuario registrado exitosamente!");
        } catch (RuntimeException e) {
            // --- CORRECCIÓN 3: Respuesta de Error ---
            // (Enviamos un JSON { "message": "..." } en lugar de un string plano)
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordForgotRequest request) {
        try {
            // (Esto ahora funcionará porque passwordService está inyectado)
            passwordService.forgotPassword(request.getEmail());
        } catch (Exception e) {
            // No revelamos si el email existe o no, por seguridad
        }
        // Siempre enviamos OK
        return ResponseEntity.ok(Map.of("message", "Si existe una cuenta asociada a ese email, se ha enviado un enlace de recuperación."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                           @RequestBody PasswordResetRequest request) {
        try {
            // (Esto ahora funcionará)
            passwordService.resetPassword(token, request.getNewPassword());
            return ResponseEntity.ok(Map.of("message", "Contraseña restablecida con éxito."));
        } catch (Exception e) {
            // (Esto atrapa "Token expirado" o "Token inválido" y lo muestra)
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}