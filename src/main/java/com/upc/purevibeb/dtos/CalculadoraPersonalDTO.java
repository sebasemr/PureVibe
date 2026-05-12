package com.upc.purevibeb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CalculadoraPersonalDTO {

    private Long usuarioId;

    // --- Entradas ---
    private BigDecimal horasBusSemana;
    private BigDecimal horasTrenSemana;
    private BigDecimal horasMetropolitanoSemana;
    private BigDecimal horasAutoSemana;
    private BigDecimal kwhMes;
    private Integer balonesGlp10kgMes;
    private Integer diasCarnePorSemana;
    private Integer bolsas5L;
    private Integer bolsas10L;
    private Integer bolsas20L;
    private List<String> tiposReciclaje;

    @JsonProperty("totalTransporteTon")
    private BigDecimal totalTransporteTon;

    @JsonProperty("totalEnergiaTon")
    private BigDecimal totalEnergiaTon;

    @JsonProperty("totalAlimentacionTon")
    private BigDecimal totalAlimentacionTon;

    @JsonProperty("totalResiduosTon")
    private BigDecimal totalResiduosTon;

    @JsonProperty("totalKgCO2e")
    private BigDecimal totalKgCO2e;
}