package org.school.exam.productlisting;

import org.school.exam.productlisting.dto.CreateProductListingDTO;
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
    public ResponseEntity<ProductListing> getProductListingById(@PathVariable int id) {
        Long longId = (long) id;
        return ResponseEntity.ok(productListingService.findById(longId));
    }

//    @GetMapping("/chef/{chefId}")
//    public ResponseEntity
//            <List<ProductListing>> getProductListingByChefId(@PathVariable int chefId) {
//        Long longId = (long) chefId;
//        return ResponseEntity.ok(productListingService.findAllByChefId(chefId));
//    }

    @PostMapping
    public ResponseEntity<ProductListing> createProductListing(@RequestBody CreateProductListingDTO DTO) {
        return ResponseEntity.ok(productListingOrchestrationService.createProductListing(DTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductListing> updateProductListing(@PathVariable int id, @RequestBody CreateProductListingDTO DTO) {
        Long longId = (long) id;
        return ResponseEntity.ok(productListingService.update(longId, DTO));
    }
}
