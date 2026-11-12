package user.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_notification_gen")
    @SequenceGenerator(name = "customer_notification_gen", sequenceName = "customer_notification_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;
    private String message;
    private LocalDateTime time;
    @Column(name = "is_read")
    private Boolean isRead;

    public CustomerNotification(Long customerId, String message) {
        this.customerId = customerId;
        this.message = message;
        this.time = LocalDateTime.now();
        this.isRead = false;
    }
}
