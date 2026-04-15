# CRUD Academia - Backend API

API REST para gestionar alumnos, materias y notas, con soporte de importacion de datos de prueba en formato .dump.

## Quickstart (1 minuto)

1. En esta carpeta, copia variables: Copy-Item .env.example .env
2. Levanta API + DB: docker compose up -d --build
3. Verifica salud: (Invoke-WebRequest -Uri "http://localhost:8080/api/health" -UseBasicParsing).Content
4. Carga datos de prueba: Invoke-WebRequest -Method POST -Uri "http://localhost:8080/api/import/dumps/sample-data.dump" -UseBasicParsing
5. Abre Swagger: http://localhost:8080/swagger-ui/index.html

## Stack

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Maven
- PostgreSQL
- Docker + Docker Compose
- OpenAPI (Swagger)

## Estructura del proyecto

- controller: endpoints REST
- service: logica de negocio
- repository: acceso a datos
- model: entidades JPA
- dto: contratos request/response
- exception: manejo global de errores

El frontend se encuentra en una carpeta independiente llamada "crud academia front".

## Variables de entorno

Copia .env.example a .env y ajusta valores:

- API_PORT: puerto de la API (default 8080)
- FRONTEND_ORIGIN: origen permitido por CORS
- DB_PORT: puerto host de PostgreSQL (default 5433)
- DB_NAME: nombre de la base de datos
- DB_USER: usuario
- DB_PASSWORD: contrasena
- DB_DRIVER: driver JDBC
- DB_URI: URI para ejecucion local fuera de Docker

## Ejecucion con Docker 

1. Copy-Item .env.example .env
2. docker compose up -d --build
3. docker compose ps
4. docker compose logs -f api
5. docker compose down

## Ejecucion local 

1. docker compose up -d db
2. Configurar variables de entorno segun .env
3. Ejecutar la API:

```powershell
& "C:\Users\darve\tools\apache-maven-3.9.11\bin\mvn.cmd" spring-boot:run
```

## Pruebas

Las pruebas Maven ya no dependen de PostgreSQL: usan perfil test con H2 en memoria.

```powershell
& "C:\Users\darve\tools\apache-maven-3.9.11\bin\mvn.cmd" test
```

## Endpoints principales

### Salud y documentacion

- GET /api/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

### Alumnos

- POST /api/alumnos
- GET /api/alumnos
- GET /api/alumnos/{id}
- PUT /api/alumnos/{id}
- DELETE /api/alumnos/{id}

### Materias

- POST /api/materias
- GET /api/materias
- GET /api/materias/{id}
- PUT /api/materias/{id}
- DELETE /api/materias/{id}

### Notas

- POST /api/notas
- GET /api/notas/alumno/{alumnoId}
- GET /api/notas/alumno/{alumnoId}?materiaId={materiaId}

### Importacion de dumps

- GET /api/import/dumps
- POST /api/import/dumps/{fileName}
- POST /api/import/dumps/upload (multipart, campo file)

Dump de ejemplo incluido:

- src/main/resources/dumps/sample-data.dump

## Ejemplos rapidos (PowerShell)

Importar dump interno:

```powershell
Invoke-WebRequest -Method POST -Uri "http://localhost:8080/api/import/dumps/sample-data.dump" -UseBasicParsing
```

Listar alumnos:

```powershell
(Invoke-WebRequest -Uri "http://localhost:8080/api/alumnos" -UseBasicParsing).Content
```

## Postman

Archivos disponibles en:

- postman/crud-academia.postman_collection.json
- postman/crud-academia.postman_environment.json
