package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.TransporteDTO;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.entities.Transporte;
import com.upc.purevibeb.interfaces.ITransporteService;
import com.upc.purevibeb.repositories.ActividadesDiariasRepository;
import com.upc.purevibeb.repositories.TransporteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransporteService implements ITransporteService {

    @Autowired
    private TransporteRepository transporteRepo;

    @Autowired
    private ActividadesDiariasRepository actividadesRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransporteDTO crear(TransporteDTO dto) {
        // 1. Buscar la entidad "madre" (ActividadesDiarias)
        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad no encontrada con ID: " + dto.getActividadId()));

        // 2. Convertir DTO a Entidad
        Transporte transporte = modelMapper.map(dto, Transporte.class);

        // 3. Asignar la entidad "madre"
        transporte.setActividad(actividad);

        // 4. Guardar y convertir de vuelta a DTO
        Transporte guardado = transporteRepo.save(transporte);
        return modelMapper.map(guardado, TransporteDTO.class);
    }

    @Override
    public List<TransporteDTO> listarPorActividad(Long actividadId) {
        return transporteRepo.findByActividadId(actividadId)
                .stream()
                .map(transporte -> modelMapper.map(transporte, TransporteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long transporteId) {
        if (!transporteRepo.existsById(transporteId)) {
            throw new NoSuchElementException("Transporte no encontrado con ID: " + transporteId);
        }
        transporteRepo.deleteById(transporteId);
    }
}