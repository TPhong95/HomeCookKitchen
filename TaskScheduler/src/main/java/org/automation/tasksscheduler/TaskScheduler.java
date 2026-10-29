package org.automation.tasksscheduler;

import org.automation.tasksscheduler.clients.ListingClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskScheduler {
    private final ListingClient listingClient;

    public TaskScheduler(ListingClient listingClient) {
        this.listingClient = listingClient;
    }

    @Scheduled(fixedRate = 60_000)
    public void getActiveListingsWithPassedDeadline() {

    }
}


