package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.*;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.entities.Institucion;
import com.upc.purevibeb.repositories.ActividadesDiariasRepository;
import com.upc.purevibeb.repositories.InstitucionRepository;
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
import java.util.stream.Collectors;

@Service
public class InstitucionService {

    @Autowired private InstitucionRepository instRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private ActividadesDiariasRepository actRepo;
    @Autowired private ModelMapper mapper;

    @Transactional
    public InstitucionDTO crear(InstitucionCrearRequest req, Long userId) {
        User admin = userRepo.findById(userId).orElseThrow();

        if (admin.getInstitucion() != null) throw new IllegalStateException("Ya perteneces a una institución");

        Institucion inst = new Institucion();
        inst.setNombre(req.getNombre());
        inst.setTipo(req.getTipo());
        inst.setAdmin(admin);

        Role roleInst = roleRepo.findByAuthority("ROLE_INSTITUCION").orElseThrow();
        admin.getRoles().add(roleInst);
        admin.setInstitucion(inst);

        instRepo.save(inst);
        userRepo.save(admin);

        return mapper.map(inst, InstitucionDTO.class);
    }

    @Transactional
    public InstitucionDTO unirse(InstitucionUnirseRequest req, Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Institucion inst = instRepo.findByCodigoInvitacion(req.getCodigoInvitacion())
                .orElseThrow(() -> new IllegalArgumentException("Código inválido"));

        if (user.getInstitucion() != null) throw new IllegalStateException("Ya perteneces a una institución");

        Role roleInst = roleRepo.findByAuthority("ROLE_INSTITUCION").orElseThrow();
        user.getRoles().add(roleInst);
        user.setInstitucion(inst);
        userRepo.save(user);

        return mapper.map(inst, InstitucionDTO.class);
    }

    @Transactional(readOnly = true)
    public InstitucionDashboardDTO getDashboard(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Institucion inst = user.getInstitucion();
        if (inst == null) throw new IllegalStateException("No tienes institución");

        List<User> miembros = userRepo.findAllByInstitucionId(inst.getId());

        BigDecimal huellaTotal = miembros.stream()
                .filter(m -> m.getHuellaTotalKgCO2e() != null)
                .map(User::getHuellaTotalKgCO2e)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<ActividadesDiarias> feed = actRepo.findTop5ByUsuario_Institucion_IdOrderByFechaDesc(inst.getId());

        InstitucionDashboardDTO dto = new InstitucionDashboardDTO();
        dto.setNombre(inst.getNombre());
        dto.setTipo(inst.getTipo());
        dto.setCodigoInvitacion(inst.getCodigoInvitacion());
        dto.setAdminUsername(inst.getAdmin().getUsername());
        dto.setTotalMiembros(miembros.size());
        dto.setHuellaTotalInstitucion(huellaTotal);
        dto.setFeedActividades(feed.stream().map(this::mapActividad).collect(Collectors.toList()));

        return dto;
    }

    private ActividadRecienteDTO mapActividad(ActividadesDiarias a) {
        ActividadRecienteDTO dto = new ActividadRecienteDTO();
        dto.setUsername(a.getUsuario().getUsername());
        dto.setDescripcion(a.getDescripcion());
        dto.setFecha(a.getFecha());
        return dto;
    }
}