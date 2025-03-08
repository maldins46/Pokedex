# Pokedex API

A REST API that provides Pokémon information as part of a software engineering challenge.

## Features
- Retrieve basic Pokémon information from the PokéAPI.
- Get a translated description using FunTranslations (Yoda/Shakespeare styles).
- Built with **Spring Boot (Java 17)**.
- Uses **WebClient** for API calls.
- Graceful error handling and timeout management.
- Includes **Swagger UI** for API documentation.

## Prerequisites - Installing Dependencies

### 1. Java 17
The application requires **Java 17** to be installed on your machine to build and run.

#### **Windows**
1. Download and install **Eclipse Temurin JDK 17** from [Adoptium](https://adoptium.net/temurin/releases/?version=17).
2. Add Java to your system PATH (if not automatically configured).
3. Verify installation:
```sh
java -version
```

#### **MacOS/Linux**
1. Install Java via Homebrew (MacOS/Linux):
```sh
brew install openjdk@17
```
2. Verify installation:
```sh
java -version
```

### 2. Maven

**Maven** is also a prerequisite. However, the project includes the Maven Wrapper (`mvnw`), so you **don't need to install Maven manually**.

### 3. Docker (optional)

Docker is only required if you choose to deploy the application in a containerized environment. If needed, follow these steps:

- Download and install Docker from [Docker Official Site](https://www.docker.com/get-started).
- Verify installation:
```sh
docker --version
```

## Running the application
### 1. Build the project
```sh
./mvnw clean install    # macOS/Linux
mvnw.cmd clean install  # Windows
```

### 2. Run the application
```sh
java -jar target/PokedexAPI-1.0.0.jar
```

## Running with Docker (optional)

### 1. Package the project

```sh
./mvnw clean package    # macOS/Linux
mvnw.cmd clean package  # Windows
```

### 2. Build the Docker image
```sh
docker build -t pokedex-api .
```

### 3. Run the container
```sh
docker run -p 8080:8080 pokedex-api
```

## Example API Calls
### Get Basic Pokémon Information
```sh
curl -X GET http://localhost:8080/pokemon/mewtwo
```
**Response:**
```json
{
  "name": "mewtwo",
  "description": "It was created by a scientist after years of horrific gene splicing and DNA engineering experiments.",
  "habitat": "rare",
  "isLegendary": true
}
```

### Get Translated Pokémon Description
```sh
curl -X GET http://localhost:8080/pokemon/translated/mewtwo
```
**Response:**
```json
{
  "name": "mewtwo",
  "description": "Created by a scientist after years of horrific gene splicing and DNA engineering experiments, it was.",
  "habitat": "rare",
  "isLegendary": true
}
```