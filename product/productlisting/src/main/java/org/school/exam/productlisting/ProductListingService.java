package org.school.exam.productlisting;

import lombok.AllArgsConstructor;
import org.school.exam.productlisting.dto.CreateProductListingDTO;
import org.school.exam.productlisting.dto.ProductListingDeadlineDTO;
import org.school.exam.productlisting.exception.BadRequestException.BadRequestException;
import org.school.exam.productlisting.exception.NotFoundException.ProductListingNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductListingService {
    private final ProductListingRepo productListingRepo;
    //private final RecipeService recipeService;

    public List<ProductListing> findAll() {
        var productListings = productListingRepo.findAll();
        if (productListings.isEmpty()) {
            throw new ProductListingNotFoundException("No product listing(s) found", Map.of());
        }
        return productListings;
    }

    public ProductListing findById(Long id) {
        ProductListing productListing = productListingRepo.findById(id).orElse(null);
        if (productListing == null) {
            throw new ProductListingNotFoundException("No product listing with id " + id + " found", Map.of());
        }
        return productListing;
    }

//    public List<ProductListing> findAllByChefId(@PathVariable int chefId) {
//        List<ProductListing> chefsListings = ;
//    }

    public ProductListing create(ProductListing productListing) {
        return productListingRepo.save(productListing);
    }

    /*
    public ProductListing update(Long id, CreateProductListingDTO DTO) {
        ProductListing productListing = productListingRepo.findById(id).orElse(null);
        if (productListing == null) {
            throw new ProductListingNotFoundException("No product listing with id " + id + " found", Map.of());
        }
        if (DTO == null){
            throw new BadRequestException("Cannot update a product listing without receiving information", Map.of());
        }
        if (DTO.quantityOffered() != productListing.getQuantityOffered()) {
        productListing.setQuantityOffered(DTO.quantityOffered());
        }
        if (DTO.unitPrice() != productListing.getUnitPrice()) {
        productListing.setUnitPrice(DTO.unitPrice());
        }
        return productListingRepo.save(productListing);
    }

     */

    public List<ProductListingDeadlineDTO> findAllPassedDeadlines() {
        var productListings = productListingRepo.findActiveWithPurchaseDeadlineAfter(LocalDateTime.now());
        if (productListings.isEmpty()) {
            throw new ProductListingNotFoundException("No product listing(s) found", Map.of());
        }
        return productListings.stream()
                .map(pl -> new ProductListingDeadlineDTO(
                        pl.getProductListingId().longValue(),
                        pl.getActive(),
                        pl.getPurchaseDeadline()))
                .toList();
    }

    public ProductListingDeadlineDTO updateDeadline(Long id) {
        ProductListing productListing = productListingRepo.findById(id).orElse(null);
        if (productListing == null) {
            throw new ProductListingNotFoundException("No product listing with id " + id + " found", Map.of());
        }
        productListing.setActive(false);
        productListingRepo.save(productListing);

        return new ProductListingDeadlineDTO(productListing.getProductListingId().longValue(), productListing.getActive(), productListing.getPurchaseDeadline());
    }
}
