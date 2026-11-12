package org.school.exam.productlisting.dto;

import java.util.List;

public record ChefResponseDTO(
        Long id,
        String name,
        List<ChefAddressDTO> addresses
) {
}
