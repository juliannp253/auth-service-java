# 🔐 AuthService - Microservicio de Autenticación (Spring Boot + JWT)

**AuthService** es un microservicio de autenticación moderno, seguro y fácilmente integrable, desarrollado con Java y Spring Boot. Diseñado con arquitectura limpia, seguridad robusta y pruebas automatizadas, es ideal para portafolios profesionales y simulaciones reales.

---

## 👨‍💻 Autor

**Julian P. Nuñez**

📫 [[GitHub](https://github.com/juliannp253)]

---

## 🧠 Objetivo

Construir una API REST de autenticación reutilizable para cualquier aplicación que requiera login, registro y manejo de tokens, ideal para microservicios o proyectos escalables. Todo funciona **localmente**, sin necesidad de servicios externos.

---

## 🚀 Tecnologías y Herramientas

| Tecnología        | Uso Principal                                  |
|------------------|-------------------------------------------------|
| Java 17          | Lenguaje principal                              |
| Spring Boot 3    | Framework de desarrollo                         |
| Spring Security 6| Autenticación y autorización                    |
| Spring Data JPA  | Acceso a base de datos                          |
| H2 / MySQL       | H2 (pruebas/desarrollo), MySQL (producción)     |
| JWT (jjwt 0.11.5)| Generación y validación de tokens               |
| BCrypt           | Hashing seguro de contraseñas                   |
| DTO + Validación | Entrada segura con javax.validation             |
| Swagger/OpenAPI  | Documentación interactiva de la API             |
| JUnit & Mockito  | Pruebas unitarias de lógica y autenticación     |
| IntelliJ / VSCode| IDE de desarrollo                               |
| Postman / curl   | Pruebas manuales de endpoints                   |

---

## 🗂️ Arquitectura

```bash
📦 authservice
 ┣ 📂controller       # Controladores REST
 ┣ 📂service          # Lógica de negocio (registro, login)
 ┣ 📂repository       # Interfaces JpaRepository
 ┣ 📂model            # Entidades JPA (User, Role)
 ┣ 📂dto              # Clases DTO (LoginRequest, LoginResponse...)
 ┣ 📂security         # JWT, filtros, configuración de seguridad
 ┣ 📜 application.properties / application.yml
 ┗ 📜 README.md
```
---
## 🌐 Endpoints implementados

🔐 /auth/register
- **Método:** POST
- **Acceso:** Público
- **Acciones:**
    - Encripta contraseñas
    - Guarda usuario
    - Asigna rol **USER**

🔐 /auth/login
- **Método:** POST
- **Acceso:** Público
- **Acciones:**
    - Verifica credenciales
    - Retorna accessToken (15 min) y refreshToken (7 días)

🔄 /auth/refresh
- **Método:** POST
- **Acceso:** Público
- **Acciones:**
    - Verifica refreshToken
    - Retorna nuevo accessToken

🔒 /user/me
- **Método:** GET
- **Acceso:** Privado
    - Retorna usuario actual autenticado (token requerido)

---
🔐 Seguridad
JWT
Generación y validación de tokens con JwtUtil

Tokens firmados con HMAC SHA-256

Header: Authorization: Bearer <token>

Spring Security
Stateless JWT

Rutas /auth/**, /swagger-ui/** y /v3/api-docs/** abiertas

Filtro personalizado: JwtAuthenticationFilter

Roles con @PreAuthorize

BCrypt
Contraseñas cifradas con BCryptPasswordEncoder

Comparación segura durante login

