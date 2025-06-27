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
## 🔐 Seguridad
**JWT**
- Tokens firmados con HMAC SHA-256
- Se envía por header:
  Authorization: Bearer <access_token>
- Clase **JwtUtil**:
 - **generateAccessToken()**
 - **generateRefreshToken()**
 - **extractUsername()**
 - **isTokenValid()**

**Spring Security**
- Stateless (sin sesiones)
- Rutas públicas: **/auth/****, **/swagger-ui/**, **/v3/api-docs/****
- Filtro personalizado: JwtAuthenticationFilter
- Roles con **@PreAuthorize** y **@EnableMethodSecurity**

**BCrypt**
- Contraseñas hasheadas con **BCryptPasswordEncoder**
- Comparación segura en login

---
## 🛢️ Base de Datos
**Desarrollo**
- H2 en memoria
- Consola accesible en:
  **http://localhost:8080/h2-console**
- JDBC:
  **jdbc:h2:mem:testdb**

**Producción**
- Opcional: MySQL
- Puedes activar el perfil **prod** y cambiar las credenciales

---
## ⚙️ Ejecución del proyecto

```bash
git clone https://github.com/tu-usuario/auth-service-java.git
cd auth-service-java
mvn spring-boot:run
```
- Swagger: **http://localhost:8080/swagger-ui/index.html**
- H2 Console: **http://localhost:8080/h2-console**

---
## 🧪 Pruebas Automatizadas
**Cubren:**
- Registro:
  - Éxito
  - Email ya en uso
- Login:
  - Credenciales válidas
  - Contraseña incorrecta
  - Email no existente
**Ejecutar:**
```bash
mvn test
```
- Framework: JUnit 5
- Mocks: Mockito
- Assertions claras con **assertThrows**, **assertEquals**, etc.

---
## 🧩 Cómo integrar esta API en otro proyecto
Cualquier cliente (web o móvil) puede consumir esta API:

**Proceso general:**
1. POST a **/auth/register** → crea cuenta
2. POST a **/auth/login** → recibe tokens
3. Agrega **Authorization: Bearer <accessToken>** a tus peticiones
4. Si el token expira, llama a **/auth/refresh**

**Tecnologías compatibles:**
- Frontend (React, Angular, Vue)
- Aplicaciones móviles (Flutter, Android)
- Otros microservicios

---
## 🐳 Próximamente: Docker
Se agregará soporte para:
- Dockerfile
- docker-compose.yml (para levantar MySQL + API juntos)
- Guía paso a paso para contenerizar y desplegar

---
## 📄 Licencia y Uso
Este microservicio es de uso **público y educativo**, ideal para proyectos personales y de portafolio.
__No está optimizado para entornos productivos sin ajustes adicionales.__
