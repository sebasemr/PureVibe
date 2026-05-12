package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.CalculadoraPersonalDTO;

public interface ICalculadoraService {
    CalculadoraPersonalDTO calcularHuellaPersonal(CalculadoraPersonalDTO request);
}