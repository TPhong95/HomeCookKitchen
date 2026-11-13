package user.chefuser.chef.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import user.chefuser.chef.ChefOrchestrationService;
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
