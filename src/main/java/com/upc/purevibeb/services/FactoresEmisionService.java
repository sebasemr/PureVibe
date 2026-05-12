package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.FactoresEmisionDTO;
import com.upc.purevibeb.entities.FactoresEmision;
import com.upc.purevibeb.interfaces.IFactoresEmisionService;
import com.upc.purevibeb.repositories.FactoresEmisionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FactoresEmisionService implements IFactoresEmisionService {

    @Autowired
    private FactoresEmisionRepository factoresRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<FactoresEmisionDTO> buscarVigente(String categoria, String subcategoria, String unidadBase) {
        return factoresRepo.findFirstByCategoriaIgnoreCaseAndSubcategoriaIgnoreCaseAndUnidadBaseIgnoreCaseAndVigenteTrue(
                        categoria, subcategoria, unidadBase)
                .map(factor -> modelMapper.map(factor, FactoresEmisionDTO.class));
    }


    @Override
    public FactoresEmisionDTO crear(FactoresEmisionDTO dto) {
        FactoresEmision entity = modelMapper.map(dto, FactoresEmision.class);
        FactoresEmision saved = factoresRepo.save(entity);
        return modelMapper.map(saved, FactoresEmisionDTO.class);
    }

    @Override
    public FactoresEmisionDTO actualizar(Long id, FactoresEmisionDTO dto) {
        factoresRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Factor no encontrado con ID: " + id));

        FactoresEmision entity = modelMapper.map(dto, FactoresEmision.class);
        entity.setId(id); // Asegura que se actualice el ID correcto

        FactoresEmision saved = factoresRepo.save(entity);
        return modelMapper.map(saved, FactoresEmisionDTO.class);
    }

    @Override
    public Optional<FactoresEmisionDTO> obtener(Long id) {
        return factoresRepo.findById(id)
                .map(factor -> modelMapper.map(factor, FactoresEmisionDTO.class));
    }

    @Override
    public List<FactoresEmisionDTO> listar() {
        return factoresRepo.findAll()
                .stream()
                .map(factor -> modelMapper.map(factor, FactoresEmisionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (!factoresRepo.existsById(id)) {
            throw new NoSuchElementException("Factor no encontrado con ID: " + id);
        }
        factoresRepo.deleteById(id);
    }
}