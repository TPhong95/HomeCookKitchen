# Kafka setup
## 0 - Kafka broker as docker container `docker-compose.yml`
### A - Kafka container
This example automatically registers itself with Consul for health checks.
```
  kafka:
    image: apache/kafka:4.3.2
    container_name: kafka
    ports:
      - "9092:9092"
    depends_on:
      - consul
    healthcheck:
      test: [ "CMD", "/opt/kafka/bin/kafka-broker-api-versions.sh", "--bootstrap-server", "localhost:9092" ]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 15s
    environment:
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,HOST:PLAINTEXT,DOCKER:PLAINTEXT
      KAFKA_LISTENERS: CONTROLLER://:9091,HOST://:9092,DOCKER://:9093
      KAFKA_ADVERTISED_LISTENERS: HOST://localhost:9092,DOCKER://kafka:9093
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@localhost:9091
      KAFKA_INTER_BROKER_LISTENER_NAME: DOCKER
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    networks:
      - m2026
  consul-register-kafka:
    image: curlimages/curl:8.8.0
    container_name: consul-register-kafka
    depends_on:
      - consul
      - kafka
    networks:
      - m2026
    restart: on-failure
    command: >
      sh -c "curl -s -X PUT http://consul:8500/v1/agent/service/register
      -H 'Content-Type: application/json'
      -d '{\"Name\": \"kafka\", \"Address\": \"kafka\", \"Port\": 9093}'"
```

### B - KafBat (Web GUI for kafka)
This example includes a container for registering it with Consul:
```
  kafbat-ui: #Kafka web GUI
    image: docker.io/kafbat/kafka-ui:v1.5.0
    container_name: kafbat-ui
    ports:
      - "8080:8080"
    depends_on:
      kafka:
        condition: service_healthy
    environment:
      # Enable editing topics/messages within the web application interface
      DYNAMIC_CONFIG_ENABLED: 'true'
      # Configure the cluster connection pointing to the internal listener
      KAFKA_CLUSTERS_0_NAME: 'local-kraft-cluster'
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: 'kafka:9093'
    networks:
      - m2026
    #(Add volumes to persist config made through web GUI)

  consul-register-kafbat:
    image: curlimages/curl:8.8.0
    container_name: consul-register-kafbat
    depends_on:
      - consul
      - kafbat-ui
    networks:
      - m2026
    restart: on-failure
    command: >
      sh -c "curl -s -X PUT http://consul:8500/v1/agent/service/register
      -H 'Content-Type: application/json'
      -d '{\"Name\": \"kafbat-ui\", \"Address\": \"kafbat-ui\", \"Port\": 8080}'"
```

## 1 - Dependency:
Needs to have this dependency: 
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-kafka</artifactId>
</dependency>
```
in the `pom.xml` of each microservice that is to implement Kafka.

## 2 - Producer:
### 2.1 - `Application.yml` setup
Under `Spring:` add:
```
spring:
  # ... existing config ...
  kafka:
    bootstrap-servers: kafka:9093          # internal Docker listener (from docker-compose)
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JacksonJsonSerializer
      properties:
        "[spring.json.add.type.headers]": false   # don't send the Java class name
```
### 2.2 - New files
For good measure, add a directory in the producer service named `kafka`.
In that directory add the following files:
#### A - `Event`:
Record:
`public record <ServiceName><EventType>Event(Long chefId, String message) {}`
- I.e.: `CustomerNotificationEvent` and `ChefNotificationEvent` 
- Will use this as the basis of the following examples with one producer interacting with 2 consumers 

```public record ChefNotificationEvent(Long chefId, String message) {}```
```public record CustomerNotificationEvent(Long chefId, String message) {}```

#### B - `Topic`:
Config class: `KafkaTopicConfig`
- Spring Boot’s KafkaAdmin creates these topics at startup. Your Kafka setup has only one broker, so replicas(1) is the maximum.
```
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String CHEF_NOTIFICATION_TOPIC = "chef-notification";
    public static final String CUSTOMER_NOTIFICATION_TOPIC = "customer-notification";

    @Bean
    public NewTopic chefNotificationTopic() {
        return TopicBuilder.name(CHEF_NOTIFICATION_TOPIC).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic customerNotificationTopic() {
        return TopicBuilder.name(CUSTOMER_NOTIFICATION_TOPIC).partitions(1).replicas(1).build();
    }
}
```

#### C - `Producer`:
`public class PurchaseNotificationProducer` with `@Service` annotation
```
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.example.purchase.kafka.KafkaTopicConfig.CHEF_NOTIFICATION_TOPIC;
import static org.example.purchase.kafka.KafkaTopicConfig.CUSTOMER_NOTIFICATION_TOPIC;

@Service
public class PurchaseNotificationProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PurchaseNotificationProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notifyChef(Long chefId, String message) {
        kafkaTemplate.send(CHEF_NOTIFICATION_TOPIC, String.valueOf(chefId),
                new ChefNotificationEvent(chefId, message));
    }

    public void notifyCustomer(Long customerId, String message) {
        kafkaTemplate.send(CUSTOMER_NOTIFICATION_TOPIC, String.valueOf(customerId),
                new CustomerNotificationEvent(customerId, message));
    }
}
```

#### D - `OrchestrationService` implementation: 
inject the producer:
- The notifications are sent after create(), so only purchases that were actually saved trigger a message.
```
import org.example.purchase.clients.ChefClient;
import org.example.purchase.clients.ProductListingClient;
import org.example.purchase.dtos.ChefDto;
import org.example.purchase.dtos.ListingInfoDto;
import org.example.purchase.dtos.ProductListingDto;
import org.example.purchase.dtos.RecipeDto;
import org.example.purchase.event.PurchaseEvent;
import org.example.purchase.event.PurchaseEventDto;
import org.springframework.stereotype.Service;
+ import org.example.purchase.kafka.PurchaseNotificationProducer;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrchestrationService {
    private final ProductListingClient productListingClient;
    private final ChefClient chefClient;
    private final PurchaseService purchaseService;

-    public PurchaseOrchestrationService(ProductListingClient productListingClient, ChefClient chefClient, PurchaseService purchaseService) {
+    public PurchaseOrchestrationService(ProductListingClient productListingClient, ChefClient chefClient,
+                                        PurchaseService purchaseService, PurchaseNotificationProducer notificationProducer) {
        this.productListingClient = productListingClient;
        this.chefClient = chefClient;
        this.purchaseService = purchaseService;
+       this.notificationProducer = notificationProducer;
    }

    public Purchase createPurchase(PurchaseDto purchaseDto) {
        Purchase purchase = new Purchase();
        List<PurchaseEventDto> purchaseEventsDto = purchaseDto.purchaseEvents();
        List<PurchaseEvent> purchaseEvents = new ArrayList<>();

        //For calculating totalPrice
        float totalPrice = 0;

        //For each purchaseEventsDto in purchaseEventDto loop
        for (PurchaseEventDto purchaseEventDto : purchaseEventsDto) {
            ProductListingDto productListingDto = productListingClient.getProductListingInfoById(purchaseEventDto.productListingId());
            RecipeDto recipeDto = productListingClient.getRecipeById(productListingDto.recipeId());
            //FIX WHEN CHEF MICROSERVICE IS UP
            ChefDto chefDto = chefClient.getChefInfoById(productListingDto.chefId());

            //Info about the dish listing
            ListingInfoDto listingInfoDto = new ListingInfoDto(
                    productListingDto.chefId(),
                    chefDto.name(),
                    purchaseEventDto.productListingId(),
                    recipeDto.recipeName(),
                    productListingDto.unitPrice()
            );

            //Creating a purchase event and setting values
            PurchaseEvent purchaseEvent = new PurchaseEvent();
            purchaseEvent.setListingInfoDto(listingInfoDto);
            purchaseEvent.setPurchase(purchase);
            purchaseEvent.setQuantity(purchaseEventDto.quantity());
            purchaseEvents.add(purchaseEvent);
            totalPrice += productListingDto.unitPrice() * purchaseEventDto.quantity();
        }
        //Setting the values of the Purchase
        purchase.setPurchaseEvent(purchaseEvents);
        purchase.setTotalPrice(totalPrice);
        purchase.setCustomerId(purchaseDto.customerId());

        Purchase savedPurchase = purchaseService.create(purchase);
+       sendNotifications(savedPurchase);
        return savedPurchase;
    }
    
+   private void sendNotifications(Purchase purchase) {
+       List<String> recipeNames = new ArrayList<>();
+
+       for (PurchaseEvent event : purchase.getPurchaseEvent()) {
+           ListingInfoDto info = event.getListingInfoDto();
+           recipeNames.add(info.recipeName());
+           notificationProducer.notifyChef(
+                   info.cookId(),
+                   "New purchase #" + purchase.getId() + ": " + event.getQuantity() + " x " + info.recipeName()
+           );
+       }
+
+       notificationProducer.notifyCustomer(
+               (long) purchase.getCustomerId(),
+               "Your purchase #" + purchase.getId() + " went through: " + String.join(", ", recipeNames)
+       );
+   }
}
```

## 3 - Consumers (Listeners)
### 3.1 - `application.yml` for both `Customer` and `Chef` services:
- `value.default.type` tells the deserializer to read the JSON into your own DTO. This works because there are no type headers.
- `ErrorHandlingDeserializer` wraps the JSON deserializer. If an invalid message arrives (a “poison pill”), it’s logged and skipped instead of making the consumer crash in a loop. 
```
spring:
  # ... existing config ...
  kafka:
    bootstrap-servers: kafka:9093
    consumer:
      group-id: chefuser
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
      properties:
        "[spring.deserializer.value.delegate.class]": org.springframework.kafka.support.serializer.JacksonJsonDeserializer
        "[spring.json.value.default.type]": user.chefuser.chef.dtos.ChefNotificationCreationDto
        "[spring.json.trusted.packages]": user.chefuser.chef.dtos
```
### 3.2 - New files for consumers/Listeners: **(Not in kafka directory)**
#### A - `ChefNotificationListener`
```
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import user.chefuser.chef.dtos.ChefNotificationCreationDto;

@Slf4j
@Component
public class ChefNotificationListener {
    private final ChefOrchestrationService chefOrchestrationService;

    public ChefNotificationListener(ChefOrchestrationService chefOrchestrationService) {
        this.chefOrchestrationService = chefOrchestrationService;
    }

    @KafkaListener(topics = "chef-notification")
    public void onChefNotification(ChefNotificationCreationDto dto) {
        log.info("Received chef notification for chefId {}", dto.chefId());
        chefOrchestrationService.createChefNotification(dto);
    }
}
```
#### B - `CustomerNotificationListener`
```
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import user.customer.dto.CustomerNotificationCreationDto;

@Slf4j
@Component
public class CustomerNotificationListener {
    private final CustomerOrchestrationService customerOrchestrationService;

    public CustomerNotificationListener(CustomerOrchestrationService customerOrchestrationService) {
        this.customerOrchestrationService = customerOrchestrationService;
    }

    @KafkaListener(topics = "customer-notification")
    public void onCustomerNotification(CustomerNotificationCreationDto dto) {
        log.info("Received customer notification for customerId {}", dto.customerId());
        customerOrchestrationService.createCustomerNotification(dto);
    }
}
```
## 4 - addition in `docker-compose.yml` files
Make `producer`'s and `listener`'s wait on Kafka service to be healthy before starting, 
so they don't try to connect before the broker I up.
```
    depends_on:
      kafka:
        condition: service_healthy
      # ... the existing ones ...
```

## 5 - Manual testing and pitfalls
- **Test it:** `POST /api/purchase` → open KafBat UI at `http://localhost:8080` and check that both topics have messages → `GET /api/chef/notification/chefId/{id}` and `GET /api/customer/notification/customerId/{id}`.
- **Message length:** `customer_notification.message` is `VARCHAR(100)`. A purchase with several dishes can easily exceed that, and the insert will fail in the listener. Increase it to 255 in the new migration, or validate the length.
- **Errors in the listener:** if `createChefNotification` throws (for example `ChefNotFoundException` after point 6), Spring Kafka's default error handler retries a few times, then logs the error and skips the message. That's fine for now. The next step would be a dead-letter topic, which is a good thing to mention in the exam.
- **Running outside Docker:** if you start a service from IntelliJ instead of in a container, use `localhost:9092` (the `HOST` listener) instead of `kafka:9093`.
- **What this simple version doesn't handle:** if Kafka is down right after the purchase is saved, the message is lost. The standard fix is the *transactional outbox* pattern: save the event in the same database transaction and publish it afterwards. That's overkill here, but it's good to show you know about it.