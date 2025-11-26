# Spring Kafka Training

A complete Spring Boot + Kafka training project with UK train data generation and processing.

## Quick Start

### 1. Start Development Environment

```shell
nix develop
```

This provides all necessary tools: Maven, JDK 21, Minikube, Helm, Helmfile, Kubectl, and K9s.

### 2. Deploy Kafka Cluster

```shell
# Start Minikube
minikube start --memory=4096 --cpus=2

# Deploy Kafka using Helmfile
helmfile sync

# Wait for Kafka to be ready
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=kafka -n kafka --timeout=300s
```

Or use the task runner:

```shell
task setup-kafka
```

### 3. Run the Kafka Producer

```shell
# Set Kafka connection
export KAFKA_BOOTSTRAP_SERVERS=$(minikube ip):30092

# Run producer
task run-producer
```

## Project Structure

- `producer/` - Spring Boot Kafka producer application
  - Generates realistic UK train station data
  - Sends messages to Kafka topics
  - Auto-scheduled data publishing
- `helmfile.yaml` - Kafka cluster configuration
- `values/` - Helm chart values for Kafka
- `Taskfile.yml` - Common development tasks

## Features

- ✅ Automated UK train data generation (stations, services, schedules)
- ✅ Kafka producer with Spring Boot
- ✅ KRaft mode Kafka (no Zookeeper required)
- ✅ Minikube-based local development
- ✅ Helmfile for infrastructure as code
- ✅ Nix flake for reproducible environment

## Documentation

- [Kafka Setup Guide](KAFKA_SETUP.md) - Detailed Kafka cluster setup and management
- [Producer Documentation](producer/README.md) - Producer application details

## Common Tasks

```shell
# Kafka Management
task setup-kafka      # Start Minikube and deploy Kafka
task kafka-status     # Check Kafka cluster status
task kafka-logs       # View Kafka logs
task kafka-shell      # Access Kafka pod shell
task kafka-destroy    # Remove Kafka cluster

# Application
task run-producer     # Run the producer application
```

## Development Workflow

1. Start Kafka cluster: `task setup-kafka`
2. Run producer: `task run-producer`
3. Monitor with K9s: `k9s -n kafka`
4. View Kafka logs: `task kafka-logs`

## Next Steps

- [ ] Create consumer application
- [ ] Add Kafka UI for monitoring
- [ ] Implement data processing pipelines
- [ ] Add integration tests

## Resources

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring Kafka Documentation](https://spring.io/projects/spring-kafka)
- [Bitnami Kafka Helm Chart](https://github.com/bitnami/charts/tree/main/bitnami/kafka)

