# Pokedex API

A REST API that provides Pokémon information, part of a software engineering challenge.

# Prerequisites - Installing Dependencies
If your machine does not have Java or Maven installed, follow these steps.

### 1. Install Java 17
#### **Windows**
1. Download and install **Eclipse Temurin JDK 17** from [Adoptium](https://adoptium.net/temurin/releases/?version=17).
2. Add Java to your system PATH (if not automatically configured).
3. Verify installation:
```sh
java -version
```

#### **macOS/Linux**
1. Install Java via Homebrew (macOS/Linux):
```sh
brew install openjdk@17
```
2. Verify installation:
```sh
java -version
```

### 2. Install Maven (If Not Using the Wrapper)
#### **Windows/macOS/Linux**
- Download and install Maven from [Apache Maven](https://maven.apache.org/download.cgi).
- Verify installation:
```sh
mvn -version
```
> **Note**: The project includes the Maven Wrapper (`mvnw`), so you **don't need to install Maven manually**.

### 3. Install Docker (Optional, for Containerized Deployment)
- Download and install Docker from [Docker Official Site](https://www.docker.com/get-started).
- Verify installation:
```sh
docker --version
```

## Running the Application
### 1. Clone the repository
```sh
git clone <repository-url>
cd pokedex-api
```

### 2. Build the project
#### Using Maven
```sh
mvn clean install
```
#### Using Maven Wrapper (Recommended)
```sh
./mvnw clean install  # macOS/Linux
mvnw.cmd clean install  # Windows
```

### 3. Run the application
#### Using Maven
```sh
mvn spring-boot:run
```
#### Using Executable JAR
```sh
java -jar target/pokedex-api-1.0.0-SNAPSHOT.jar
```

### 4. Access the API
- **Base URL:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

### 5. Example API Calls
#### Get Basic Pokémon Information
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

#### Get Translated Pokémon Description
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

## Running with Docker (Optional)
### 1. Build the Docker Image
```sh
docker build -t pokedex-api .
```

### 2. Run the Container
```sh
docker run -p 8080:8080 pokedex-api
```
