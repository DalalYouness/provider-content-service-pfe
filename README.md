# Provider Content Service

A professional microservice for the home services platform, designed to manage the service catalog and provider expertise within a distributed system.

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?style=for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge&logo=mysql" alt="MySQL" />
  <img src="https://img.shields.io/badge/Security-JWT-000000?style=for-the-badge&logo=jwt" alt="JWT Security" />
</p>

## Overview

This repository is part of a distributed home services platform developed as a final master's degree project. It is the content and catalog service responsible for:

- managing the list of available home services
- linking providers to services through expertise
- exposing provider/service data to other microservices
- enforcing authorization for admin and provider actions

In the broader architecture, this service works alongside other platform services such as the identity/authentication service and configuration discovery services, enabling a modular and scalable system.

## Project Role in the Distributed System

This service acts as the knowledge layer of the platform:

- the admin defines the global catalog of services
- providers declare which services they offer
- clients can discover providers by service category
- other services consume this information through REST APIs

## Core Functionalities

### 1. Service Catalog Management
The service catalog is managed by admins and includes:

- adding a new service
- listing all services with pagination
- searching services by keyword
- updating service metadata
- deleting services

### 2. Provider Expertise Management
Providers can:

- select their service set
- add a service to their profile
- remove a service
- view providers associated with a specific service

### 3. Secure Authorization Model
The application uses Spring Security and JWT-based validation to protect endpoints:

- public endpoints for catalog browsing
- admin-only endpoints for catalog administration
- provider-only endpoints for expertise updates

### 4. Integration with Identity Service
The service communicates with the identity service using Feign clients to retrieve provider information by ID. This allows the content service to stay focused on catalog and expertise data while delegating user/profile retrieval to the dedicated identity service.

## Technology Stack

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- MySQL
- Flyway migrations
- Spring Cloud Config
- Spring Cloud Netflix Eureka
- OpenFeign
- MapStruct
- Lombok
- Maven

## Repository Structure

```text
provider-content-service-pfe/
├── .github/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/dalal/providercontentservicepfe/
│   │   │   ├── dtos/
│   │   │   ├── entities/
│   │   │   ├── exceptions/
│   │   │   ├── feign/
│   │   │   ├── filters/
│   │   │   ├── handlers/
│   │   │   ├── mappers/
│   │   │   ├── repositories/
│   │   │   ├── security/
│   │   │   ├── services/
│   │   │   ├── web/
│   │   │   └── ProviderContentServicePfeApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   └── test/
├── .gitignore
├── .gitattributes
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Main Domain Concepts

### Category / Service
Represents a service offered in the platform, such as plumbing, cleaning, painting, electrical work, or gardening.

### Expertise
Represents the relationship between a provider and a service. It is modeled as a many-to-many association between provider IDs and service IDs.

### Portfolio Item
Stores additional provider content such as portfolio images and descriptions.

## REST API

### Service Catalog Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/service/add` | Create a new service (admin only) |
| GET | `/api/v1/service/all` | Get all services with pagination |
| GET | `/api/v1/service/search` | Search services by keyword |
| GET | `/api/v1/service/category/{id}` | Retrieve a specific service by ID |
| PUT | `/api/v1/service/update/{id}` | Update service data (admin only) |
| DELETE | `/api/v1/service/delete/{id}` | Delete a service (admin only) |

### Provider Expertise Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/expertise/select-services` | Replace the provider's selected services |
| POST | `/api/v1/expertise/add-more-service` | Add a new service to a provider |
| DELETE | `/api/v1/expertise/remove-service/{serviceId}` | Remove a service from a provider |
| GET | `/api/v1/expertise/{serviceId}/providers` | Get all providers offering a given service |

> Some routes are public and some are protected according to the role-based security rules defined in `SecurityConfig`.

## Security Configuration

The application enforces JWT-based authentication and uses method-level authorization:

- `@PreAuthorize("hasRole('ADMIN')")` for administrative operations
- `@PreAuthorize("hasRole('PRESTATAIRE')")` for provider operations
- public read access for catalog browsing and provider lookup operations

This ensures that service management is protected while discovery remains accessible to the platform.

## Database Design

The schema is managed with Flyway migrations and includes the following core tables:

- `services`
- `portfolio_items`
- `expertise`

The `expertise` table is the associative table linking providers with the services they offer.

## Local Setup

### Prerequisites

- Java 21+
- Maven 3.9+
- MySQL database
- Config Server running on `localhost:8888` (as configured in `application.properties`)

### 1. Clone the repository

```bash
git clone https://github.com/DalalYouness/provider-content-service-pfe.git
cd provider-content-service-pfe
```

### 2. Configure the database and config server
Update your database credentials and external configuration according to your local environment.

### 3. Run the application

Using Maven wrapper:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

### 4. Verify the service
The service starts on the default Spring Boot port and exposes the configured REST endpoints.

## Application Configuration

The main configuration is stored in:

```properties
src/main/resources/application.properties
```

It includes:

- application name
- Spring Cloud Config import
- JWT public key configuration

## Why This Service Matters

This microservice is essential to the platform because it transforms static catalog data into dynamic business logic:

- it defines what services exist
- it maps provider skills to those services
- it enables discovery and search in a home services marketplace
- it supports the platform's distributed and modular architecture

## Final Master's Project Context

This repository represents a practical implementation of a microservice in a distributed system, covering:

- REST API development
- domain modeling
- JPA persistence
- database migration
- service-to-service communication
- security and authorization
- technical integration in a larger platform ecosystem

## License

This project is currently developed as part of an academic project and may be adapted for demonstration, research, or portfolio purposes.

