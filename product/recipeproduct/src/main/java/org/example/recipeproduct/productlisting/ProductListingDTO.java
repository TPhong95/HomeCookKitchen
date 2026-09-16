package org.example.recipeproduct.productlisting;

public record ProductListingDTO(
    Long recipeId,
    int quantity,
    float unitPrice
) {
}
