package org.example.purchase.purchase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseOrchestrationService purchaseOrchestrationService;

    public PurchaseController(PurchaseService purchaseService, PurchaseOrchestrationService purchaseOrchestrationService) {
        this.purchaseService = purchaseService;
        this.purchaseOrchestrationService = purchaseOrchestrationService;
    }

    @GetMapping()
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable int id) {
        Purchase result = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> result = purchaseService.getAllPurchases();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<Purchase> savePurchase(@RequestBody PurchaseDto purchaseDto) {
        Purchase purchase = purchaseOrchestrationService.createPurchase(purchaseDto);
        return ResponseEntity.ok(purchase);
    }

}
