package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.NotificacionDTO;
import com.upc.purevibeb.security.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface INotificacionService {
    void crearNotificacion(User usuario, String mensaje, String linkRuta);
    Page<NotificacionDTO> getNotificaciones(Long usuarioId, int page, int size);
    Map<String, Long> getResumen(Long usuarioId);
    void marcarLeidas(List<Long> notificacionIds, Long usuarioId);
}