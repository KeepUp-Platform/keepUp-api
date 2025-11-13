# 🚗 KeepUp - Product Backlog (Módulo: Vehículos)

 Visión general: “Plataforma de administración de recursos”

 Un sistema centralizado donde personas o empresas puedan registrar, monitorear y administrar cualquier tipo de recurso que requiera mantenimiento, pagos o seguimiento periódico.
 
 Administrar vehículos → registrar mantenimientos, seguros, SOAT, revisiones, gastos de combustible.

Este backlog prioriza las funcionalidades clave para entregar el valor mínimo viable (MVP) del módulo de vehículos.

> **Persona Principal:** Propietario de Vehículo (Puede ser un individuo o un administrador de flota).

---

### HU-001: Gestión básica de vehículos (CRUD)
* **Como:** Propietario de Vehículo,
* **Quiero:** Poder registrar, ver, editar y eliminar la información básica de mis vehículos (Placa, Marca, Modelo, Año, Color),
* **Para:** Tener un inventario centralizado de mi flota.

**Criterios de Aceptación:**
* El formulario de creación debe validar que la placa (matrícula) sea única.
* El usuario debe ver una lista/tabla con todos sus vehículos registrados.
* Al hacer clic en un vehículo, debe poder editar su información o eliminarlo.

**Estimación:** `6 h` (Complejidad Media)

---

### HU-002: Registro de documentos y vencimientos (SOAT/Seguros)
* **Como:** Propietario de Vehículo,
* **Quiero:** Registrar los documentos importantes de mi vehículo (ej. SOAT, Seguro Obligatorio, Revisión Técnico-Mecánica) y sus fechas de vencimiento,
* **Para:** Poder recibir alertas antes de que expiren.

**Criterios de Aceptación:**
* Desde la vista de un vehículo (HU-001), debe haber una sección para "Documentos".
* Se debe poder añadir un tipo de documento (lista desplegable), la fecha de expedición y la fecha de vencimiento.
* Se debe poder (opcional) subir un archivo PDF/imagen del documento.

**Estimación:** `4 h` (Complejeras Media-Baja)

---

### HU-003: Alertas de vencimiento
* **Como:** Propietario de Vehículo,
* **Quiero:** Recibir una notificación por correo electrónico y ver una alerta en mi dashboard 15 días antes de que un documento (SOAT, Seguro, etc.) se venza,
* **Para:** Evitar multas y renovar mis documentos a tiempo.

**Criterios de Aceptación:**
* Debe existir un proceso (ej. un `Job` programado en Spring Boot) que se ejecute diariamente.
* El proceso debe verificar los documentos que vencen en 15 días o menos.
* Debe enviar un correo (usando SendGrid) al propietario del vehículo.
* Las alertas deben aparecer en una sección "Notificaciones" del dashboard.

**Estimación:** `6h ` (Complejidad Media)

---

### HU-004: Registro de mantenimientos y gastos
* **Como:** Propietario de Vehículo,
* **Quiero:** Registrar los mantenimientos realizados a mi vehículo (ej. cambio de aceite, llantas) y otros gastos (ej. combustible, peajes),
* **Para:** Llevar un historial completo de costos y servicios de cada vehículo.

**Criterios de Aceptación:**
* Desde la vista de un vehículo, debe haber una sección de "Historial" o "Gastos".
* Se debe poder registrar un evento con: Fecha, Tipo (Mantenimiento, Combustible, Otro), Descripción y Costo.
* El sistema debe mostrar un total de gastos por vehículo.

**Estimación:** `4 h` (Complejidad Media-Baja)

---



---

## 📋 Planificación de Sprint y Distribución del Equipo

**Objetivo del Sprint:** "Al finalizar el Sprint, un usuario podrá registrar sus vehículos, asociarles documentos con vencimiento, registrar gastos de mantenimiento y recibir alertas por correo electrónico antes de que los documentos expiren."

### Andres gonzales: "Backend Core"
* **Enfoque:** Construir la base de la API REST de Spring Boot.
* **Tareas:**
    * Implementar la entidad, repositorio, servicio y controlador para **HU-001 (CRUD de Vehículos)**.
    * Definir la arquitectura de seguridad inicial (ej. JWT) para asegurar los endpoints.
    * Configurar la conexión a la base de datos PostgreSQL.

### Juan pablo vargas: "Backend Services"
* **Enfoque:** Desarrollar las lógicas de negocio y servicios complementarios.
* **Tareas:**
    * Implementar la entidad, repositorio, servicio y controlador para **HU-002 (Documentos)**.
    * Implementar la entidad y API para **HU-004 (Gastos/Mantenimiento)**.
    * Ambos servicios deben estar correctamente asociados a la entidad Vehículo.

### Samuel zapata: "Frontend Core"
* **Enfoque:** Construir la interfaz de usuario para la gestión principal.
* **Tareas:**
    * Crear el componente (React/Angular) para el **formulario de creación/edición de Vehículos (HU-001)**.
    * Crear el componente de **listado/dashboard de Vehículos (HU-001)**.
    * Integrar las vistas con los endpoints de Andres Zapata.

### Juan Pablo Rico: "Frontend & Integrations"
* **Enfoque:** Desarrollar las interfaces de usuario secundarias y el servicio de notificaciones.
* **Tareas:**
    * Crear los componentes de UI para **registrar Documentos (HU-002)** y **Gastos (HU-004)** (probablemente modales o pestañas en la vista del vehículo).
    * **Co-trabajar con Juan pablo vargas (Backend)** en la **HU-003 (Alertas)**:
        * *Dev 4 (Frontend):* Crea la sección "Alertas" en el dashboard.
        * *Dev 2 (Backend):* Configura el `@Scheduled` en Spring Boot y la integración con SendGrid para los correos.
