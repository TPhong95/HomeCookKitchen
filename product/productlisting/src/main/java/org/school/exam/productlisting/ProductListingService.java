package org.school.exam.productlisting;

import lombok.AllArgsConstructor;
import org.school.exam.productlisting.dto.CreateProductListingDTO;
import org.school.exam.productlisting.exception.badrequest.BadRequestException;
import org.school.exam.productlisting.exception.notfound.ProductListingNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductListingService {
    private final ProductListingRepo productListingRepo;
    //private final RecipeService recipeService;

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

//    public List<ProductListing> findAllByChefId(@PathVariable int chefId) {
//        List<ProductListing> chefsListings = ;
//    }

    public ProductListing create(ProductListing productListing) {
        return productListingRepo.save(productListing);
    }

    public ProductListing update(Long id, CreateProductListingDTO DTO) {
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
