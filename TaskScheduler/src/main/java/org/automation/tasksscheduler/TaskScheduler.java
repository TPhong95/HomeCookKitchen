package org.automation.tasksscheduler;

import lombok.extern.slf4j.Slf4j;
import org.automation.tasksscheduler.clients.ListingClient;
import org.automation.tasksscheduler.dto.ListingDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskScheduler {
    private final ListingClient listingClient;

    public TaskScheduler(ListingClient listingClient) {
        this.listingClient = listingClient;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void getActiveListingsWithPassedDeadline() {
        try {
            List<ListingDto> listings = listingClient.getListings();

            if (listings.isEmpty()) {
                System.out.println("No items to process");
                return;
            }
            
            log.info("Found " + listings);

            for (ListingDto listing : listings) {
                if (listing.active()) {
                    listingClient.updateListingDeadline(listing);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


