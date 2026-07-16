# EdTeam Backend

API REST en Spring Boot 3 con **MySQL** (`Apputp`) y migraciones **Flyway**.
Flutter sigue usando **Firebase Auth**; clientes y productos viven en MySQL.

## Requisitos

- Java 21+
- Maven 3.9+
- MySQL con la base de datos `Apputp` creada (puede estar vacía)

## Configuración

1. Copia el ejemplo de credenciales locales:

```bash
copy src\main\resources\application-local.yml.example src\main\resources\application-local.yml
```

2. Edita `application-local.yml` con tu usuario/contraseña de MySQL.

3. Arranca con el perfil `local`:

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Sin archivo local, puedes usar variables de entorno:

```bash
set DB_USER=root
set DB_PASSWORD=tu_password
mvn spring-boot:run
```

Al arrancar, Flyway aplica:

- `V1__create_clientes.sql`
- `V2__create_productos.sql`

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
