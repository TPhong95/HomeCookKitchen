package org.automation.tasksscheduler;

import lombok.extern.slf4j.Slf4j;
import org.automation.tasksscheduler.clients.ListingClient;
import org.automation.tasksscheduler.clients.PurchaseClient;
import org.automation.tasksscheduler.dto.ChefNotificationCreationDto;
import org.automation.tasksscheduler.dto.CustomerNotificationCreationDto;
import org.automation.tasksscheduler.dto.ListingDto;
import org.automation.tasksscheduler.dto.PurchasedListingDto;
import org.automation.tasksscheduler.kafka.KafkaProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class ListingDeadlineScheduler {
    private final ListingClient listingClient;
    private final PurchaseClient purchaseClient;
    private final KafkaProducer kafkaProducer;

    public ListingDeadlineScheduler(ListingClient listingClient, PurchaseClient purchaseClient, KafkaProducer kafkaProducer) {
        this.listingClient = listingClient;
        this.purchaseClient = purchaseClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(cron = "${scheduler.listing-deadline.cron:0 */1 * * * *}")
    public void getActiveListingsWithPassedDeadline() {
        log.atDebug().log("Running Listing deadline check");
        try {
            List<ListingDto> listings = listingClient.getListings();
            log.atDebug()
                    .addKeyValue("listingCount", listings.size())
                    .addKeyValue("listings", listings)
                    .log("Received listings:");
            if (listings.isEmpty()) {
                System.out.println("No items to process");
                return;
            }
            
            log.info("Found " + listings);

            for (ListingDto listing : listings) {
                if (listing.active()) {
                    listingClient.updateListingDeadline(listing);
                }
                sendNotification(listing);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void sendNotification(ListingDto listing) {

        List<PurchasedListingDto> customerList =purchaseClient.getListings(listing.id());

        for (PurchasedListingDto customer : customerList) {
            String customerMessage = "The deadline for " + listing.recipeName() +" with " + customer.quantity() + " amount from " + listing.chefName()
                    + " passed at " + listing.purchaseDeadline().format(DateTimeFormatter.ofPattern("HH:mm EEEE d MMMM yyyy")) + ". "
                    + "You may get your order at the address " + listing.compositeAddress() + " and it's ready by "
                    + listing.readyBy().format(DateTimeFormatter.ofPattern("HH:mm EEEE d MMMM yyyy"));

            kafkaProducer.sendCustomerNotification(new CustomerNotificationCreationDto(customer.customerId(), customerMessage));
        }

        String chefMessage = "The deadline for " + listing.recipeName() + " passed at " + listing.purchaseDeadline().format(DateTimeFormatter.ofPattern("HH:mm EEEE d MMMM yyyy")) + ". "
                + listing.quantityPurchased() + " out of " + listing.quantityOffered() + " has been purchased. Your set estimated time of when it's ready is " +
                listing.readyBy().format(DateTimeFormatter.ofPattern("HH:mm EEEE d MMMM yyyy"));

        kafkaProducer.sendChefNotification(new ChefNotificationCreationDto(listing.chefId(), chefMessage));

    }
}


