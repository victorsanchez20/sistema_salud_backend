package com.salud.demo.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * El reloj se inyecta como dependencia para que los servicios no llamen a
 * LocalDate.now() directamente y las pruebas puedan fijar la fecha (IoC / DIP).
 */
@Configuration
public class TimeConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
