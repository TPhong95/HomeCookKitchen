package org.example.purchase.purchase;

import org.example.purchase.exception.notfound.PurchaseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepo purchaseRepo;

    public PurchaseService(PurchaseRepo purchaseRepo) {
        this.purchaseRepo = purchaseRepo;
    }

    public Purchase save(Purchase purchase) {
        Purchase savedPurchase = purchaseRepo.save(purchase);
        return savedPurchase;
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepo.findById(id).orElseThrow(() -> {
           return new PurchaseNotFoundException("Purchase with id: " + id + " not found");
        });
    }

    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = purchaseRepo.findAll();
        return purchases;
    }

    public void deletePurchaseById(Long id) {
        if (!purchaseRepo.existsById(id)) {
            throw new PurchaseNotFoundException("Purchase with id " + id + " not found");
        }

        Purchase purchase = getPurchaseById(id);

        purchaseRepo.deleteById(id);
    }
}

