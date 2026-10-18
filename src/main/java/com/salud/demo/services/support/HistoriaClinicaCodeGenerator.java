package com.salud.demo.services.support;

import org.springframework.stereotype.Component;

@Component 
public class HistoriaClinicaCodeGenerator {

    public String generar(Long pacienteId) {
        return String.format("HC%06d", pacienteId);
    }
}
