package org.example.purchase.datainit;

import org.example.purchase.event.PurchaseEventDto;
import org.example.purchase.purchase.Purchase;
import org.example.purchase.purchase.PurchaseDto;
import org.example.purchase.purchase.PurchaseOrchestrationService;
import org.example.purchase.purchase.PurchaseRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseDataInitializer implements CommandLineRunner {

    private final PurchaseRepo repo;
    private final PurchaseOrchestrationService orchestrationService;

    public PurchaseDataInitializer(PurchaseRepo repo, PurchaseOrchestrationService orchestrationService) {
        this.repo = repo;
        this.orchestrationService = orchestrationService;
    }


    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {

            List<PurchaseEventDto> purchaseList1 = new ArrayList<>();
            purchaseList1.add(new PurchaseEventDto(1L, 3));
            purchaseList1.add(new PurchaseEventDto(2L, 4));
            PurchaseDto purchase1 = new PurchaseDto(1, purchaseList1);
            Purchase savedPurchase1 = orchestrationService.createPurchase(purchase1);

            List<PurchaseEventDto> purchaseList2 = new ArrayList<>();
            purchaseList2.add(new PurchaseEventDto(3L, 2));
            purchaseList2.add(new PurchaseEventDto(4L, 1));
            PurchaseDto purchase2 = new PurchaseDto(2, purchaseList2);
            Purchase savedPurchase2 = orchestrationService.createPurchase(purchase2);

            List<PurchaseEventDto> purchaseList3 = new ArrayList<>();
            purchaseList3.add(new PurchaseEventDto(5L, 1));
            purchaseList3.add(new PurchaseEventDto(1L, 2));
            PurchaseDto purchase3 = new PurchaseDto(3, purchaseList3);
            Purchase savedPurchase3 = orchestrationService.createPurchase(purchase3);



            System.out.println(">>> " + repo.count() + " purchase were added to the purchase database! <<<");
        }
    }
}
