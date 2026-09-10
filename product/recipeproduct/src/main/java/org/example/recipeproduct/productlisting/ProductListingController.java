package org.example.recipeproduct.productlisting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
