# 🚀 FideiNexus Jenkins CI/CD Demo

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Java](https://img.shields.io/badge/Java-17-orange.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-green.svg)]()
[![License](https://img.shields.io/badge/license-MIT-blue.svg)]()

**CI/CD Pipeline Demo - FideiNexus Sacramental System**

Proyecto de demostración para implementación de pipeline automatizado en Jenkins, incluyendo integración con SonarQube, Slack, pruebas funcionales con Selenium y pruebas de carga con JMeter.

---

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#-descripción-del-proyecto)
- [Características](#-características)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Arquitectura](#-arquitectura)
- [Instalación](#-instalación)
- [Pipeline Jenkins](#-pipeline-jenkins)
- [Pruebas](#-pruebas)
- [Integraciones](#-integraciones)
- [Autores](#-autores)

---

## 📖 Descripción del Proyecto

FideiNexus es un sistema de gestión sacramental parroquial que permite administrar solicitudes de sacramentos (Bautismo, Confirmación, Matrimonio) de manera digital. Este repositorio contiene una versión simplificada del microservicio de solicitudes, diseñada específicamente para demostrar la implementación de un pipeline CI/CD completo.

### Objetivo

Implementar un pipeline automatizado en Jenkins que:
- ✅ Compile y pruebe la aplicación
- ✅ Realice análisis de calidad del código con SonarQube
- ✅ Execute pruebas funcionales con Selenium
- ✅ Execute pruebas de carga con JMeter
- ✅ Notifique resultados mediante Slack

---

## ✨ Características

- **API REST Reactiva**: Endpoints HTTP con Spring WebFlux
- **Arquitectura Hexagonal**: Separación clara de responsabilidades
- **Base de Datos**: PostgreSQL con R2DBC (reactivo)
- **Documentación**: Swagger/OpenAPI integrado
- **Tests Unitarios**: JUnit 5 + Mockito
- **Code Coverage**: JaCoCo
- **CI/CD**: Jenkins Pipeline completo

---

## 🛠 Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.4.5**
- **Spring WebFlux** (programación reactiva)
- **Spring Data R2DBC** (acceso a datos reactivo)
- **Lombok** (reducción de boilerplate)
- **SpringDoc OpenAPI** (documentación automática)

### Testing
- **JUnit 5** (tests unitarios)
- **Reactor Test** (tests reactivos)
- **JaCoCo** (cobertura de código)
- **JMeter** (pruebas de carga)
- **Selenium WebDriver** (pruebas funcionales)

### CI/CD & Quality
- **Jenkins** (automatización)
- **SonarQube/SonarCloud** (calidad de código)
- **Slack** (notificaciones)
- **Maven** (gestión de dependencias)

---

## 🏗 Arquitectura

```
fideinexus-jenkins-demo/
├── src/
│   ├── main/
│   │   ├── java/vallegrande/edu/pe/fideinexus/
│   │   │   ├── FideiNexusApplication.java
│   │   │   ├── domain/
│   │   │   │   ├── model/Request.java
│   │   │   │   └── port/RequestRepository.java
│   │   │   └── infrastructure/
│   │   │       └── adapter/RequestController.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/vallegrande/edu/pe/fideinexus/
│           ├── FideiNexusApplicationTests.java
│           └── domain/model/RequestTest.java
├── jmeter/
│   ├── scenario-green.jmx   (10 usuarios, 5s)
│   ├── scenario-yellow.jmx  (50 usuarios, 10s)
│   └── scenario-red.jmx     (100 usuarios, 20s)
├── selenium/
│   ├── test1-login.py       (Prueba de autenticación)
│   └── test2-request.py     (Prueba de creación de solicitud)
├── Jenkinsfile              (Pipeline completo)
├── pom.xml                  (Maven config)
└── README.md
```

### Arquitectura Hexagonal

```
┌─────────────────────────────────────────────────┐
│              Adaptadores de Entrada             │
│          (RequestController - REST API)         │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│                 Núcleo de Dominio               │
│          (Request, RequestRepository)           │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│              Adaptadores de Salida              │
│         (R2DBC Repository - PostgreSQL)         │
└─────────────────────────────────────────────────┘
```

---

## 🚀 Instalación

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.9+**
- **Jenkins** (con plugins: Pipeline, SonarQube Scanner, Slack Notification, Performance)
- **PostgreSQL** (o H2 para desarrollo)
- **JMeter 5.6+**
- **Python 3.8+** con Selenium
- **ChromeDriver** (para Selenium)

### Clonar el Repositorio

```bash
git clone https://github.com/DubanSalas/fideinexus-jenkins-demo.git
cd fideinexus-jenkins-demo
```

### Compilar el Proyecto

```bash
mvn clean install
```

### Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8087`

### Acceder a Swagger

```
http://localhost:8087/swagger-ui.html
```

---

## 🔄 Pipeline Jenkins

### Stages del Pipeline

1. **Checkout**: Clona el repositorio desde GitHub
2. **Build**: Compila el código fuente (`mvn clean compile`)
3. **Unit Tests**: Ejecuta tests unitarios y genera reporte JaCoCo
4. **SonarQube Analysis**: Analiza calidad del código
5. **Quality Gate**: Verifica que el código cumpla los estándares
6. **Package**: Genera el JAR ejecutable
7. **JMeter Load Tests**: Ejecuta 3 escenarios de carga (verde, amarillo, rojo)
8. **Selenium Functional Tests**: Ejecuta pruebas funcionales

### Configuración en Jenkins

```groovy
// Ver archivo: Jenkinsfile
```

---

## 🧪 Pruebas

### Tests Unitarios

```bash
mvn test
```

### Cobertura de Código

```bash
mvn jacoco:report
```

Reporte disponible en: `target/site/jacoco/index.html`

### Pruebas de Carga (JMeter)

**Escenario Verde** (10 usuarios, 5 segundos):
```bash
jmeter -n -t jmeter/scenario-green.jmx -l target/green-results.jtl
```

**Escenario Amarillo** (50 usuarios, 10 segundos):
```bash
jmeter -n -t jmeter/scenario-yellow.jmx -l target/yellow-results.jtl
```

**Escenario Rojo** (100 usuarios, 20 segundos):
```bash
jmeter -n -t jmeter/scenario-red.jmx -l target/red-results.jtl
```

### Pruebas Funcionales (Selenium)

```bash
python selenium/test1-login.py
python selenium/test2-request.py
```

---

## 🔗 Integraciones

### SonarQube / SonarCloud

- **Organization**: dubansalas
- **Project Key**: DubanSalas_fideinexus-jenkins-demo
- **URL**: https://sonarcloud.io

### Slack

- **Canal**: #jenkins-notifications
- **Webhook**: Configurado en Jenkins Credentials

### Métricas de Calidad

- **Code Coverage**: > 80%
- **Duplicated Lines**: < 3%
- **Code Smells**: 0 (objetivo)
- **Bugs**: 0
- **Vulnerabilities**: 0

---

## 📊 Resultados Esperados

### Escenarios JMeter

| Escenario | Usuarios | Duración | Throughput Esperado | Error Rate |
|-----------|----------|----------|---------------------|------------|
| Verde     | 10       | 5s       | 50 req/s            | < 1%       |
| Amarillo  | 50       | 10s      | 100 req/s           | < 5%       |
| Rojo      | 100      | 20s      | 150 req/s           | < 10%      |

### Tests Funcionales

| Test | Funcionalidad | Resultado Esperado |
|------|---------------|--------------------|
| Test 1 | Login de usuario | Redirección a /dashboard |
| Test 2 | Crear solicitud | Mensaje "Solicitud creada exitosamente" |

---

## 👥 Autores

**Duban Alexander Salas Napán**
- GitHub: [@DubanSalas](https://github.com/DubanSalas)
- Email: duban.salas@vallegrande.edu.pe

**Curso**: Arquitectura de Software  
**Institución**: Universidad Valle Grande  
**Año**: 2024

---

## 📄 Licencia

Este proyecto es de uso académico. Desarrollado como parte de la asignatura de Arquitectura de Software.

---

## 🎥 Video Demostrativo

[Enlace al video]() - Explicación completa del pipeline (8-10 minutos)

---

**⭐ Si te gustó este proyecto, dale una estrella en GitHub!**
