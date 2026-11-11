package org.school.exam.productlisting;

import org.school.exam.productlisting.dto.CreateProductListingDTO;
import org.school.exam.productlisting.dto.ProductListingDeadlineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productListing")
public class ProductListingController {

    private final ProductListingService productListingService;
    private final ProductListingOrchestrationService productListingOrchestrationService;

    public ProductListingController(ProductListingService productListingService, ProductListingOrchestrationService productListingOrchestrationService) {
        this.productListingService = productListingService;
        this.productListingOrchestrationService = productListingOrchestrationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductListing>> getAllProductListings() {
        return ResponseEntity.ok(productListingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductListing> getProductListingById(@PathVariable Long id) {
        return ResponseEntity.ok(productListingService.findById(id));
    }

//    @GetMapping("/chef/{chefId}") //Get active listings by chefId (to view all active listing from same chef)
//    public ResponseEntity
//            <List<ProductListing>> getProductListingByChefId(@PathVariable Long chefId) {
//        return ResponseEntity.ok(productListingService.findAllByChefId(chefId));
//    }

    @PostMapping
    public ResponseEntity<ProductListing> createProductListing(@RequestBody CreateProductListingDTO DTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productListingOrchestrationService.createProductListing(DTO));
    }

    /*
    @PatchMapping("/{id}")
    public ResponseEntity<ProductListing> updateProductListing(@PathVariable Long id, @RequestBody CreateProductListingDTO DTO) {
        return ResponseEntity.ok(productListingService.update(id, DTO));
    }

     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductListing(@PathVariable Long id) {
        productListingOrchestrationService.deleteProductListingById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deadline")
    public ResponseEntity<List<ProductListingDeadlineDTO>> getAllPassedDeadlines() {
        return ResponseEntity.ok(productListingService.findAllPassedDeadlines());
    }

    @PatchMapping("/deadline/{id}")
    public ResponseEntity<ProductListingDeadlineDTO> updateProductListingActive(@PathVariable Long id) {
        return ResponseEntity.ok(productListingService.updateDeadline(id));
    }
}
