I needed to disable Windows NAT driver to run my Docker image.
1. Run PowerShell as admin
2. net stop winnat
To start Windows NAT driver
3. net start winnat

The Dockerfile contains instructions to run the Spring Boot image.
The docker-compose.yml in "customer" directory contains instructions to start
both the PostgreSQL instance and the Spring Boot image.

1. Open Docker Desktop
2. cd user\customer\
3. docker compose up -d --build
4. localhost:8003/customer/all
   - Should return "No customers found"