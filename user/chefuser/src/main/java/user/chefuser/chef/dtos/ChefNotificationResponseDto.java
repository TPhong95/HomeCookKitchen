package user.chefuser.chef.dtos;

import java.time.LocalDateTime;

public record ChefNotificationResponseDto(
        Long id,
        Long chefId,
        String message,
        LocalDateTime time,
        Boolean isRead
) {

}
