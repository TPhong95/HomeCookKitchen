package org.example.purchase.purchase;

import org.example.purchase.exception.notfound.PurchaseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Purchase getPurchaseById(int id) {
        return purchaseRepo.findById(id).orElseThrow(() -> {
           return new PurchaseNotFoundException("Purchase with id: " + id + " not found");
        });
    }

    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = purchaseRepo.findAll();
        return purchases;
    }

    public void deletePurchaseById(int id) {
        if (!purchaseRepo.existsById(id)) {
            throw new PurchaseNotFoundException("Purchase with id " + id + " not found");
        }

        Purchase purchase = getPurchaseById(id);

        purchaseRepo.deleteById(id);
    }

    public Purchase create(PurchaseDto purchaseDto) {
        Purchase purchase = new Purchase(purchaseDto.customerId(), purchaseDto.purchaseEvents());

        purchase.setPurchaseEvent(new ArrayList<>());
        purchase.setTotalPrice(0);
        Purchase savedPurchase = purchaseRepo.save(purchase);
        return savedPurchase;
    }

}

