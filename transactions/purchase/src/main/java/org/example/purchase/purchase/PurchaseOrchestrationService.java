package org.example.purchase.purchase;

import org.example.purchase.clients.ChefClient;
import org.example.purchase.clients.CustomerClient;
import org.example.purchase.clients.ProductListingClient;
import org.example.purchase.dtos.*;
import org.example.purchase.event.PurchaseEvent;
import org.example.purchase.event.PurchaseEventDto;

import org.example.purchase.kafka.PurchaseNotificationProducer;

import org.example.purchase.event.PurchaseEventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrchestrationService {
    private final ProductListingClient productListingClient;
    private final ChefClient chefClient;
    private final PurchaseService purchaseService;
    private final PurchaseNotificationProducer notificationProducer;
    private final PurchaseEventService purchaseEventService;
    private final CustomerClient customerClient;


    public PurchaseOrchestrationService(ProductListingClient productListingClient, ChefClient chefClient, PurchaseService purchaseService, PurchaseNotificationProducer notificationProducer, PurchaseEventService purchaseEventService, CustomerClient customerClient) {
        this.productListingClient = productListingClient;
        this.chefClient = chefClient;
        this.purchaseService = purchaseService;
        this.notificationProducer = notificationProducer;
        this.purchaseEventService = purchaseEventService;
        this.customerClient = customerClient;
    }

    public List<PurchasedListingQuantityDto> getPurchasedQuantitiesByCustomer(Long id) {
        return purchaseEventService.getPurchasedQuantitiesByCustomer(id);
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

            //HAR IKKE ROLLBACK ENDA
            UpdatePurchaseProductListingDTO updateDto = new UpdatePurchaseProductListingDTO(purchaseEventDto.quantity());
            productListingClient.UpdatePurchaseValue(updateDto, purchaseEventDto.productListingId());
        }
        //Setting the values of the Purchase
        purchase.setPurchaseEvent(purchaseEvents);
        purchase.setTotalPrice(totalPrice);
        purchase.setCustomerId(purchaseDto.customerId());

        Purchase savedPurchase = purchaseService.create(purchase);
        return savedPurchase;
    }

    //INITDATA
    public Purchase createOldPurchase(PurchaseDto purchaseDto) {
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

            //HAR IKKE ROLLBACK ENDA
            UpdatePurchaseProductListingDTO updateDto = new UpdatePurchaseProductListingDTO(purchaseEventDto.quantity());
            productListingClient.UpdateOldPurchaseValue(updateDto, purchaseEventDto.productListingId());
        }
        //Setting the values of the Purchase
        purchase.setPurchaseEvent(purchaseEvents);
        purchase.setTotalPrice(totalPrice);
        purchase.setCustomerId(purchaseDto.customerId());

        Purchase savedPurchase = purchaseService.create(purchase);
        sendNotifications(savedPurchase);
        return savedPurchase;
    }

    private void sendNotifications(Purchase purchase) {
        // one message per listing, to each relevant chef
        for (PurchaseEvent event : purchase.getPurchaseEvent()) {
            ListingInfoDto infoDto = event.getListingInfoDto();
            CustomerDto customer = customerClient.getCustomerInfoById(purchase.getCustomerId());
            String chefMessage = "A new purchase has been done on " + event.getListingInfoDto().recipeName() + " for " + event.getQuantity() + " seats. From " + customer.name() + ".";
            notificationProducer.notifyChef(
                    infoDto.cookId(),
                    chefMessage
            );
        }

        // One message to the customer with a confirming message containing all the dishes he/she bought
        /*
        List<String> lines = purchase.getPurchaseEvent().stream()
                .map(event -> {
                    ListingInfoDto infoDto = event.getListingInfoDto();
                    float linePrice = infoDto.unitPrice() * event.getQuantity();
                    return infoDto.recipeName() + " x " + event.getQuantity() + " = " + linePrice;
                }).toList();
         */

        PurchaseEvent purchaseEvent = purchase.getPurchaseEvent().get(0);
        ListingInfoDto listingInfoDto = purchaseEvent.getListingInfoDto();
        String customerMessage = "You have purchased " + listingInfoDto.recipeName() + " with " + purchaseEvent.getQuantity() + " seats.";
        notificationProducer.notifyCustomer(purchase.getCustomerId(), customerMessage);
    }
}
