package com.upc.purevibeb.security.services;

import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // (En un proyecto real, inyectarías un EmailService aquí)
    // @Autowired
    // private EmailService emailService;

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));

        // 1. Genera un token único
        String token = UUID.randomUUID().toString();

        // 2. Establece la expiración (ej. 1 hora)
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        // 3. (Simulación) En un proyecto real, aquí enviarías el email:
        // String resetLink = "http://localhost:4200/reset-password?token=" + token;
        // emailService.send(email, "Reseteo de Contraseña purevibe", "Clic aquí: " + resetLink);

        // 4. (Para desarrollo) Imprimimos el token en la consola para que puedas probar
        System.out.println("Token de reseteo para " + email + ": " + token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        // 1. Busca al usuario por el token
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o no encontrado"));

        // 2. Verifica que el token no haya expirado
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        // 3. Actualiza la contraseña (encriptada)
        user.setPassword(passwordEncoder.encode(newPassword));

        // 4. Anula el token para que no se pueda reusar
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);
    }
}