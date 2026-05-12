package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.RecomendacionDTO;
import com.upc.purevibeb.entities.Familia;
import com.upc.purevibeb.entities.Institucion;
import com.upc.purevibeb.security.entities.User;
import com.upc.purevibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecomendacionService {

    @Autowired
    private UserRepository userRepo;

    @Autowired private FamiliaService familiaService;
    @Autowired private InstitucionService institucionService;

    @Transactional(readOnly = true)
    public List<RecomendacionDTO> generarRecomendaciones(Long usuarioId) {
        User user = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        List<RecomendacionDTO> recomendaciones = new ArrayList<>();

        if (user.getInstitucion() != null) {
            return generarParaInstitucion(user.getInstitucion());
        } else if (user.getFamilia() != null) {
            return generarParaFamilia(user.getFamilia(), user.getId());
        } else {
            return generarParaUsuario(user);
        }
    }

    private List<RecomendacionDTO> generarParaUsuario(User user) {
        List<RecomendacionDTO> recs = new ArrayList<>();

        if (user.getHuellaTotalKgCO2e() == null) {
            recs.add(new RecomendacionDTO("General", "Calcula tu huella para recibir consejos.", "fa-calculator", "ALTO"));
            return recs;
        }

        if (checkAlto(user.getHuellaTransporte(), 1000)) {
            recs.add(new RecomendacionDTO("Transporte", "Tu huella de transporte es alta. Intenta usar bicicleta o transporte público.", "fa-bicycle", "ALTO"));
        }

        return recs;
    }

    private List<RecomendacionDTO> generarParaFamilia(Familia familia, Long userId) {
        List<RecomendacionDTO> recs = new ArrayList<>();

        // Obtenemos el dashboard para tener los totales
        var dashboard = familiaService.getDashboardFamiliar(userId);
        BigDecimal totalFamilia = dashboard.getHuellaTotalFamiliaKg();

        if (totalFamilia.compareTo(BigDecimal.ZERO) == 0) {
            recs.add(new RecomendacionDTO("Familia", "Nadie en la familia ha calculado su huella aún.", "fa-users", "ALTO"));
            return recs;
        }

        recs.add(new RecomendacionDTO("Hogar", "Organicen un día de 'Cero Desperdicio' en familia.", "fa-home", "MEDIO"));

        if (totalFamilia.compareTo(new BigDecimal("5000")) > 0) {
            recs.add(new RecomendacionDTO("Energía Familiar", "El consumo total es alto. Revisen el aislamiento térmico de la casa.", "fa-temperature-low", "ALTO"));
        }

        return recs;
    }

    private List<RecomendacionDTO> generarParaInstitucion(Institucion institucion) {
        List<RecomendacionDTO> recs = new ArrayList<>();

        recs.add(new RecomendacionDTO("Comunidad", "Incentiva el carpooling entre los miembros de " + institucion.getTipo().toLowerCase() + ".", "fa-car-side", "ALTO"));
        recs.add(new RecomendacionDTO("Infraestructura", "Instalar sensores de movimiento para las luces puede reducir un 30% el consumo.", "fa-lightbulb", "MEDIO"));
        recs.add(new RecomendacionDTO("Residuos", "Implementen puntos de reciclaje visibles en todas las áreas comunes.", "fa-recycle", "BAJO"));

        return recs;
    }

    private boolean checkAlto(BigDecimal valor, int umbral) {
        return valor != null && valor.compareTo(new BigDecimal(umbral)) > 0;
    }
}