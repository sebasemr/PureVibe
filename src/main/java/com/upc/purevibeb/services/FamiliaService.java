package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.*;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.entities.Familia;
import com.upc.purevibeb.interfaces.IFamiliaService;
import com.upc.purevibeb.repositories.ActividadesDiariasRepository;
import com.upc.purevibeb.repositories.FamiliaRepository;
import com.upc.purevibeb.security.entities.Role;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.RoleRepository;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FamiliaService implements IFamiliaService {

    @Autowired private FamiliaRepository familiaRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private ModelMapper mapper;
    @Autowired private ActividadesDiariasRepository actividadRepo;

    @Override
    @Transactional
    public FamiliaDTO crearFamilia(FamiliaCrearRequest request, Long usuarioId) {
        User adminUser = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        if (adminUser.getFamilia() != null) {
            throw new IllegalStateException("Ya perteneces a una familia.");
        }
        if (familiaRepo.findByAdminId(usuarioId).isPresent()) {
            throw new IllegalStateException("Este usuario ya administra una familia.");
        }

        Familia familia = new Familia();
        familia.setNombre(request.getNombreFamilia());
        familia.setAdmin(adminUser);

        Role familiarRol = roleRepo.findByAuthority("ROLE_FAMILIAR")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_FAMILIAR no encontrado. Revisa import.sql"));

        adminUser.getRoles().add(familiarRol);

        adminUser.setFamilia(familia);

        familiaRepo.save(familia);
        userRepo.save(adminUser);

        return convertToDto(familia);
    }

    @Override
    @Transactional
    public FamiliaDTO unirseAFamilia(FamiliaUnirseRequest request, Long usuarioId) {
        User miembroUser = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        Familia familia = familiaRepo.findByCodigoInvitacion(request.getCodigoInvitacion())
                .orElseThrow(() -> new IllegalArgumentException("Código de invitación inválido"));

        if (miembroUser.getFamilia() != null) {
            throw new IllegalStateException("Este usuario ya pertenece a una familia.");
        }

        Role miembroRol = roleRepo.findByAuthority("ROLE_FAMILIAR")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_FAMILIAR no encontrado. Revisa import.sql"));

        miembroUser.getRoles().add(miembroRol);
        miembroUser.setFamilia(familia);

        userRepo.save(miembroUser);

        Familia familiaActualizada = familiaRepo.findById(familia.getId()).get();
        return convertToDto(familiaActualizada);
    }

    @Override
    @Transactional(readOnly = true) // Es solo de lectura
    public FamiliaDashboardDTO getDashboardFamiliar(Long usuarioId) {
        // 1. Obtener el usuario y su familia
        User user = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        Familia familia = user.getFamilia();
        if (familia == null) {
            throw new IllegalStateException("Este usuario no pertenece a ninguna familia.");
        }

        // 2. Obtener todos los miembros de esa familia
        List<User> miembros = userRepo.findAllByFamiliaId(familia.getId());

        // 3. Calcular la huella total de la familia
        // (Sumamos la 'huellaTotalKgCO2e' de la Calculadora Personal de cada miembro)
        BigDecimal huellaTotal = miembros.stream()
                .filter(m -> m.getHuellaTotalKgCO2e() != null)
                .map(User::getHuellaTotalKgCO2e)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Obtener el feed de actividades
        List<ActividadesDiarias> actividades = actividadRepo
                .findTop5ByUsuario_FamiliaIdOrderByFechaDesc(familia.getId());

        // 5. Mapear todo al DTO
        FamiliaDashboardDTO dto = new FamiliaDashboardDTO();
        dto.setNombreFamilia(familia.getNombre());
        dto.setCodigoInvitacion(familia.getCodigoInvitacion());
        dto.setAdminUsername(familia.getAdmin().getUsername());
        dto.setMiembrosUsernames(miembros.stream()
                .map(User::getUsername)
                .collect(Collectors.toList()));
        dto.setHuellaTotalFamiliaKg(huellaTotal);
        dto.setFeedActividades(actividades.stream()
                .map(this::mapActividadToDto)
                .collect(Collectors.toList()));

        return dto;
    }

    // Helper para el feed
    private ActividadRecienteDTO mapActividadToDto(ActividadesDiarias act) {
        ActividadRecienteDTO dto = new ActividadRecienteDTO();
        dto.setUsername(act.getUsuario().getUsername());
        dto.setDescripcion(act.getDescripcion());
        dto.setFecha(act.getFecha());
        return dto;
    }

    private FamiliaDTO convertToDto(Familia familia) {
        FamiliaDTO dto = new FamiliaDTO();
        dto.setId(familia.getId());
        dto.setNombre(familia.getNombre());
        dto.setCodigoInvitacion(familia.getCodigoInvitacion());
        dto.setAdminUsername(familia.getAdmin().getUsername());

        List<String> miembros = userRepo.findAllByFamiliaId(familia.getId()).stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        dto.setMiembrosUsernames(miembros);
        return dto;
    }
}