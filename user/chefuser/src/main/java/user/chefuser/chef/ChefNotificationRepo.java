package user.chefuser.chef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefNotificationRepo extends JpaRepository<ChefNotification, Long> {
    List<ChefNotification> getChefNotificationByChefId(Long chefId);
}
