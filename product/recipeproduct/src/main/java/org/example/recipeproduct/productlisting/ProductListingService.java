package org.example.recipeproduct.productlisting;

import lombok.AllArgsConstructor;
import org.example.recipeproduct.exception.BadRequestException;
import org.example.recipeproduct.exception.ProductListingNotFoundException;
import org.example.recipeproduct.recipe.Recipe;
import org.example.recipeproduct.recipe.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductListingService {
    private final ProductListingRepo productListingRepo;
    private final RecipeService recipeService;

    public List<ProductListing> findAll() {
        var productListings = productListingRepo.findAll();
        if (productListings.isEmpty()) {
            throw new ProductListingNotFoundException("No product listing(s) found");
        }
        return productListings;
    }

    public ProductListing findById(Long id) {
        ProductListing productListing = productListingRepo.findById(id).orElse(null);
        if (productListing == null) {
            throw new ProductListingNotFoundException("No product listing with id " + id + " found");
        }
        return productListing;
    }

    public ProductListing createProductListing(ProductListingDTO DTO){
        if(DTO.recipeId() == null || DTO.recipeId().equals(0L)){
            throw new BadRequestException("Recipe ID must be set and cannot be zero");
        } else if (DTO.quantity() == 0) {
            throw new BadRequestException("Quantity must be set/cannot be zero");
        } else if (DTO.unitPrice() == 0){
            throw new BadRequestException("Unit price must be set/cannot be zero");
        } else if (DTO.unitPrice() > 5000){
            throw new BadRequestException("Unit price cannot be greater than 5000");
        }
        Recipe recipe = recipeService.getRecipeById(DTO.recipeId());
        ProductListing productListing = new ProductListing(recipe, DTO.quantity(), DTO.unitPrice());
        return productListingRepo.save(productListing);
    }

    public ProductListing updateProductListing(Long id, ProductListingDTO DTO) {
        ProductListing productListing = productListingRepo.findById(id).orElse(null);
        if (productListing == null) {
            throw new ProductListingNotFoundException("No product listing with id " + id + " found");
        }
        if (DTO == null){
            throw new BadRequestException("Cannot update a product listing without receiving information");
        }
        if (DTO.quantity() != productListing.getQuantity()) {
        productListing.setQuantity(DTO.quantity());
        }
        if (DTO.unitPrice() != productListing.getUnitPrice()) {
        productListing.setUnitPrice(DTO.unitPrice());
        }
        return productListingRepo.save(productListing);
    }
}
