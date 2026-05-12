package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.NotificacionDTO;
import com.upc.purevibeb.interfaces.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class NotificacionController {

    @Autowired
    private INotificacionService service;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Page<NotificacionDTO>> getNotificaciones(
            @RequestParam Long usuarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(service.getNotificaciones(usuarioId, page, size));
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Map<String, Long>> getResumen(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(service.getResumen(usuarioId));
    }

    @PostMapping("/marcar-leidas")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<Void> marcarLeidas(@RequestParam Long usuarioId, @RequestBody List<Long> ids) {
        service.marcarLeidas(ids, usuarioId);
        return ResponseEntity.ok().build();
    }
}