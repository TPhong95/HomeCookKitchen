package org.school.exam.productlisting.dto;

import java.time.LocalDateTime;

public record ProductListingDeadlineDTO(
        Long id,
        Boolean active,
        LocalDateTime purchaseDeadline
) {
}
