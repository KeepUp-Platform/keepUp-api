# 🚗 KeepUp - Plataforma de Gestión de Recursos (Enfoque MVP Cliente)

Este repositorio contiene el plan de trabajo y la documentación central para el MVP (Producto Mínimo Viable) de KeepUp, enfocado 100% en la experiencia del cliente.

**Estrategia Clave:** Backend-First (3 Devs) / Frontend Mínimo (1 Dev rotativo).
**Meta:** Entregar un flujo funcional de cliente en 2 Sprints (2 semanas).

---

## Forma de Trabajo 

Se descarta el módulo de `Admin` para el MVP.

* **🧩 Épica 1: Autenticación de Clientes**
    * **Descripción:** Como cliente, quiero registrarme e iniciar sesión para acceder a mi cuenta personal.
    * **Features:**
        * `FEAT-AUTH-01`: Registro de Clientes
        * `FEAT-AUTH-02`: Login de Clientes (con JWT)

* **🧩 Épica 2: Gestión de Flota (Cliente)**
    * **Descripción:** Como cliente, quiero gestionar el ciclo de vida de mis vehículos, incluyendo sus documentos y gastos.
    * **Features:**
        * `FEAT-VEHICLE-01`: CRUD de Vehículos (vinculado al cliente)
        * `FEAT-DOCS-01`: CRUD de Documentos (vinculado a un vehículo)
        * `FEAT-EXPENSE-01`: CRUD de Gastos (vinculado a un vehículo)
        * `FEAT-ALERT-01`: Alertas de Vencimiento (Servicio de Notificación)

---

## Historias de Usuario (Backlog del MVP)

* **HU-001 (Registro):** Como visitante, quiero crear una cuenta (nombre, email, pass) para acceder a la plataforma.
* **HU-002 (Login):** Como usuario registrado, quiero iniciar sesión con mi email y pass para acceder a mi dashboard.
* **HU-004 (CRUD Vehículos):** Como cliente, quiero registrar, ver, editar y eliminar **mis** vehículos.
* **HU-006 (Documentos):** Como cliente, quiero registrar los documentos y fechas de vencimiento de **mis** vehículos.
* **HU-008 (Gastos):** Como cliente, quiero registrar los mantenimientos y gastos de **mis** vehículos.
* **HU-007 (Alertas):** Como cliente, quiero recibir notificaciones por email antes de que un documento se venza.

---

## 🚀 Sprint 1: Fundación (Auth + Core Vehículos)

* **Objetivo:** "Un cliente puede registrarse, iniciar sesión y realizar el CRUD completo de sus propios vehículos."
* **HUs:** `HU-001`, `HU-002`, `HU-004`.

### 👤 Dev 1: Andres Gonzales (Backend - Seguridad)
* **Feature:** `FEAT-AUTH` (Seguridad de API)
* **Tasks:**
    * `[Task-B-01]` Configurar Spring Security (dependencias JWT, `WebSecurityConfig`). **(6h)**
    * `[Task-B-02]` Implementar `PasswordEncoder` (BCrypt). **(3h)**
    * `[Task-B-03]` Implementar `UserDetailsService` (para cargar usuario por email). **(6h)**
    * `[Task-B-04]` Implementar Filtros JWT (`JwtRequestFilter` para validar el token). **(10h)**
    * `[Task-B-05]` Implementar `JwtUtil` (para generar y validar tokens). **(8h)**
    * `[Task-B-06]` Configurar CORS y endpoints públicos (`/api/auth/**`). **(4h)**
    * `[Task-B-07]` Revisión de PRs de Backend. **(3h)**

### 👤 Dev 2: Juan Pablo Vargas (Backend - Auth Logic)
* **Feature:** `FEAT-AUTH` (Lógica de Autenticación)
* **Tasks:**
    * `[Task-B-08]` Implementar Entidades `User` (nombre, email, pass) y `Role` (enum `ROLE_CLIENT`). **(6h)**
    * `[Task-B-09]` Implementar `UserRepository` y `RoleRepository`. **(3h)**
    * `[Task-B-10]` Implementar DTOs (`RegisterRequest`, `LoginRequest`, `AuthResponse`). **(4h)**
    * `[Task-B-11]` Implementar `AuthService` (métodos `register()` y `login()`). **(10h)**
    * `[Task-B-12]` Implementar `AuthController` (endpoints `/register` y `/login`). **(8h)**
    * `[Task-B-13]` Pruebas unitarias para `AuthService`. **(5h)**
    * `[Task-L-01]` (Como Líder) Gestión y revisión de PRs. **(4h)**

### 👤 Dev 4: Juan Pablo Rico (Backend - Lógica de Negocio)
* **Feature:** `FEAT-VEHICLE-01` (CRUD de Vehículos)
* **Tasks:**
    * `[Task-B-14]` Implementar Entidad `Vehicle` (Placa, Marca, etc.). **(4h)**
    * `[Task-B-15]` Establecer relación `ManyToOne` (Vehicle -> User). **(4h)**
    * `[Task-B-16]` Implementar `VehicleRepository`. **(2h)**
    * `[Task-B-17]` Implementar DTOs (`VehicleRequest`, `VehicleResponse`). **(4h)**
    * `[Task-B-18]` Implementar `VehicleService` (CRUD). **(10h)**
    * `[Task-B-19]` Modificar `VehicleService` para operar solo sobre vehículos del usuario autenticado (`AuthenticationPrincipal`). **(8h)**
    * `[Task-B-20]` Implementar `VehicleController` (Endpoints CRUD protegidos). **(6h)**
    * `[Task-B-21]` Pruebas unitarias para `VehicleService`. **(2h)**

### 👤 Dev 3: Samuel Zapata (Frontend - Angular Básico)
* **Feature:** `FEAT-AUTH` y `FEAT-VEHICLE-01` (UI Mínima)
* **Tasks:**
    * `[Task-F-01]` Crear proyecto Angular (con routing, `HttpClientModule`). **(4h)**
    * `[Task-F-02]` Crear `AuthService` (Angular) (login, register, logout). **(6h)**
    * `[Task-F-03]` Crear `AuthInterceptor` (para adjuntar JWT). **(6h)**
    * `[Task-F-04]` Crear `AuthGuard` (para proteger rutas). **(4h)**
    * `[Task-F-05]` Crear `RegisterComponent` (formulario). **(4h)**
    * `[Task-F-06]` Crear `LoginComponent` (formulario). **(4h)**
    * `[Task-F-07]` Crear `VehicleService` (Angular) (CRUD). **(4h)**
    * `[Task-F-08]` Crear `VehicleListComponent` (tabla simple). **(4h)**
    * `[Task-F-09]` Crear `VehicleFormComponent` (formulario en modal/página). **(4h)**

---

## 🚀 Sprint 2: Valor Agregado (Documentos, Gastos y Alertas)

* **Objetivo:** "Un cliente puede añadir documentos y gastos a sus vehículos y recibirá alertas de vencimiento."
* **HUs:** `HU-006`, `HU-008`, `HU-007`.
* **Nota de Rotación:** Samuel pasa a Backend, JP Rico pasa a Frontend.

### 👤 Dev 1: Andres Gonzales (Backend - Documentos)
* **Feature:** `FEAT-DOCS-01`
* **Tasks:**
    * `[Task-B-22]` Implementar Entidad `Document` (tipo, fechaVencimiento). **(4h)**
    * `[Task-B-23]` Establecer relación `ManyToOne` (Document -> Vehicle). **(3h)**
    * `[Task-B-24]` Implementar `DocumentRepository`. **(2h)**
    * `[Task-B-25]` Implementar DTOs y Mappers para `Document`. **(4h)**
    * `[Task-B-26]` Implementar `DocumentService` (CRUD anidado, verificando propiedad). **(10h)**
    * `[Task-B-27]` Implementar `DocumentController` (Endpoints: `.../vehicles/{vehicleId}/documents`). **(10h)**
    * `[Task-B-28]` Pruebas unitarias para `DocumentService`. **(4h)**
    * `[Task-B-29]` Revisión de PRs. **(3h)**

### 👤 Dev 2: Juan Pablo Vargas (Backend - Alertas)
* **Feature:** `FEAT-ALERT-01` (Complejo)
* **Tasks:**
    * `[Task-B-30]` Configurar `MailService` (Integración SendGrid, `MailConfig`). **(6h)**
    * `[Task-B-31]` Implementar `@Scheduled` (Job programado) en un `SchedulerService`. **(6h)**
    * `[Task-B-32]` Lógica del Job: Buscar en `DocumentRepository` docs por vencer en 15 días. **(8h)**
    * `[Task-B-33]` Lógica del Job: Iterar, obtener email del `User` y llamar al `MailService`. **(8h)**
    * `[Task-B-34]` Implementar Entidad/Controller `Alert` (para notificaciones en dashboard). **(8h)**
    * `[Task-L-02]` (Como Líder) Gestión y revisión de PRs. **(4h)**

### 👤 Dev 3: Samuel Zapata (Backend - Gastos)
* **Feature:** `FEAT-EXPENSE-01`
* **Tasks:**
    * `[Task-B-36]` Implementar Entidad `Expense` (tipo, fecha, costo). **(4h)**
    * `[Task-B-37]` Establecer relación `ManyToOne` (Expense -> Vehicle). **(3h)**
    * `[Task-B-38]` Implementar `ExpenseRepository`. **(2h)**
    * `[Task-B-39]` Implementar DTOs y Mappers para `Expense`. **(4h)**
    * `[Task-B-40]` Implementar `ExpenseService` (CRUD anidado, verificando propiedad). **(10h)**
    * `[Task-B-41]` Implementar `ExpenseController` (Endpoints: `.../vehicles/{vehicleId}/expenses`). **(10h)**
    * `[Task-B-42]` Pruebas unitarias para `ExpenseService`. **(4h)**
    * `[Task-B-43]` Revisión de PRs. **(3h)**

### 👤 Dev 4: Juan Pablo Rico (Frontend - Angular Básico)
* **Feature:** UI para Docs, Expenses, Alerts
* **Tasks:**
    * `[Task-F-10]` Crear `DocumentService` (Angular). **(4h)**
    * `[Task-F-11]` Crear `ExpenseService` (Angular). **(4h)**
    * `[Task-F-12]` Crear `AlertService` (Angular). **(3h)**
    * `[Task-F-13]` Modificar la vista `Vehicle` (o crear `VehicleDetail`) para mostrar pestañas (Info, Documentos, Gastos). **(6h)**
    * `[Task-F-14]` Crear `DocumentListComponent` y `DocumentFormComponent`. **(6h)**
    * `[Task-F-15]` Crear `ExpenseListComponent` y `ExpenseFormComponent`. **(6h)**
    * `[Task-F-16]` Integrar los 3 servicios con sus respectivos endpoints. **(7h)**
    * `[Task-F-17]` Crear `AlertBellComponent` en el Navbar. **(4h)**

---

## 🌳 Estrategia de Ramas (Git Workflow)

Usaremos **GitHub Flow** (simple y ágil).
1.  `main`: Rama principal, siempre estable y desplegable. **Protegida**.
2.  `feature/[TIPO]/[ID]_[DESCRIPCION]`: Ramas de trabajo.

**Nomenclatura:** `[tipo]/[ID-feature-o-task]_[descripcion-corta]`
* **Tipos:** `feature/`, `fix/`, `chore/`
* **Ejemplo:** `feature/FEAT-AUTH-01_registro-clientes`

**Flujo:**
1.  `git checkout main`
2.  `git pull origin main` (Sincronizar)
3.  `git checkout -b feature/FEAT-01_mi-tarea` (Crear rama)
4.  *(...trabajar y hacer commits...)`
5.  `git push origin feature/FEAT-01_mi-tarea` (Subir)
6.  Crear **Pull Request (PR)** en GitHub apuntando a `main` y solicitar revisión.

---
```
keepup-api/
├── .github/
│   └── pull_request_template.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/keepup/
│   │   │       ├── KeepupApplication.java
│   │   │
│   │   │       ├── core/                         # Configuración general y utilidades
│   │   │       │   ├── config/                   # Configuraciones (CORS, Mail, etc.)
│   │   │       │   │   ├── CorsConfig.java
│   │   │       │   │   └── MailConfig.java
│   │   │       │   ├── exception/                # Manejo global de excepciones
│   │   │       │   │   ├── GlobalExceptionHandler.java
│   │   │       │   │   └── ResourceNotFoundException.java
│   │   │       │   └── security/                 # Seguridad y JWT
│   │   │       │       ├── WebSecurityConfig.java
│   │   │       │       ├── UserDetailsServiceImpl.java
│   │   │       │       ├── JwtUtil.java
│   │   │       │       └── JwtRequestFilter.java
│   │   │
│   │   │       ├── auth/                         # Módulo de Autenticación (SPRINT 1)
│   │   │       │   ├── controller/
│   │   │       │   │   └── AuthController.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── RegisterRequest.java
│   │   │       │   │   ├── LoginRequest.java
│   │   │       │   │   └── AuthResponse.java
│   │   │       │   ├── entity/
│   │   │       │   │   ├── User.java
│   │   │       │   │   └── Role.java
│   │   │       │   ├── repository/
│   │   │       │   │   ├── UserRepository.java
│   │   │       │   │   └── RoleRepository.java
│   │   │       │   └── service/
│   │   │       │       └── AuthService.java
│   │   │
│   │   │       ├── vehicle/                      # Módulo de Vehículos (SPRINT 1 y 2)
│   │   │       │   ├── controller/
│   │   │       │   │   ├── VehicleController.java
│   │   │       │   │   ├── DocumentController.java
│   │   │       │   │   └── ExpenseController.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── VehicleDTO.java
│   │   │       │   │   ├── DocumentDTO.java
│   │   │       │   │   └── ExpenseDTO.java
│   │   │       │   ├── entity/
│   │   │       │   │   ├── Vehicle.java
│   │   │       │   │   ├── Document.java
│   │   │       │   │   └── Expense.java
│   │   │       │   ├── repository/
│   │   │       │   │   ├── VehicleRepository.java
│   │   │       │   │   ├── DocumentRepository.java
│   │   │       │   │   └── ExpenseRepository.java
│   │   │       │   └── service/
│   │   │       │       ├── VehicleService.java
│   │   │       │       ├── DocumentService.java
│   │   │       │       └── ExpenseService.java
│   │   │
│   │   │       └── notification/                 # Módulo de Alertas (SPRINT 2)
│   │   │           ├── controller/
│   │   │           │   └── AlertController.java
│   │   │           ├── entity/
│   │   │           │   └── Alert.java
│   │   │           ├── repository/
│   │   │           │   └── AlertRepository.java
│   │   │           └── service/
│   │   │               ├── SchedulerService.java
│   │   │               └── NotificationService.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties             # Configuración: BBDD, JWT, SendGrid, etc.
│   │       └── db/
│   │           └── migration/                     # (Opcional: Flyway/Liquibase)
│
│   └── test/
│       └── java/
│           └── com/keepup/
│               ├── auth/service/
│               │   └── AuthServiceTest.java
│               └── vehicle/service/
│                   ├── VehicleServiceTest.java
│                   └── (...)
│
├── pom.xml

