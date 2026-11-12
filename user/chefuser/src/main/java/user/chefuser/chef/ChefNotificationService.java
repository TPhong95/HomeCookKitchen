package user.chefuser.chef;

import org.springframework.stereotype.Service;
import user.chefuser.chef.exception.NotFoundException.NotFoundException;

import java.util.List;
import java.util.Map;

@Service
public class ChefNotificationService {
    private final ChefNotificationRepo chefNotificationRepo;

    public ChefNotificationService(ChefNotificationRepo chefNotificationRepo) {
        this.chefNotificationRepo = chefNotificationRepo;
    }

    public ChefNotification createNotification(ChefNotification notification) {
        return chefNotificationRepo.save(notification);
    }

    public ChefNotification getNotificationById(long id) {
        return chefNotificationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification with id " + id + " not found", Map.of()));
    }

    public List<ChefNotification> getNotificationsByChefId(Long chefId) {
        return chefNotificationRepo.getChefNotificationByChefId(chefId);
    }

    public ChefNotification updateNotification(ChefNotification notification) {
        return chefNotificationRepo.save(notification);
    }
}
