# Startup

## Run

```bash
`docker-compose.yml` runs the services with the `prod` profile, `docker-compose_dev.yml` with `dev`.
# Prod
docker compose up -d --build

# Dev (includes ELK and Kafka UI)
docker compose -f docker-compose_dev.yml up -d --build

# Stop
docker compose down --remove-orphans

# Scale a service
docker compose up -d --scale customer-service=3
```

Startup takes about 1.5–2 minutes. Test data is created by the initdata service, which uses a CommandLineRunner to call the services through the gateway once they are all healthy. 

Scalable: chef-service, customer-service, recipe-service, productlisting-service, purchase-service. 

Single instance: taskscheduler-service (the scheduled job would otherwise run once per instance), and the gateway, frontend, Consul, Kafka and databases (fixed host ports / container names).



The frontend starts with the rest of the project. Open http://localhost:5173 in the browser.

## Ports

| Component | Port |
|---|---|
| Gateway | 8100 |
| Frontend | 5173 |
| Consul | 8500 |
| Kafka | 9092 |
| Kafka UI (dev) |"8090:8080"|
| Kibana (dev) | 5601 |
| Elasticsearch (dev) | 9200 |

## Services

Accessed through the gateway. Host ports are assigned by Docker.

| Service | Container port | Route                    |
|---|---|--------------------------|
| chef-service | 8002 | `/api/chef/**`           |
| customer-service | 8003 | `/api/customer/**`       |
| productlisting-service | 8006 | `/api/productListing/**` |
| recipe-service | 8008 | `/api/recipe/**`         |
| purchase-service | 8011 | `/api/purchase/**`       |
| taskscheduler-service | 8020 | no route                 |

## Databases

User: `username` / Password: `password`

| Database | Port |
|---|---|
| chefDB | 5432 |
| customerDB | 5433 |
| productlistingDB | 5434 |
| purchaseDB | 5435 |
| recipeDB | 5436 |

## Configuration

Central configuration in `consul/consul_kv.json`, loaded into Consul KV on startup.
After editing it: `docker compose build consul-importer`.