package org.school.exam.productlisting;

import lombok.Getter;
import lombok.Setter;
import org.school.exam.productlisting.client.RecipeClient;
import org.school.exam.productlisting.dto.CreateProductListingDTO;
import org.school.exam.productlisting.dto.GetRecipeDTO;
import org.school.exam.productlisting.exception.badrequest.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
public class ProductListingOrchestrationService {
    private ProductListingService productListingService;
    private RecipeClient recipeClient;

    public ProductListingOrchestrationService(ProductListingService productListingService, RecipeClient recipeClient) {
        this.productListingService = productListingService;
        this.recipeClient = recipeClient;
    }

    public ProductListing createProductListing(CreateProductListingDTO DTO) {
        validateCreateDTO(DTO);
        GetRecipeDTO verificationRecipe = recipeClient.getRecipeById(DTO.recipeId()); //Validates existence of recipe

        ProductListing productListing = new ProductListing(
                DTO.recipeId(), DTO.chefId(), DTO.active(),
                DTO.quantity(), DTO.unitPrice(),
                DTO.purchaseDeadline(), DTO.readyBy()
        );

        return productListingService.create(productListing);
    }

    private void validateCreateDTO(CreateProductListingDTO DTO) {
        if (DTO.recipeId() == 0){
            throw new BadRequestException("Recipe ID must be set and cannot be zero");
        } else if (DTO.quantity() <= 0) {
            throw new BadRequestException("Quantity must be set/cannot be zero or less");
        } else if (DTO.unitPrice() <= 0){
            throw new BadRequestException("Unit price must be set/cannot be zero or less");
        } else if (DTO.unitPrice() > 5000){
            throw new BadRequestException("Unit price cannot be greater than 5000");
        } else if (DTO.purchaseDeadline() == null
                || DTO.purchaseDeadline().isBefore(LocalDateTime.now().plusMinutes(1))) {
            throw new BadRequestException(
                    "Purchase deadline must be set, and must be minimum 1 minute after time of publication of listing"
            );
        } else if (DTO.readyBy() == null
                || DTO.readyBy().isBefore(LocalDateTime.now().plusMinutes(1))) {
            throw new BadRequestException(
                    "The dish cannot be ready for consumption earlier than 1 minute after time of publication of listing, and must be set"
            );
        }
    }
}
