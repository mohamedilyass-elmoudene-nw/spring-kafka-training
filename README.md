# Spring Kafka Training

A complete Spring Boot + Kafka training project with UK train data generation and processing.

## Quick Start

### 1. Start Development Environment

```shell
nix develop
```

This provides all necessary tools: Maven, JDK 21, and Task.

### 2. Start Infrastructure (Kafka + Kafka UI)

```shell
task kafka-up
```

This will start:
- **Kafka Broker** (KRaft mode) on `localhost:9092`
- **Kafka UI** on `http://localhost:8080`

### 3. Run Applications

**Run the Producer:**
```shell
task run-producer
```

**Run the Consumer:**
```shell
task run-consumer
```

## Project Structure

- `producer/` - Spring Boot Kafka producer application
  - Generates realistic UK train station data
  - Sends messages to Kafka topics
  - Auto-scheduled data publishing
- `consumer/` - Spring Boot Kafka consumer application
  - Consumes train data messages
  - Deserializes JSON payloads
- `docker-compose.yml` - Local infrastructure (Kafka, Kafka UI)
- `Taskfile.yml` - Common development tasks

## Features

- ✅ Automated UK train data generation (stations, services, schedules)
- ✅ Kafka producer with Spring Boot
- ✅ Kafka consumer with Spring Boot
- ✅ KRaft mode Kafka (no Zookeeper required)
- ✅ Docker Compose for local development
- ✅ Kafka UI for easy monitoring
- ✅ Nix flake for reproducible environment

## Common Tasks

```shell
# Infrastructure Management
task kafka-up         # Start Kafka and Kafka UI
task kafka-down       # Stop infrastructure
task kafka-logs       # View Kafka logs
task kafka-ui-logs    # View Kafka UI logs
task kafka-shell      # Access Kafka broker shell

# Application
task run-producer     # Run the producer application
task run-consumer     # Run the consumer application
```

## Development Workflow

1. Start Kafka cluster: `task kafka-up`
2. Open Kafka UI: [http://localhost:8080](http://localhost:8080)
3. Run consumer: `task run-consumer`
4. Run producer: `task run-producer`
5. Monitor logs and Kafka UI

## Resources

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring Kafka Documentation](https://spring.io/projects/spring-kafka)
- [Kafka UI](https://github.com/provectus/kafka-ui)
