# Changelog

Formato basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/). Las versiones se etiquetarán al publicar cada release; mientras tanto los cambios se acumulan en «Sin publicar».

## [Sin publicar]
- RFC-01: CORS centralizado en `WebConfig`, credenciales de base de datos por variables de entorno y credencial retirada del comentario de `application-dev.properties`. Excepción de dominio común, `GlobalExceptionHandler` y `CrudService` base.
- RFC-02: el registro de citas, las horas disponibles, la autenticación y las estadísticas pasan de controladores y servicios largos a servicios con una sola responsabilidad.
- RFC-03: `EstadoSesion`, `RangoEdad` y `PacienteCredentialPolicy` reemplazan números sueltos y condiciones encadenadas.
- RFC-04: `Clock` inyectable y sin `@Autowired` por campo; los controladores ya no usan repositorios.
- RFC-05: el perfil `prod` desactiva `spring.jpa.show-sql`; se eliminó código comentado y obsoleto para mejorar la legibilidad y el mantenimiento..
- RFC-06: se incorporó integración continua con GitHub Actions para ejecutar las verificaciones de Maven en cada push y Pull Request.
- RFC-07: se añadió `@Transactional` a `guardarMasivo()` para evitar guardados parciales.