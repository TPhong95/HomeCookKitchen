package user.chefuser.chef;

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
public class ChefNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chef_notification_gen")
    @SequenceGenerator(name = "chef_notification_gen", sequenceName = "chef_notification_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "chef_id")
    private Long chefId;
    @Column(columnDefinition = "TEXT")
    private String message;
    private LocalDateTime time;
    @Column(name = "is_read")
    private Boolean isRead;

    public ChefNotification(Long chefId, String message) {
        this.chefId = chefId;
        this.message = message;
        this.time = LocalDateTime.now();
        this.isRead = false;
    }
}
