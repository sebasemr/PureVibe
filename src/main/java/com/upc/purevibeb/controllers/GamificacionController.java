package com.upc.purevibeb.controllers;

import com.upc.purevibeb.dtos.CanjearRequest;
import com.upc.purevibeb.dtos.EstadoGamificacionDTO;
import com.upc.purevibeb.dtos.RecompensaDTO;
import com.upc.purevibeb.interfaces.IGamificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class GamificacionController {

    public record OtorgarPorActividadRequest(Long actividadId, Long usuarioId) {}

    @Autowired
    private IGamificacionService gamificacionService;

    @GetMapping("/recompensas")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<List<RecompensaDTO>> listarRecompensas() {
        return ResponseEntity.ok(gamificacionService.listarRecompensas());
    }

    @GetMapping("/estado/{usuarioId}")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<EstadoGamificacionDTO> getEstado(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(gamificacionService.getEstadoUsuario(usuarioId));
    }

    @PostMapping("/canjear")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<EstadoGamificacionDTO> canjear(@RequestBody CanjearRequest request) {
        try {
            EstadoGamificacionDTO nuevoEstado = gamificacionService.canjearRecompensa(request);
            return ResponseEntity.ok(nuevoEstado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/otorgarPorActividad")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<EstadoGamificacionDTO> otorgarPorActividad(@RequestBody OtorgarPorActividadRequest request) {
        try {
            EstadoGamificacionDTO nuevoEstado = gamificacionService.analizarYOtorgarPuntos(
                    request.actividadId(),
                    request.usuarioId()
            );
            return ResponseEntity.ok(nuevoEstado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}