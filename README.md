## Prerequisites

The only requirement is Docker Desktop.

Java, Maven and Node are not required locally. All services are built and run in Docker.
On the first run, all images and dependencies (Maven, npm) are downloaded automatically, so the first startup takes a few minutes.

## Build and run

`docker-compose.yml` runs the services with the `prod` profile. `docker-compose_dev.yml` runs them with the `dev` profile and also starts ELK and Kafka UI. These are development tools and are left out of prod to reduce resource usage and startup time.

```bash
# Prod
docker compose up -d --build

# Dev
docker compose -f docker-compose_dev.yml up -d --build
```

Startup takes about 1.5–2 minutes after the images are built.
Test data is created by the `initdata` service, which uses a `CommandLineRunner` to call the services through the gateway once they are all healthy.

### Verify

```bash
docker compose ps -a
```

All services should be `healthy`, and `initdata` should show `Exited (0)` when the test data has been created.
Registered services and their health checks can be seen in Consul at http://localhost:8500.

The frontend runs as a Docker container and starts together with the rest of the project. Open http://localhost:5173 in the browser.

## Stop

```bash
# Prod
docker compose down --remove-orphans

# Dev
docker compose -f docker-compose_dev.yml down --remove-orphans
```

## Scaling

Scaling is recommended with the prod setup (`docker-compose.yml`). The dev setup also runs ELK (Elasticsearch, Logstash, Kibana), which uses a lot of memory and can make the system slow or unstable when running several extra instances.

```bash
# Scale a service up (example: customer-service to 3 instances)
docker compose up -d --no-deps --scale customer-service=3 customer-service

# Scale back down
docker compose up -d --no-deps --scale customer-service=1 customer-service
```

`--no-deps` and the service name at the end make sure only the scaled service is affected.

`chef-service`, `customer-service`, `recipe-service`, `productlisting-service` and `purchase-service` can run in multiple instances behind the gateway.

`taskscheduler-service` runs as a single instance on purpose. It runs one scheduled job, and multiple instances would run the same job at the same time, causing duplicate calls (and duplicate notifications).

The gateway, frontend, Consul, Kafka and databases run as single instances (fixed host ports / container names).

## Access points

| Component           | URL / Port            |
|---------------------|-----------------------|
| Gateway             | http://localhost:8100 |
| Frontend            | http://localhost:5173 |
| Consul UI           | http://localhost:8500 |
| Kafka               | http://localhost:9092 |
| Kafka UI (dev)      | http://localhost:8090 |
| Kibana (dev)        | http://localhost:5601 |
| Elasticsearch (dev) | http://localhost:9200 |

### Services

All services are accessed through the gateway. Host ports are assigned by Docker.

| Service                | Container port | Gateway route            |
|------------------------|----------------|--------------------------|
| chef-service           | 8002           | `/api/chef/**`           |
| customer-service       | 8003           | `/api/customer/**`       |
| productlisting-service | 8006           | `/api/productListing/**` |
| recipe-service         | 8008           | `/api/recipe/**`         |
| purchase-service       | 8011           | `/api/purchase/**`       |
| taskscheduler-service  | 8020           | no route (internal)      |

### Databases

User: `username` / Password: `password`

| Database         | Port |
|------------------|------|
| chefDB           | 5432 |
| customerDB       | 5433 |
| productlistingDB | 5434 |
| purchaseDB       | 5435 |
| recipeDB         | 5436 |

## Configuration

Central configuration is in `consul/consul_kv.json` and is loaded into Consul KV on startup by `consul-importer`.
After editing it, rebuild the importer:

```bash
docker compose build consul-importer
```

Hvis formateringen fortsatt blir feil, kan du åpne filen README_startup.md jeg sendte og kopiere innholdet derfra. Den har riktig markdown.