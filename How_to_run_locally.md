# Running the E-Commerce Microservices Application Locally

This guide provides instructions on how to set up and run the entire E-commerce microservices system on your local machine.

## 🏗️ Architecture Overview

The system consists of the following components:
- **Infrastructure**: Oracle Database, Apache Kafka (KRaft mode).
- **Config Server**: Centralized configuration management using Spring Cloud Config.
- **Microservices**:
  - `orderservice`: Handles order creation and management.
  - `paymentservice`: Processes payments for orders.
  - `notificationservice`: Sends notifications based on events.
- **Shared Libraries**: `events-lib` (shared event DTOs) and `dblogging` (shared logging logic).

---

## 📋 Prerequisites

Ensure you have the following installed:
- **Java 25**: The services are configured to use Java 25.
- **Maven**: For building the projects.
- **Docker & Docker Compose**: For running the infrastructure (Database & Kafka).
- **Lombok**: Ensure your IDE has the Lombok plugin installed and annotation processing is enabled.

---

## 🚀 Setup Steps

### 1. Start Infrastructure (Database & Kafka)

The application uses Docker Compose to manage the database and message broker.

```bash
docker-compose up -d oracle-db kafka-kraft
```

Wait for the containers to be healthy. You can check the status using:
```bash
docker ps
```

### 2. Install Shared Libraries

Before building the microservices, you must install the shared libraries into your local Maven repository (`.m2`).

#### Install `events-lib`
Navigate to the `events-lib` directory and run:
```bash
cd events-lib
mvn clean install
cd ..
```

#### Install `dblogging`
Navigate to the `dblogging` directory (located in the sibling folder) and run:
```bash
cd ../dblogging
mvn clean install
cd ../E-commerce-application
```

---

### 3. Run the Config Server

The microservices depend on the Config Server for their properties.

#### Build the Config Server:
```bash
cd config-server
mvn clean package -DskipTests
```

#### Start the Config Server:
You can run it using Docker Compose:
```bash
docker-compose up -d config-server
```
Or run it locally:
```bash
mvn spring-boot:run
```
*Note: If running locally, ensure it can access the `config-repo` directory.*

---

### 4. Build and Run Microservices

You can now start the individual services.

#### Option A: Using Docker Compose (Recommended)
This will build and start all services at once.
```bash
# First, build all jars
mvn clean package -DskipTests

# Start everything
docker-compose up --build
```

#### Option B: Running Individually (Development Mode)
If you want to run a specific service for debugging:
```bash
# Example for Order Service
cd orderservice
mvn spring-boot:run
```

---

## 📡 Service Ports

| Service | Port | Description |
| :--- | :--- | :--- |
| **Config Server** | `8888` | Central configuration |
| **Order Service** | `8081` | Order management API |
| **Payment Service** | `8082` | Payment processing API |
| **Notification Service** | `8083` | Notification handler |
| **Oracle DB** | `1521` | Database |
| **Kafka** | `9092` | Message broker |

---

## ✅ Verification

1. **Check Config Server**: Access `http://localhost:8888/orderservice/default`. You should see the configuration properties.
2. **Check Order Service**: Access `http://localhost:8081/api/orders`.
3. **Test the Flow**:
   - Create an order via `POST http://localhost:8081/api/orders`.
   - Check logs of `paymentservice` and `notificationservice` to see the event-driven flow in action.

---

## 🛠️ Troubleshooting

- **Oracle DB Connection**: If the services fail to connect to Oracle, ensure the `oracle-db` container is fully started (it can take a minute).
- **Kafka Connectivity**: If you see Kafka connection errors, ensure the `KAFKA_ADVERTISED_LISTENERS` in `docker-compose.yml` is correctly set to `localhost:9092` for external access.
- **Missing Dependencies**: If Maven fails to find `events-lib` or `dblogging`, verify that you ran `mvn clean install` in their respective directories first.
