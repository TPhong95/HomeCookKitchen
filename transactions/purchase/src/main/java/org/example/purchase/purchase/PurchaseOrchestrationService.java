package org.example.purchase.purchase;

import org.example.purchase.clients.ChefClient;
import org.example.purchase.clients.ProductListingClient;
import org.example.purchase.dtos.ChefDto;
import org.example.purchase.dtos.ListingInfoDto;
import org.example.purchase.dtos.ProductListingDto;
import org.example.purchase.dtos.RecipeDto;
import org.example.purchase.event.PurchaseEvent;
import org.example.purchase.event.PurchaseEventDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrchestrationService {
    private final ProductListingClient productListingClient;
    private final ChefClient chefClient;
    private final PurchaseService purchaseService;

    public PurchaseOrchestrationService(ProductListingClient productListingClient, ChefClient chefClient, PurchaseService purchaseService) {
        this.productListingClient = productListingClient;
        this.chefClient = chefClient;
        this.purchaseService = purchaseService;
    }

    public Purchase createPurchase(PurchaseDto purchaseDto) {
        Purchase purchase = new Purchase();
        List<PurchaseEventDto> purchaseEventsDto = purchaseDto.purchaseEvents();
        List<PurchaseEvent> purchaseEvents = new ArrayList<>();

        //For calculating totalPrice
        float totalPrice = 0;

        //For each purchaseEventsDto in purchaseEventDto loop
        for (PurchaseEventDto purchaseEventDto : purchaseEventsDto) {
            ProductListingDto productListingDto = productListingClient.getProductListingInfoById(purchaseEventDto.productListingId());
            RecipeDto recipeDto = productListingClient.getRecipeById(productListingDto.recipeId());
            //FIX WHEN CHEF MICROSERVICE IS UP
            ChefDto chefDto = chefClient.getChefInfoById(productListingDto.chefId());

            //Info about the dish listing
            ListingInfoDto listingInfoDto = new ListingInfoDto(
                    productListingDto.chefId(),
                    chefDto.name(),
                    purchaseEventDto.productListingId(),
                    recipeDto.recipeName(),
                    productListingDto.unitPrice()
            );

            //Creating a purchase event and setting values
            PurchaseEvent purchaseEvent = new PurchaseEvent();
            purchaseEvent.setListingInfoDto(listingInfoDto);
            purchaseEvent.setPurchase(purchase);
            purchaseEvent.setQuantity(purchaseEventDto.quantity());
            purchaseEvents.add(purchaseEvent);
            totalPrice += productListingDto.unitPrice() * purchaseEventDto.quantity();
        }
        //Setting the values of the Purchase
        purchase.setPurchaseEvent(purchaseEvents);
        purchase.setTotalPrice(totalPrice);
        purchase.setCustomerId(purchaseDto.customerId());

        Purchase savedPurchase = purchaseService.create(purchase);
        return savedPurchase;
    }
}
