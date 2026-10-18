package com.salud.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

/**
 * Configuración web centralizada. El CORS se define una sola vez aquí
 * (antes estaba repetido con @CrossOrigin(origins = "*") en cada controlador)
 * y los orígenes se parametrizan por entorno con app.cors.allowed-origins.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String[] origenesPermitidos;
    private final String uploadsLocation;


    public WebConfig(
            @Value("${app.cors.allowed-origins:http://localhost:4200,https://sistema-salud-fisica.vercel.app}")
            String[] origenesPermitidos,
            @Value("${app.uploads.location:./uploads/}")
            String uploadsLocation) {

        this.origenesPermitidos = origenesPermitidos;
        this.uploadsLocation = uploadsLocation;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(origenesPermitidos)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String location = Path.of(uploadsLocation)
            .toAbsolutePath()
            .normalize()
            .toUri()
            .toString();

        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler("/uploads/**")
            .addResourceLocations(location);
    }
}
