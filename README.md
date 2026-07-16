# EdTeam

Marketplace universitario: Flutter + Firebase Auth + API Spring Boot / MySQL.

| Carpeta | Rol |
|---------|-----|
| [`flutter_application_1/`](flutter_application_1/) | App cliente (Firebase Auth + HTTP) |
| [`backend/`](backend/) | API REST, Flyway, MySQL `Apputp` |

Arranque rápido:

1. Configura MySQL y `backend` → ver [`backend/README.md`](backend/README.md)
2. `mvn spring-boot:run -Dspring-boot.run.profiles=local` en `backend/`
3. `flutter run` en `flutter_application_1/`
