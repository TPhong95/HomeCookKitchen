package org.school.exam.productlisting;

import lombok.AllArgsConstructor;
import org.school.exam.productlisting.dto.ProductListingDeadlineDTO;
import org.school.exam.productlisting.dto.UpdatePurchaseProductListingDTO;
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
        return productListings;
    }

    public List<ProductListing> findAllForCustomer() {
        return productListingRepo.findActiveForCustomer();
    }

    public List<ProductListing> findChefActiveListings(Long id) {
        return productListingRepo.findChefActiveListings(id);
    }

    public List<ProductListing> findChefHistoricListings(Long id) {
        return productListingRepo.findChefHistoricListings(id);
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

    public ProductListing update(Long id, UpdatePurchaseProductListingDTO DTO) {
        ProductListing productListing = productListingRepo.findById(id).orElse(null);
        if (productListing == null) {
            throw new ProductListingNotFoundException("No product listing with id " + id + " found", Map.of());
        }
        if (DTO == null){
            throw new BadRequestException("Cannot update a product listing without receiving information", Map.of());
        }
        if (productListing.getActive() == false) {
            throw new BadRequestException("Cannot update a product listing without active product", Map.of());
        }
        if (productListing.getQuantityOffered() - productListing.getQuantityPurchased() < DTO.quantityWantToPurchase()) {
            throw new BadRequestException("Cannot update a product listing with insufficient quantity", Map.of());
        }
        productListing.setQuantityPurchased(productListing.getQuantityPurchased() + DTO.quantityWantToPurchase());
        return productListingRepo.save(productListing);
    }

    public void delete(ProductListing productListing) {
        productListingRepo.delete(productListing);
    }

    public List<ProductListingDeadlineDTO> findAllPassedDeadlines() {
        var productListings = productListingRepo.findActiveWithPurchaseDeadlineAfter(LocalDateTime.now());
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
