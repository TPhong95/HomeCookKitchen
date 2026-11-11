package org.school.exam.productlisting;

import lombok.Getter;
import lombok.Setter;
import org.school.exam.productlisting.client.ChefClient;
import org.school.exam.productlisting.client.RecipeClient;
import org.school.exam.productlisting.dto.ChefAddressDTO;
import org.school.exam.productlisting.dto.ChefResponseDTO;

import org.school.exam.productlisting.dto.CreateProductListingDTO;
import org.school.exam.productlisting.dto.GetRecipeDTO;
import org.school.exam.productlisting.exception.BadRequestException.BadRequestException;
import org.school.exam.productlisting.exception.ConflictException.ConflictException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@Getter
@Setter
public class ProductListingOrchestrationService {
    private ProductListingService productListingService;
    private RecipeClient recipeClient;
    private ChefClient chefClient;

    public ProductListingOrchestrationService(ProductListingService productListingService, RecipeClient recipeClient, ChefClient chefClient) {
        this.productListingService = productListingService;
        this.recipeClient = recipeClient;
        this.chefClient = chefClient;
    }

    public ProductListing createProductListing(CreateProductListingDTO DTO) {
        validateCreateDTO(DTO);

        GetRecipeDTO recipe = recipeClient.getRecipeById(DTO.recipeId()); //Validates existence of recipe
        if (!Objects.equals(DTO.chefId(), recipe.cookId())) {
            throw new BadRequestException("Recipe does not belong to this chef", Map.of());
        }
        if (!recipe.active()) {
            throw new BadRequestException("Recipe is not active", Map.of());
        }

        ChefResponseDTO chef = chefClient.getChefById(DTO.chefId());
        ChefAddressDTO address = chef.addresses().stream()
                .filter(a -> Objects.equals(a.id(), DTO.addressId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        "Address not found or not active for this chef", Map.of()));

        String chefCompositeAddress = address.street() + ", " + address.postalCode() + " " + address.city();

        ProductListing productListing = new ProductListing(
                null, DTO.recipeId(), recipe.recipeName(),
                recipe.pictureUrl(), recipe.description(),
                DTO.addressId(), chefCompositeAddress, DTO.chefId(), chef.name(),
                true, DTO.quantityOffered(), 0, DTO.unitPrice(), LocalDateTime.now(),
                DTO.purchaseDeadline(), DTO.readyBy()
        );

        return productListingService.create(productListing);
    }

    public void deleteProductListingById(Long id) {
        ProductListing pl = productListingService.findById(id);
        if(pl.getQuantityPurchased() > 0) throw new ConflictException("You cannot a delete a listing that has purchases", Map.of());
        productListingService.delete(pl);
    }

    private void validateCreateDTO(CreateProductListingDTO DTO) {
        if (DTO.recipeId() == null || DTO.recipeId() <= 0){
            throw new BadRequestException("Recipe ID must be set and cannot be zero", Map.of());
        } else if (DTO.chefId() == null) {
            throw new BadRequestException("Chef ID must be set", Map.of());
        } else if (DTO.addressId() == null) {
            throw new BadRequestException("Address must be selected", Map.of());
        } else if (DTO.quantityOffered() <= 0) {
            throw new BadRequestException("Quantity must be set/cannot be zero or less", Map.of());
        } else if (DTO.unitPrice() <= 0){
            throw new BadRequestException("Unit price must be set/cannot be zero or less", Map.of());
        } else if (DTO.unitPrice() > 5000){
            throw new BadRequestException("Unit price cannot be greater than 5000", Map.of());
        } else if (DTO.purchaseDeadline() == null
                || DTO.purchaseDeadline().isBefore(LocalDateTime.now().plusMinutes(1))) {
            throw new BadRequestException(
                    "Purchase deadline must be set, and must be minimum 1 minute after time of publication of listing"
                    , Map.of());
        } else if (DTO.readyBy() == null
                || DTO.readyBy().isBefore(LocalDateTime.now().plusMinutes(1))) {
            throw new BadRequestException(
                    "The dish cannot be ready for consumption earlier than 1 minute after time of publication of listing, and must be set"
                    , Map.of());
        } else if (DTO.readyBy().isBefore(DTO.purchaseDeadline())) {
            throw new BadRequestException("Ready by cannot be before the purchase deadline", Map.of());
        }
    }
}
