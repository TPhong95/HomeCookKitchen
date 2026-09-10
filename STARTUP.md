## General info
Localhost&DB ports
```
User:       8000 - 8005
- chefuser: 8002
- custUser: 8003
User_DB:    5432:5432
product:    8006 - 8010
product_DB: 5433:5433
Transaction:8011 - 8015
Tranac_DB:  5434:5434
```

# Startup sequence

## 1 - user service:

```
cd user
spring-boot:run
```

## 2 - 