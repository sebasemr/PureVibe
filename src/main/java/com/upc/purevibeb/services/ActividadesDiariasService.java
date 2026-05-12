package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.ActividadesDiariasDTO;
import com.upc.purevibeb.dtos.ReporteTransporteDTO;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.interfaces.IActividadesDiariasService;
import com.upc.purevibeb.repositories.ActividadesDiariasRepository;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadesDiariasService implements IActividadesDiariasService {

    @Autowired
    private ActividadesDiariasRepository actividadesRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ActividadesDiariasDTO crearActividad(Long usuarioId, LocalDate fecha, String descripcion) {

        Optional<ActividadesDiarias> existente = actividadesRepo.findByUsuarioIdAndFecha(usuarioId, fecha);

        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una actividad registrada para esta fecha.");
        }

        ActividadesDiarias nuevaActividad = new ActividadesDiarias();

        User usuario = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no existe: " + usuarioId));

        nuevaActividad.setUsuario(usuario);
        nuevaActividad.setFecha(fecha);
        nuevaActividad.setDescripcion(descripcion);

        ActividadesDiarias actividadGuardada = actividadesRepo.save(nuevaActividad);

        return modelMapper.map(actividadGuardada, ActividadesDiariasDTO.class);
    }

    @Override
    public Optional<ActividadesDiariasDTO> obtenerPorId(Long actividadId) {
        return actividadesRepo.findById(actividadId)
                .map(entity -> modelMapper.map(entity, ActividadesDiariasDTO.class));
    }

    @Override
    public void eliminar(Long actividadId) {
        if (!actividadesRepo.existsById(actividadId)) {
            throw new NoSuchElementException("Actividad no encontrada con ID: " + actividadId);
        }
        actividadesRepo.deleteById(actividadId);
    }
    @Override
    public List<ReporteTransporteDTO> obtenerReporteTransportePorCombustibleYDistancia(
            Long usuarioId,
            String tipoCombustible,
            BigDecimal distanciaMinKm,
            BigDecimal distanciaMaxKm) {

        // Validar que el usuario existe
        if (!userRepo.existsById(usuarioId)) {
            throw new NoSuchElementException("Usuario no encontrado con ID: " + usuarioId);
        }

        // Obtener todas las actividades del usuario
        List<ActividadesDiarias> actividades = actividadesRepo.findByUsuarioId(usuarioId);

        // Filtrar transportes por tipoCombustible y rango de distancia
        return actividades.stream()
                .flatMap(actividad -> actividad.getTransportes().stream()
                        .filter(transporte -> {
                            // Filtrar por tipoCombustible
                            boolean cumpleTipoCombustible = tipoCombustible == null ||
                                    tipoCombustible.isEmpty() ||
                                    transporte.getTipoCombustible().equalsIgnoreCase(tipoCombustible);

                            // Filtrar por rango de distancia
                            BigDecimal distancia = transporte.getDistanciaKm();
                            boolean cumpleDistancia = (distanciaMinKm == null || distancia.compareTo(distanciaMinKm) >= 0) &&
                                    (distanciaMaxKm == null || distancia.compareTo(distanciaMaxKm) <= 0);

                            return cumpleTipoCombustible && cumpleDistancia;
                        })
                        .map(transporte -> new ReporteTransporteDTO(
                                actividad.getId(),
                                transporte.getId(),
                                actividad.getFecha(),
                                transporte.getMedio(),
                                transporte.getTipoCombustible(),
                                transporte.getDistanciaKm(),
                                transporte.getConsumoLitros100km(),
                                actividad.getDescripcion()
                        ))
                )
                .collect(Collectors.toList());
    }
}