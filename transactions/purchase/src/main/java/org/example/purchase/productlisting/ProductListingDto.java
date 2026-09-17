package org.example.purchase.productlisting;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductListingDto(
        int productListingId,
        String name,
        float unitPrice

) {
}
