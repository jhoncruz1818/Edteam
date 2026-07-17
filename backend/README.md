# EdTeam Backend

API REST en Spring Boot 3 con **Microsoft SQL Server** (`Apputp`) y migraciones **Flyway**.
Flutter sigue usando **Firebase Auth**; clientes y productos viven en SQL Server (SSMS).

## Requisitos

- Java 21+
- Maven 3.9+
- SQL Server con la base de datos `Apputp` creada (puede estar vacía)
- SQL Server Management Studio (opcional, para ver tablas)

## Configuración

1. En SSMS, crea la base si aún no existe:

```sql
CREATE DATABASE Apputp;
```

2. El proyecto usa el login SQL `edteam_app` (ya creado en tu máquina local).
   Credenciales en `application-local.yml`:

```yaml
spring:
  datasource:
    username: edteam_app
    password: EdteamLocal123!
```

3. Arranca:

```bash
cd backend
mvn spring-boot:run
```

Al arrancar, Flyway aplica:

- `V1__create_clientes.sql`
- `V2__create_productos.sql`

### Si el login no existe

En SSMS (Autenticación de Windows), ejecuta:

```sql
CREATE LOGIN edteam_app WITH PASSWORD = 'EdteamLocal123!', CHECK_POLICY = OFF;
USE Apputp;
CREATE USER edteam_app FOR LOGIN edteam_app;
ALTER ROLE db_owner ADD MEMBER edteam_app;
```

## Firebase (tokens)

Por defecto `app.firebase.verify-tokens: false` (modo local): el backend lee el UID del JWT de Firebase **sin verificar la firma**.

Para producción:

1. En Firebase Console → Project settings → Service accounts → Generate new private key
2. Guarda el JSON fuera del repo
3. En `application-local.yml`:

```yaml
app:
  firebase:
    credentials-path: C:/ruta/apputp-firebase-adminsdk.json
    verify-tokens: true
```

## Endpoints

Todos requieren `Authorization: Bearer <Firebase ID token>`.

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/clientes` | Crear perfil tras registro |
| GET | `/api/clientes/me` | Perfil del usuario autenticado |
| GET | `/api/clientes/{firebaseUid}` | Perfil por UID (contacto WhatsApp) |
| GET | `/api/productos` | Listado |
| GET | `/api/productos/{id}` | Detalle |
| POST | `/api/productos` | Publicar |

## Puerto

`http://localhost:8080`
