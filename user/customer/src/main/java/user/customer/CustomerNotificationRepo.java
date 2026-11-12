package user.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNotificationRepo extends JpaRepository<CustomerNotification, Long> {
    List<CustomerNotification> getCustomerNotificationByCustomerId(Long customerId);
}
