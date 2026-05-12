package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.*;
import com.upc.purevibeb.entities.HistorialPuntos;
import com.upc.purevibeb.interfaces.IGamificacionService;
import com.upc.purevibeb.interfaces.INotificacionService;
import com.upc.purevibeb.interfaces.IReporteService;
import com.upc.purevibeb.repositories.HistorialPuntosRepository;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class GamificacionService implements IGamificacionService {

    @Autowired
    private HistorialPuntosRepository historialRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private IReporteService reporteService;

    private static final List<RecompensaDTO> RECOMPENSAS_DISPONIBLES = List.of(
            new RecompensaDTO(1, "Cupón 10% tienda eco", "Descuento en productos sostenibles", 50),
            new RecompensaDTO(2, "Botella reutilizable", "Acero inoxidable 500ml", 120),
            new RecompensaDTO(3, "Pack bolsas de tela", "3 bolsas reusables para compras", 80),
            new RecompensaDTO(4, "Semillas de árbol nativo", "Planta un árbol en tu comunidad", 150),
            new RecompensaDTO(5, "Kit de cubiertos de bambú", "Set de viaje con estuche de tela", 100),
            new RecompensaDTO(6, "Planta de interior", "Planta purificadora de aire (mediana)", 200)
    );

    private static final Map<String, Integer> ACCIONES_PUNTOS = Map.of(
            "REGISTRO_DIARIO", 5,
            "RECICLAJE", 10,
            "TRANSPORTE_SOSTENIBLE", 15
    );

    @Override
    public List<RecompensaDTO> listarRecompensas() {
        return RECOMPENSAS_DISPONIBLES;
    }

    @Override
    public EstadoGamificacionDTO getEstadoUsuario(Long usuarioId) {
        int saldo = historialRepo.getSaldoPuntos(usuarioId);
        return EstadoGamificacionDTO.builder()
                .puntosTotales(saldo)
                .puntosGanados(0)
                .mensaje("Saldo actualizado")
                .build();
    }

    @Override
    @Transactional
    public EstadoGamificacionDTO analizarYOtorgarPuntos(Long actividadId, Long usuarioId) {
        ReporteDTO reporte = reporteService.calcularReporte(actividadId);

        // Obtenemos el User completo (necesario para guardar la relación)
        User user = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        int puntosGanadosHoy = 0;

        // Pasamos el objeto 'user' en lugar del ID
        puntosGanadosHoy += otorgarPuntosInternal(user, "REGISTRO_DIARIO", "Registro de actividad diaria");

        if (reporte.getResiduosKgCO2e().compareTo(BigDecimal.ZERO) == 0) {
            puntosGanadosHoy += otorgarPuntosInternal(user, "RECICLAJE", "¡Buen trabajo reciclando!");
        }
        if (reporte.getTransporteKgCO2e().compareTo(new BigDecimal("0.5")) < 0) {
            puntosGanadosHoy += otorgarPuntosInternal(user, "TRANSPORTE_SOSTENIBLE", "Transporte de bajo impacto");
        }

        int saldoTotal = historialRepo.getSaldoPuntos(usuarioId);

        if (puntosGanadosHoy > 0) {
            notificacionService.crearNotificacion(user,
                    "¡Ganaste " + puntosGanadosHoy + " puntos por tu registro diario!",
                    "/tienda");
        }

        return EstadoGamificacionDTO.builder()
                .puntosTotales(saldoTotal)
                .puntosGanados(puntosGanadosHoy)
                .mensaje("¡Puntos otorgados por tu registro!")
                .build();
    }

    private int otorgarPuntosInternal(User usuario, String codigo, String detalle) {
        Integer puntos = ACCIONES_PUNTOS.get(codigo);
        if (puntos == null || puntos <= 0) return 0;

        HistorialPuntos ganancia = new HistorialPuntos();

        // Usamos setUsuario(Objeto) en lugar de setUsuarioId(Long)
        ganancia.setUsuario(usuario);
        ganancia.setCodigoAccion(codigo);
        ganancia.setPuntos(puntos);
        ganancia.setDetalle(detalle);
        historialRepo.save(ganancia);

        return puntos;
    }

    @Override
    @Transactional
    public EstadoGamificacionDTO canjearRecompensa(CanjearRequest request) {
        User user = userRepo.findById(request.getUsuarioId())
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        RecompensaDTO recompensa = RECOMPENSAS_DISPONIBLES.stream()
                .filter(r -> r.getId().equals(request.getRecompensaId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Recompensa no encontrada"));

        int saldoActual = historialRepo.getSaldoPuntos(request.getUsuarioId());
        if (saldoActual < recompensa.getCostoPuntos()) {
            throw new IllegalStateException("Puntos insuficientes");
        }

        HistorialPuntos gasto = new HistorialPuntos();

        // Usamos setUsuario(Objeto)
        gasto.setUsuario(user);

        gasto.setCodigoAccion("CANJE_" + recompensa.getId());
        gasto.setPuntos(-recompensa.getCostoPuntos());
        gasto.setDetalle("Canje: " + recompensa.getNombre());
        historialRepo.save(gasto);

        notificacionService.crearNotificacion(user,
                "Canjeaste \"" + recompensa.getNombre() + "\" exitosamente.",
                null);

        // Calculamos el saldo manualmente para devolverlo rápido
        int nuevoSaldo = saldoActual - recompensa.getCostoPuntos();
        return EstadoGamificacionDTO.builder()
                .puntosTotales(nuevoSaldo)
                .puntosGanados(-recompensa.getCostoPuntos())
                .mensaje("¡Canje exitoso!")
                .build();
    }
}