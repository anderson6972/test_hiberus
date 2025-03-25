# Hiberus - API de Precios

Esta aplicación es un microservicio desarrollado en Spring Boot 3 que expone un endpoint REST para consultar el precio final y la tarifa aplicable de un producto en función de la fecha de aplicación. Utiliza una arquitectura hexagonal, una base de datos en memoria H2 y proporciona documentación interactiva a través de Swagger UI.
En el apartado resources del proyecto se encuentra el archivo TestJava_back.txt con el enunciado de la prueba tecnica

---

## Tabla de Contenidos
- [Requisitos](#requisitos)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Ejecutar la Aplicación](#ejecutar-la-aplicación)
- [Desde la Línea de Comandos (Maven)](#desde-la-línea-de-comandos-maven)
- [Documentación de la API (Swagger UI)](#documentación-de-la-api-swagger-ui)
- [Acceso a la Consola H2](#acceso-a-la-consola-h2)
- [Especificaciones del Código y Arquitectura](#especificaciones-del-código-y-arquitectura)
- [Test unitarios y pruebas de integración](#test-unitarios-y-pruebas-de-integración)

---

## Requisitos
- Java 17 o superior.
- Maven 3.8 o superior.
---

## Estructura del proyecto
El proyecto sigue una arquitectura hexagonal, separando la lógica de dominio y casos de uso de los adaptadores de entrada (REST) y salida (persistencia). La estructura principal es la siguiente:

```plaintext
com.hiberus.prices
├── application
│   ├── port.in                // Interfaces de casos de uso
│   └── service                // Implementaciones de casos de uso
├── domain
│   ├── model                  // Entidades y objetos de valor del dominio
│   ├── repository             // Interfaces de repositorios (puertos de salida)
│   └── exception              // Excepciones del dominio
├── infrastructure
│   ├── adapter.in             // Adaptadores de entrada (REST, controladores, mappers, etc.)
│   └── adapter.out            // Adaptadores de salida (persistencia, entidades JPA, repositorios, etc.)
├── configuration              // Configuraciones generales (beans, Swagger, etc.)
└── generated                  // Código generado a partir del archivo OpenAPI (por el plugin)
```

# Ejecutar la Aplicación

## Desde la Línea de Comandos (Maven)

1. **Compilar y empaquetar la aplicación**:
   mvn clean install

2. **Ejecutar el JAR generado:
    java -jar target/prices-0.0.1-SNAPSHOT.jar

La aplicación estará disponible en el puerto 8080.

# Documentación de la API (Swagger UI)
La documentación interactiva de la API está disponible a través de Swagger UI. Una vez iniciada la aplicación, accede a:

http://localhost:8080/swagger-ui/index.html

Allí podrás probar los endpoints y consultar los esquemas de datos (incluyendo el modelo de errores).

# Acceso a la Consola H2
La base de datos H2 está configurada para ser accesible. Para acceder, abre en tu navegador:

http://localhost:8080/h2-console

Utiliza los siguientes datos de conexión:

JDBC URL: jdbc:h2:mem:pricesdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
Username: sa
Password: (vacío)


# Especificaciones del Código y Arquitectura

Arquitectura Hexagonal
La aplicación está estructurada en capas para separar la lógica de negocio de la infraestructura. Los adaptadores de entrada (controladores REST y mappers) y de salida (persistencia con JPA) están claramente definidos.

Gestión de Excepciones
Se implementa un controlador global de excepciones (@ControllerAdvice) para capturar y retornar respuestas de error según el modelo definido en OpenAPI.

Generación de Código
Se utiliza el plugin openapi-generator-maven-plugin para generar interfaces y modelos a partir del archivo OpenAPI (prices.yaml), promoviendo el enfoque API First.

#  Test unitarios y pruebas de integración
Este proyecto incluye pruebas de integración para validar el correcto funcionamiento de la API REST y su interacción con la base de datos. Se han implementado utilizando JUnit 5, Mockito y una base de datos en memoria H2 para pruebas.

Test Unitarios:
Para ejecutar todos los test unitario ejecutar desde la consola: `mvn test -DexcludedGroups="integracion"`

Pruebas de integracion:
Para ejecutar todos los test de integracion ejecutar desde la consola: `mvn test -DexcludedGroups="unit"`

Validan que los endpoints REST devuelvan respuestas correctas.