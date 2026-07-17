# EdTeam

Marketplace universitario: Flutter + Firebase Auth + API Spring Boot / SQL Server.

| Carpeta | Rol |
|---------|-----|
| [`flutter_application_1/`](flutter_application_1/) | App cliente (Firebase Auth + HTTP) |
| [`backend/`](backend/) | API REST, Flyway, SQL Server `Apputp` |

Arranque rápido:

1. Configura SQL Server y `backend` → ver [`backend/README.md`](backend/README.md)
2. `mvn spring-boot:run "-Dspring-boot.run.profiles=local"` en `backend/`
3. `flutter run` en `flutter_application_1/`
