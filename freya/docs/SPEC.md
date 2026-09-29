# Freya BE — Spec (Regularidad)

> Alcance: solo Backend (`freya-java-BE`). Documenta lo que existe hoy, sin proponer cambios.
> Idioma: español. Archivo único.
> Central: https://github.com/phalanxeyes/freya-java
> FE: https://github.com/phalanxeyes/freya-java-FE — Spec FE funcional ya definida (Home, `SongView`, `CoverView`, Auth, subir cover pendiente, `/admin`, `NotFound`, `Service Down`).
> BE: https://github.com/phalanxeyes/freya-java-BE

## 1. Visión

Freya es una plataforma social donde usuarios comparten y escuchan covers de canciones. El BE expone API REST para catálogo público de canciones/covers aprobados, alta de covers (quedan pendientes) y administración.

## 2. Alcance Regularidad

### Incluye
- ABMC Simple: Usuario, Canción.
- ABMC Dependiente: Cover (depende de `Song` + `User` uploader).
- CU no-ABMC: Subir y aprobar cover (flujo simple).
- Listado Simple: Listado de covers por cada canción (filtrado actual: en cliente FE; BE expone listados por estado).
- Niveles de acceso actuales: No-Logged, Logged, Admin (sin rol en DB, ver §3).
- Manejo de errores a nivel API (nivel actual, ver §10).

### No incluye (fuera de scope, registrado como futuro)
- Comentarios (FE `CoverComments` es stub local; no hay entidad).
- Playlists y guardar/favoritos.
- Artista como entidad (hoy `artist: string` en `Album`).
- Rol Moderador (solo No-Logged, Logged, Admin por ahora).
- Listado complejo de comentarios por usuario con filtros de fecha (requerimiento AD, no especificado aquí).
- Upload de audio propio (hoy solo `youtubeUrl`, a futuro audio hosteado).

## 3. Roles

No hay campo `rol` en DB. `freya/src/main/java/com/salest/freya/entities/User.java:16-32` solo tiene `id, username, hashedPass, email, createdAt, updatedAt`. `CustomUserDetailsService` devuelve `authorities` vacías. `WebSecurityConfig` solo distingue `permitAll` vs `authenticated()` con `TODO` a `hasRole("ADMIN")` en `freya/src/main/java/com/salest/freya/security/WebSecurityConfig.java:82-83`.

Convención actual para la spec:

| Rol | Descripción | Acceso BE |
|---|---|---|
| No-Logged | Sin JWT | Catálogo público GET + `/api/auth/**` + health/test público + Swagger |
| Logged | Con JWT Bearer | Todo lo anterior + escrituras songs/covers + `/api/users/**` + listados `/all` |
| Admin | Logged con privilegio operativo (sin enforcement en código) | Opera ABMC canciones + `approve/reject` covers. Enforcement pendiente (hoy cualquier logged puede). |
| Moderador | Futuro, no especificar | — |

## 4. Modelo de datos

Todas las entidades: `id: Integer IDENTITY`, `createdAt/updatedAt: LocalDateTime` con `@CreatedDate/@LastModifiedDate` + `AuditingEntityListener`, JSON `yyyy-MM-dd'T'HH:mm:ss.SSS'Z'`. Habilitado por `@EnableJpaAuditing` en `FreyaApplication.java`.

- `User` (`users`) — `freya/src/main/java/com/salest/freya/entities/User.java:18-32`: `id, username, hashedPass, email, createdAt, updatedAt`. Sin relaciones.
- `Album` (`albums`) — `freya/src/main/java/com/salest/freya/entities/Album.java:17-31`: `id, name, artist: string, coverUrl, createdAt, updatedAt`. Sin controller (entidad/repo/mapper/DTO existen, no expuesta).
- `Song` (`songs`) — `freya/src/main/java/com/salest/freya/entities/Song.java:18-37`: `id, duration: float, name, lyrics, album: ManyToOne(album_id), createdAt, updatedAt`.
- `Cover` (`covers`) — `freya/src/main/java/com/salest/freya/entities/Cover.java:17-51`: `id, duration: Integer, playCount: Integer, youtubeUrl: string (nullable en seed), uploader: ManyToOne(uploader_user_id)->User, song: ManyToOne(song_id)->Song, status: enum STRING {PENDING, APPROVED, REJECTED, HIDDEN}, createdAt, updatedAt`.

DER de referencia (general): `freyaDER.png` + Draw.io en repo central. La spec BE congela solo estas 4 tablas y relaciones `covers.song_id->songs`, `covers.uploader_user_id->users`, `songs.album_id->albums`.

## 5. Stack y configuración (solo documentar)

- `freya/pom.xml:5-21`: Java 21, Spring Boot `4.1.0`, `com.salest:freya:0.0.1-SNAPSHOT`, MapStruct `1.6.3`, `springdoc-openapi 2.8.5`, `jjwt 0.13.0`, `typescript-generator 3.2.1263` (perfil `generate-ts-types`, salida `generated-ts/api.d.ts`, patrón `com.salest.freya.dtos.**`).
- `freya/src/main/resources/application.properties:1-14`: `jdbc:mysql://localhost:3306/freya` (`root/root`), puerto default `8080`, `ddl-auto=create-drop + sql.init.mode=always + defer-datasource-initialization=true + show-sql=true` (borra y resiembra con `data.sql` en cada arranque), `jwt.expiration=86400000` (24h).
- CORS: `http://localhost:5173`, `GET,POST,PUT,DELETE,OPTIONS`, `allowCredentials=true` (`WebSecurityConfig.java:45-54`).
- Swagger UI: `/swagger-ui/**`, `/v3/api-docs/**` públicos.
- Patrón de capas: `Controller -> Service -> Mapper (MapStruct) -> Repository (JpaRepository<Integer>) / Entity`, DTOs como `record`.

## 6. Auth (contrato BE actual)

`freya/src/main/java/com/salest/freya/controllers/AuthController.java:13-47`:

- `POST /api/auth/signup` body `User{username, hashedPass}` (usa entidad, no DTO). Si `existsByUsername` → `200` con texto `"Error: Username is already taken!"`. Si no, guarda con `BCryptPasswordEncoder` y devuelve `"User registered successfully!"`. Nota: no valida email ni password rules; `email` se ignora en este endpoint.
- `POST /api/auth/signin` body `User{username, hashedPass}` → `AuthenticationManager + CustomUserDetailsService + BCrypt` → devuelve JWT `String` plano (subject=`username`, HMAC-SHA, 24h). Sin refresh/logout.
- Uso: header `Authorization: Bearer <jwt>` vía `AuthTokenFilter` (stateless, CSRF off).

Gap conocido vs FE: la spec FE menciona `POST auth/login, POST auth/register` con `email+password`. No se congela en BE: vale el contrato de este §6. FE tiene pendiente enviar `Authorization` header.

## 7. Contrato API BE

Base: `http://localhost:8080/api/`.

### 7.1 Usuarios — `freya/src/main/java/com/salest/freya/controllers/UserController.java:15-48`
Requiere JWT en todos (`WebSecurityConfig.java:88`).

| Método | Ruta | Body | Respuesta |
|---|---|---|---|
| GET | `/users/{userId}` | — | `UserDTO` / 404 si no existe |
| GET | `/users` | — | `List<UserDTO>` |
| POST | `/users` | `CreateUserDTO` | `UserDTO` (alta directa, sin encoder aquí) |
| DELETE | `/users/{userId}` | — | `204` / 404 |
| PUT | — | — | No existe (comentado) |

DTOs: `UserDTO(id, userName, createdAt)`, `CreateUserDTO(userName, email, password)`.

### 7.2 Canciones — `freya/src/main/java/com/salest/freya/controllers/SongController.java:18-49`

| Método | Ruta | Auth | Body | Respuesta |
|---|---|---|---|---|
| GET | `/songs/{songId}` | público | — | `SongDTO` / 404 |
| GET | `/songs` | público | — | `List<SongDTO>` (catálogo) |
| POST | `/songs` | logged | `CreateSongDTO` | `SongDTO` |
| PUT | `/songs/{songId}` | logged | `UpdateSongDTO` | `SongDTO` / 404 |
| DELETE | `/songs/{songId}` | logged | — | `204` / 404 |

DTOs: `SongDTO(id, name, duration: float, lyrics, album: AlbumDTO, createdAt, updatedAt)`, `CreateSongDTO(duration, name, lyrics)` (sin `albumId`), `UpdateSongDTO(duration, name, lyrics, albumId)`, `AlbumDTO(id, name, artist, coverUrl)`, `CreateAlbumDTO(name, artist)` (existe pero sin controller).

Operador esperado: solo Admin (sin enforcement; hoy cualquier logged).

### 7.3 Covers — `freya/src/main/java/com/salest/freya/controllers/CoverController.java:17-71`, `freya/src/main/java/com/salest/freya/services/CoverService.java:85-107`

| Método | Ruta | Auth | Respuesta |
|---|---|---|---|
| GET | `/covers/{coverId}` | público | `CoverDTO` / 404 |
| GET | `/covers` , `/covers/approved` | público | Solo `APPROVED` |
| GET | `/covers/pending` | público en config actual | Solo `PENDING` (cola de moderación visible sin login — estado actual) |
| GET | `/covers/all` | logged | Todos los estados |
| POST | `/covers` | logged | `201 CoverDTO` desde `CreateCoverDTO` |
| POST | `/covers{coverId}/approve` | logged | `CoverDTO` con `APPROVED` (mapping en código sin `/` inicial: `@PostMapping("{coverId}/approve")`) |
| POST | `/covers{coverId}/reject` | logged | `CoverDTO` con `REJECTED` (igual observación) |
| PUT | `/covers/{coverId}` | logged | `CoverDTO` (solo `youtubeUrl, duration, songId` si no nulos) / 404 |
| DELETE | `/covers/{coverId}` | logged | `204` / 404 |

DTOs: `CoverDTO(id, duration, playCount, song: SongDTO, createdAt, updatedAt)` (no expone `status, uploader, youtubeUrl`), `CreateCoverDTO(duration, songId, uploaderUserId, youtubeUrl)`, `UpdateCoverDTO(youtubeUrl, duration, songId)`.

Media: solo `youtubeUrl` por ahora. Futuro: audio hosteado propio. `duration` manual (Integer), `playCount` persistido sin endpoint de incremento (seed con valores).

### 7.4 Utilidades
- `GET /api/health` público → `HealthDTO("ok")` (`HealthController.java:13-16`). FE lo usa para pantalla `Service Down`.
- `GET /api/test/all` público → `"Public Content."`; `GET /api/test/user` logged → `"User Content."` (`TestController.java:7-14`).
- `GET /catpat` → `"Catete Patete!"`.
- Swagger: `/swagger-ui.html`, `/v3/api-docs/**`.

## 8. Casos de uso (Regularidad)

### CU1 — ABMC Usuario (simple)
Actor: Logged (Admin operativo). `POST /users`, `GET /users`, `GET /users/{id}`, `DELETE /users/{id}`. Sin `PUT`. Errores: `404 IDNotFoundException`, `400 IllegalArgumentException` (id null).

### CU2 — ABMC Canción oficial (simple, solo Admin)
Actor: Admin en `/admin → songs` (FE). `POST/PUT/DELETE/GET /songs`. Alta mínima `CreateSongDTO(name, lyrics, duration)`; asociación a álbum solo vía `PUT {albumId}`. Eliminar con confirmación en FE; BE borra por id (`204`/`404`). Reflejo: Home y `SongView` recargan catálogo.

### CU3 — ABMC Cover (dependiente)
Actor: Logged. Depende de `Song` + `User` existentes (`songId, uploaderUserId`). `POST /covers`, `PUT /covers/{id}` (solo youtube/duration/song), `DELETE /covers/{id}`, `GET` varios. Si `songId` inexistente en update → `404`.

### CU4 — Subir y aprobar cover (flujo simple)
1. Logged elige canción destino, pega `youtubeUrl`, FE completa `duration + uploaderUserId` de sesión → `POST /covers` → estado inicial según `CoverMapper`/DB (cola `PENDING` operativa; ver seed).
2. Admin ve `GET /covers/pending` en `/admin → covers` → `POST .../approve` (visible en `GET /covers` y `SongView`) o `POST .../reject` (oculto de listados públicos).
3. Sin motivo de rechazo, sin notificación, sin edición por moderador, sin re-subida formal. Acción reversible solo vía `DELETE`.
4. No-Logged no puede subir (POST exige JWT → `401`).

### L1 — Listado de covers por cada canción
Alcance: ver todos los covers aprobados de una canción en `SongView`. Implementación actual: `GET /songs/{id}` + `GET /covers` (aprobados) con filtro en cliente FE por `cover.song.id`. Sin paginación/orden server-side; `Home` ordena últimos covers por `createdAt` desc en cliente. Criterio: si canción inexistente → `"Canción no encontrada"`; si sin covers → sección oculta; si cover inexistente → `"Cover no encontrado"`.

## 9. Seguridad y permisos (estado actual)

`WebSecurityConfig.java:67-91`: públicos `auth/**`, `GET health/**`, `/catpat`, `/test/all`, Swagger, `/error`, `GET /songs[/|/*]`, `GET /covers[/|/*]` (+ `/pending` por coincidir con `/covers/*`); autenticados `GET /songs/all`, `GET /covers/all`, `POST/PUT/DELETE songs/covers`, `users/**`, `test/user`, resto. JWT Bearer stateless.

## 10. Errores y validaciones (nivel actual)

- `GlobalExceptionHandler.java:10-26`: `IllegalArgumentException → 400 ProblemDetail{message}`, `IDNotFoundException(Cover/Song/...) → 404 ProblemDetail{message}`.
- `AuthEntryPointJwt`: `401` sin JWT en rutas protegidas.
- `signup` duplicado: `200` texto error (no `409`).
- Sin Bean Validation (`@Valid`), sin `409/422` tipados, sin paginación. Errores expuestos en UI (forms y “no encontrado”), no solo consola.

## 11. Seed (`data.sql`, recreado en cada arranque)

`freya/src/main/resources/data.sql:1-44`: 6 users, 7 albums (coverUrl `i.scdn.co`), 9 songs, 11 covers (5 `APPROVED`, 4 `PENDING`, 1 `REJECTED`, 1 `HIDDEN`; varios `youtubeUrl` NULL, `playCount` precargado).

## 12. Crosscheck FE–BE y gaps registrados (no bloquear Regularidad)

1. Auth: BE `signin/signup + username`; FE `login/register + email`. Vale BE §6.
2. `CoverDTO` no trae `status/uploader/youtubeUrl`; FE deriva `videoId` de `youtubeUrl` (hoy `null` → “Sin video disponible”) y muestra `playCount/fecha`.
3. `approve/reject` sin `/` inicial en mapping; probar con Swagger antes de integrar.
4. `Album` sin controller; alta de canción con álbum requiere `PUT` posterior o SQL.
5. `GET /covers/pending` público en config actual; si se quiere cola solo Admin, ajustar `WebSecurityConfig`.
6. `SongHeader` FE con imagen hardcodeada → usar `album.coverUrl`.
7. Filtro covers por canción en cliente → ideal query backend a futuro.

## 13. Futuro explícito (post-Regularidad)

Moderador, `Artista` entidad, `Playlist`, `Favorito`, `Comentario` + listado complejo con filtros de fecha, upload propio, `playCount` auto, `refresh token`, `hasRole("ADMIN")`, paginación, validaciones, `AlbumController`.
