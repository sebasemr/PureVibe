package com.upc.purevibeb.config;

import com.upc.purevibeb.dtos.ActividadesDiariasDTO;
import com.upc.purevibeb.dtos.EnergiaDTO;
import com.upc.purevibeb.dtos.ResiduoDTO;
import com.upc.purevibeb.dtos.TransporteDTO;
import com.upc.purevibeb.entities.ActividadesDiarias;
import com.upc.purevibeb.entities.Energia;
import com.upc.purevibeb.entities.Residuo;
import com.upc.purevibeb.entities.Transporte;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper m = new ModelMapper();
        m.getConfiguration().setSkipNullEnabled(true);


        m.createTypeMap(ActividadesDiarias.class, ActividadesDiariasDTO.class)
                .addMappings(mp -> mp.map(src -> src.getUsuario().getId(), ActividadesDiariasDTO::setUsuarioId));

        m.createTypeMap(Transporte.class, TransporteDTO.class)
                .addMappings(mp -> mp.map(src -> src.getActividad().getId(), TransporteDTO::setActividadId));

        m.createTypeMap(Energia.class, EnergiaDTO.class)
                .addMappings(mp -> mp.map(src -> src.getActividad().getId(), EnergiaDTO::setActividadId));

        m.createTypeMap(Residuo.class, ResiduoDTO.class)
                .addMappings(mp -> mp.map(src -> src.getActividad().getId(), ResiduoDTO::setActividadId));

        return m;
    }
}