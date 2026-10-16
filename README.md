# Sistema Salud – Backend (Spring Boot)

API REST del Sistema de Gestión para Terapia Física y Rehabilitación.

## Arquitectura por capas

```
controller  →  services  →  repositories  →  PostgreSQL
   (HTTP)     (reglas)       (JPA)
                 ↑
        domain (enums) · services/auth (estrategias) · exception · config
```

* **controller**: solo traduce HTTP a llamadas de servicio (sin repositorios).
* **services**: una responsabilidad por clase (`CitaService`, `PacienteAuthService`, `PacienteEstadisticasService`, …).
* **services/support/CrudService**: CRUD común para los servicios de catálogo.
* **domain**: `EstadoSesion`, `RangoEdad` (valores de negocio extensibles).
* **exception**: `RecursoNoEncontradoException` + `GlobalExceptionHandler` (404).
* **config**: CORS (`WebConfig`), `PasswordEncoder`, `Clock` inyectable.

## Configuración por entorno

| Variable | Perfil | Descripción | Valor por defecto |
|---|---|---|---|
| `SPRING_PROFILES_ACTIVE` | todos | Perfil activo | `dev` |
| `POSTGRES_URL` | prod (opcional en dev) | URL JDBC | `jdbc:postgresql://localhost:5432/bd_terapia` (dev) |
| `POSTGRES_USER` | prod (opcional en dev) | Usuario | `postgres` (dev) |
| `POSTGRES_PASSWORD` | prod (opcional en dev) | Contraseña | `postgres` (dev) |
| `APP_CORS_ALLOWED_ORIGINS` | todos | Orígenes permitidos, separados por coma | `http://localhost:4200` (dev) / `*` (prod) |

> No escribir credenciales en los archivos `.properties`; usar variables de entorno.

## Ejecutar

```bash
./mvnw spring-boot:run          # perfil dev
./mvnw test                     # pruebas unitarias
```

## Estrategia de ramas (GitFlow simplificado)

`main` (producción, solo con tags `vX.Y.Z`) · `develop` (integración) · `feature/RFC-xx-*` · `release/x.y.z` · `hotfix/RFC-xx-*`.
Mensajes de commit: `tipo(ámbito): resumen` (convencional) y referencia al RFC.
