package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.CanjearRequest;
import com.upc.purevibeb.dtos.EstadoGamificacionDTO;
import com.upc.purevibeb.dtos.RecompensaDTO;

import java.util.List;

public interface IGamificacionService {

    List<RecompensaDTO> listarRecompensas();

    EstadoGamificacionDTO getEstadoUsuario(Long usuarioId);

    EstadoGamificacionDTO canjearRecompensa(CanjearRequest request);

    EstadoGamificacionDTO analizarYOtorgarPuntos(Long actividadId, Long usuarioId);
}