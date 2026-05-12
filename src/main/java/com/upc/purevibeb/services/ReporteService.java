package com.upc.purevibeb.services;

import com.upc.purevibeb.dtos.FactoresEmisionDTO;
import com.upc.purevibeb.dtos.ReporteDTO;
import com.upc.purevibeb.interfaces.IFactoresEmisionService;
import com.upc.purevibeb.interfaces.IReporteService;
import com.upc.purevibeb.repositories.EnergiaRepository;
import com.upc.purevibeb.repositories.ResiduoRepository;
import com.upc.purevibeb.repositories.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReporteService implements IReporteService {

    @Autowired private TransporteRepository transporteRepo;
    @Autowired private EnergiaRepository energiaRepo;
    @Autowired private ResiduoRepository residuoRepo;
    @Autowired private IFactoresEmisionService factoresService;

    private static final BigDecimal CERO = BigDecimal.ZERO;

    @Override
    public ReporteDTO calcularReporte(Long actividadId) {

        BigDecimal totalTransporte = calcularTransporte(actividadId);
        BigDecimal totalEnergia = calcularEnergia(actividadId);
        BigDecimal totalResiduos = calcularResiduos(actividadId);

        BigDecimal totalGeneral = totalTransporte.add(totalEnergia).add(totalResiduos);

        return ReporteDTO.builder()
                .actividadId(actividadId)
                .transporteKgCO2e(totalTransporte)
                .energiaKgCO2e(totalEnergia)
                .residuosKgCO2e(totalResiduos)
                .totalKgCO2e(totalGeneral)
                .build();
    }

    private BigDecimal calcularTransporte(Long actividadId) {
        List<Object[]> transportes = transporteRepo.findTransporteAgrupadoPorActividad(actividadId);
        BigDecimal total = CERO;

        for (Object[] fila : transportes) {
            String medio = (String) fila[0];
            BigDecimal distancia = (BigDecimal) fila[1];
            BigDecimal kmAvion = (BigDecimal) fila[2];

            if (medio.equals("avion") && kmAvion != null) {
                total = total.add(kmAvion.multiply(getFactor("transporte", "avion", "km")));
            } else if (distancia != null) {
                total = total.add(distancia.multiply(getFactor("transporte", medio, "km")));
            }
        }
        return total;
    }

    private BigDecimal calcularEnergia(Long actividadId) {
        List<Object[]> energias = energiaRepo.findEnergiaAgrupadaPorActividad(actividadId);
        BigDecimal total = CERO;

        for (Object[] fila : energias) {
            String tipo = (String) fila[0];
            String unidad = (String) fila[1];
            BigDecimal consumo = (BigDecimal) fila[2];

            if (consumo != null) {
                // Para "dispositivos", usamos la 'unidad' (ej. "2to4") como subcategoría
                if (tipo.equals("dispositivos")) {
                    total = total.add(consumo.multiply(getFactor("energia", unidad, "dia"))); // Asumiendo factor "por dia"
                } else {
                    total = total.add(consumo.multiply(getFactor("energia", tipo, unidad)));
                }
            }
        }
        return total;
    }

    private BigDecimal calcularResiduos(Long actividadId) {
        List<Object[]> residuos = residuoRepo.findResiduosAgrupadosPorActividad(actividadId);
        BigDecimal total = CERO;

        for (Object[] fila : residuos) {
            Boolean reciclaje = (Boolean) fila[0];
            BigDecimal pesoKg = (BigDecimal) fila[1];

            if (pesoKg != null) {
                // (Lógica simple: si no recicla, suma la huella. Si recicla, la huella es 0)
                if (reciclaje != null && !reciclaje) {
                    total = total.add(pesoKg.multiply(getFactor("residuos", "general", "kg")));
                }
            }
        }
        return total;
    }

    private BigDecimal getFactor(String categoria, String subcategoria, String unidad) {
        return factoresService.buscarVigente(categoria, subcategoria, unidad)
                .map(FactoresEmisionDTO::getFactorKgco2ePerUnidad)
                .orElse(CERO); // Si no encuentra un factor, multiplica por 0
    }
}