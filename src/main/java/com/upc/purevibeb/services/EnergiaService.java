package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.EnergiaDTO;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.entities.Energia;
import com.upc.purevibeb.interfaces.IEnergiaService;
import com.upc.purevibeb.repositories.ActividadesDiariasRepository;
import com.upc.purevibeb.repositories.EnergiaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EnergiaService implements IEnergiaService {

    @Autowired
    private EnergiaRepository energiaRepo;

    @Autowired
    private ActividadesDiariasRepository actividadesRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EnergiaDTO crear(EnergiaDTO dto) {
        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad no encontrada con ID: " + dto.getActividadId()));

        Energia energia = modelMapper.map(dto, Energia.class);
        energia.setActividad(actividad);

        Energia guardada = energiaRepo.save(energia);
        return modelMapper.map(guardada, EnergiaDTO.class);
    }

    @Override
    public List<EnergiaDTO> listarPorActividad(Long actividadId) {
        return energiaRepo.findByActividadId(actividadId)
                .stream()
                .map(energia -> modelMapper.map(energia, EnergiaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long energiaId) {
        if (!energiaRepo.existsById(energiaId)) {
            throw new NoSuchElementException("Energia no encontrada con ID: " + energiaId);
        }
        energiaRepo.deleteById(energiaId);
    }
}