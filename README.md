# Microservicio de Usuarios (`backend-usuario`)

Este microservicio forma parte del ecosistema **Pedidos360**. Su responsabilidad principal es gestionar el registro de los usuarios una vez que se han autenticado mediante Azure AD, y notificar asíncronamente a otros servicios (como el de Perfiles) a través de RabbitMQ.

##  Características Principales

*   **Autenticación Delegada:** Valida tokens JWT emitidos por Azure AD (OAuth2 Resource Server).
*   **Mensajería Asíncrona:** Publica un evento en RabbitMQ (`usuario.creado.queue`) cada vez que un usuario nuevo inicia sesión por primera vez.
*   **Persistencia:** Utiliza PostgreSQL para almacenar un registro local básico del usuario.



##  Endpoints

### `POST /api/v1/usuarios/sincronizar`
*   **Auth:** Requiere un `Bearer Token` (JWT de Azure AD).
*   **Descripción:** Extrae los claims del token (correo y nombre). Si el usuario no existe en la BD local, lo crea y dispara el evento a RabbitMQ. Retorna los datos del usuario.

## Despliegue en EC2 con Docker Run

Para desplegar este microservicio junto al resto de tu ecosistema en AWS, sigue estos pasos:

1. **Construir la imagen de Docker:**
   ```bash
   docker build -t backend_usuario .
