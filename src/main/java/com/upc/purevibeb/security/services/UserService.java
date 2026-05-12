package com.upc.purevibeb.security.services;

import com.upc.purevibeb.security.dtos.ProfileDTO;
import com.upc.purevibeb.security.dtos.RegisterRequestDTO;
import com.upc.purevibeb.security.dtos.UpdatePasswordDTO;
import com.upc.purevibeb.security.entities.Role;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.RoleRepository;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Error: El nombre de usuario ya está en uso.");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Error: El email ya está en uso.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_USER' no encontrado en import.sql"));
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + id));
    }

    public boolean hasId(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userLogged = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Usuario logueado no encontrado"));
        return userLogged.getId().equals(id);
    }

    public ProfileDTO getProfile(Long userId) {
        User user = this.findById(userId);

        String nombreFamilia = (user.getFamilia() != null) ? user.getFamilia().getNombre() : null;

        String nombreInstitucion = (user.getInstitucion() != null) ? user.getInstitucion().getNombre() : null;

        return new ProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getHuellaTotalKgCO2e(),
                nombreFamilia,
                nombreInstitucion
        );
    }

    @Transactional
    public void changePassword(Long userId, UpdatePasswordDTO dto) {
        User user = this.findById(userId);

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

}