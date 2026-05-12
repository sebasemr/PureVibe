package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.ResiduoDTO;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.entities.Residuo;
import com.upc.purevibeb.interfaces.IResiduoService;
import com.upc.purevibeb.repositories.ActividadesDiariasRepository;
import com.upc.purevibeb.repositories.ResiduoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ResiduoService implements IResiduoService {

    @Autowired
    private ResiduoRepository residuoRepo;

    @Autowired
    private ActividadesDiariasRepository actividadesRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResiduoDTO crear(ResiduoDTO dto) {
        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad no encontrada con ID: " + dto.getActividadId()));

        Residuo residuo = modelMapper.map(dto, Residuo.class);
        residuo.setActividad(actividad);

        Residuo guardado = residuoRepo.save(residuo);
        return modelMapper.map(guardado, ResiduoDTO.class);
    }

    @Override
    public List<ResiduoDTO> listarPorActividad(Long actividadId) {
        return residuoRepo.findByActividadId(actividadId)
                .stream()
                .map(residuo -> modelMapper.map(residuo, ResiduoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long residuoId) {
        if (!residuoRepo.existsById(residuoId)) {
            throw new NoSuchElementException("Residuo no encontrado con ID: " + residuoId);
        }
        residuoRepo.deleteById(residuoId);
    }
}