package org.example.purchase.purchase;


import org.example.purchase.dtos.PurchasedListingDto;
import org.example.purchase.exception.NotFoundException.PurchaseNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

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

    public Purchase getPurchaseById(int id) {
        return purchaseRepo.findById(id).orElseThrow(() -> new PurchaseNotFoundException("Purchase with id: " + id + " not found", Map.of()));
    }

    public List<PurchasedListingDto> getQuantitiesByProductListingId(Long productListingId) {
        return purchaseRepo.findQuantitiesByProductListingId(productListingId);
    }

    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = purchaseRepo.findAll();
        return purchases;
    }

    public void deletePurchaseById(int id) {
        if (!purchaseRepo.existsById(id)) {
            throw new PurchaseNotFoundException("Purchase with id " + id + " not found", Map.of());
        }

        Purchase purchase = getPurchaseById(id);

        purchaseRepo.deleteById(id);
    }

    public Purchase create(Purchase purchase) {
        Purchase savedPurchase = purchaseRepo.save(purchase);
        return savedPurchase;
    }

}

