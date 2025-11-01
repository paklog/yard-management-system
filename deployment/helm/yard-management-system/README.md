# Yard Management System Helm Chart

Dock scheduling and trailer tracking system

## Installation

```bash
helm install yard-management-system .
```

## Configuration

See `values.yaml` for all configuration options.

### Key Configuration

```yaml
image:
  repository: paklog/yard-management-system
  tag: latest

service:
  port: 8094
  managementPort: 8081

resources:
  limits:
    cpu: 1000m
    memory: 1Gi
  requests:
    cpu: 500m
    memory: 512Mi

autoscaling:
  enabled: true
  minReplicas: 2
  maxReplicas: 10
```

### Database Configuration

MongoDB is enabled by default.



### Observability

- **Metrics**: Prometheus metrics available at `/actuator/prometheus`
- **Tracing**: OpenTelemetry traces sent to Tempo
- **Logging**: Structured logs sent to Loki

## Uninstallation

```bash
helm uninstall yard-management-system
```

## Maintainers

- Paklog Team
