package org.example.purchase.event;

import org.example.purchase.dtos.PurchasedListingQuantityDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseEventService {
    private final PurchaseEventRepo purchaseEventRepo;

    public PurchaseEventService(PurchaseEventRepo purchaseEventRepo) {
        this.purchaseEventRepo = purchaseEventRepo;
    }

    public List<PurchasedListingQuantityDto> getPurchasedQuantitiesByCustomer(Long customerId) {
        return purchaseEventRepo.sumQuantitiesByListingForCustomer(customerId);
    }
}
