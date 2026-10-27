## General info
Localhost&DB ports
```
User:       8000 - 8005
- chefuser: 8002
- custUser: 8003
Chef_DB:    5432:5432
Cust_DB:    5433:5432
recipe:     8008 - 8010
recipe_DB:  5436:5432
productlisting_DB: 5434:5432
productlisting: 8006 - 8007
purcahse:8011 - 8015
purchase_DB:  5435:5435
```

# Startup sequence

## 1 - user service:

```
cd user
spring-boot:run
```

## 2 - 