package com.salud.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Prueba de integración: necesita una base de datos PostgreSQL en ejecución, por eso se omite en el build local.
@Disabled("Requiere PostgreSQL en ejecución (bd_terapia en localhost)")
@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
