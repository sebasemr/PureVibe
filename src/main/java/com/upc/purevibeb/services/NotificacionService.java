package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.NotificacionDTO;
import com.upc.purevibeb.entities.Notificacion;
import com.upc.purevibeb.interfaces.INotificacionService;
import com.upc.purevibeb.repositories.NotificacionRepository;
import com.upc.purevibeb.security.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class NotificacionService implements INotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepo;
    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public void crearNotificacion(User usuario, String mensaje, String linkRuta) {
        Notificacion n = new Notificacion();
        n.setUsuario(usuario);
        n.setMensaje(mensaje);
        n.setLinkRuta(linkRuta);
        n.setLeido(false);
        n.setFechaCreacion(LocalDateTime.now());
        notificacionRepo.save(n);
    }

    @Override
    public Page<NotificacionDTO> getNotificaciones(Long usuarioId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificacionRepo.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId, pageable)
                .map(n -> mapper.map(n, NotificacionDTO.class));
    }

    @Override
    public Map<String, Long> getResumen(Long usuarioId) {
        long unreadCount = notificacionRepo.countByUsuarioIdAndLeidoFalse(usuarioId);
        return Map.of("unreadCount", unreadCount);
    }

    @Override
    @Transactional
    public void marcarLeidas(List<Long> notificacionIds, Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepo.findByIdsAndUsuarioId(notificacionIds, usuarioId);
        for (Notificacion n : notificaciones) {
            n.setLeido(true);
        }
        notificacionRepo.saveAll(notificaciones);
    }
}