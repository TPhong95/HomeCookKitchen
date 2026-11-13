package org.automation.tasksscheduler.clients;

import org.automation.tasksscheduler.dto.ListingDto;
import org.automation.tasksscheduler.dto.PurchasedListingDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PurchaseClient {
    private final String url;
    private final RestClient restClient;

    public PurchaseClient(
    ) {
        this.url = "http://gateway:8100/api/"; //The call to the controller in the other microservice
        this.restClient = RestClient.create();
    }

    //Example functions using get from another service and receiving with a dto
    public List<PurchasedListingDto> getListings(Long productListingId) {
        String callUrl = this.url + "purchase/" + productListingId + "/taskscheduler";
        return restClient
                .get()
                .uri(callUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<List<PurchasedListingDto>>() {});
    }

}
