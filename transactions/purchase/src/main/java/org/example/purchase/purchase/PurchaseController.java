package org.example.purchase.purchase;

import org.example.purchase.dtos.PurchasedListingDto;
import org.example.purchase.dtos.PurchasedListingQuantityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseOrchestrationService purchaseOrchestrationService;

    public PurchaseController(PurchaseService purchaseService, PurchaseOrchestrationService purchaseOrchestrationService) {
        this.purchaseService = purchaseService;
        this.purchaseOrchestrationService = purchaseOrchestrationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable int id) {
        Purchase result = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> result = purchaseService.getAllPurchases();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PurchasedListingQuantityDto>> getPurchasesByCustomer(@PathVariable Long customerId) {
        List<PurchasedListingQuantityDto> result = purchaseOrchestrationService.getPurchasedQuantitiesByCustomer(customerId);
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<Purchase> savePurchase(@RequestBody PurchaseDto purchaseDto) {
        Purchase purchase = purchaseOrchestrationService.createPurchase(purchaseDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchase);
    }

    @GetMapping("/{productListingId}/taskscheduler")
    public ResponseEntity<List<PurchasedListingDto>> getByProductListingId(@PathVariable Long productListingId) {
        return ResponseEntity.ok(purchaseService.getQuantitiesByProductListingId(productListingId));
    }

    //INITDATA
    @PostMapping("/initdata")
    public ResponseEntity<Purchase> saveOldPurchase(@RequestBody PurchaseDto purchaseDto) {
        Purchase purchase = purchaseOrchestrationService.createOldPurchase(purchaseDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchase);
    }

}
