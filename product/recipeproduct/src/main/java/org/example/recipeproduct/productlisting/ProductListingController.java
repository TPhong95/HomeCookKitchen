package org.example.recipeproduct.productlisting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productListing")
public class ProductListingController {

    private final ProductListingService productListingService;

    public ProductListingController(ProductListingService productListingService) {
        this.productListingService = productListingService;
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

    @PostMapping
    public ResponseEntity<ProductListing> createProductListing(@RequestBody ProductListingDTO DTO) {
        return ResponseEntity.ok(productListingService.createProductListing(DTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductListing> updateProductListing(@PathVariable int id, @RequestBody ProductListingDTO DTO) {
        Long longId = (long) id;
        return ResponseEntity.ok(productListingService.updateProductListing(longId, DTO));
    }
}
