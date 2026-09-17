package org.example.purchase.purchase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
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
        Purchase purchase = purchaseService.create(purchaseDto);
        return ResponseEntity.ok(purchase);
    }

}
