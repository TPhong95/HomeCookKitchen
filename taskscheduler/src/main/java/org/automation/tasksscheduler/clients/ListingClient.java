package org.automation.tasksscheduler.clients;

import org.automation.tasksscheduler.dto.ListingDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListingClient {
    private final String url;
    private final RestClient restClient;

    public ListingClient(
    ) {
        this.url = "http://gateway:8100/api/"; //The call to the controller in the other microservice
        this.restClient = RestClient.create();
    }

    //Example functions using get from another service and receiving with a dto
    public List<ListingDto> getListings() {
        String callUrl = this.url + "productListing/deadline";
        return restClient
                .get()
                .uri(callUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ListingDto>>() {});
    }

    public ListingDto updateListingDeadline(ListingDto listingDto) {
        String callUrl = this.url + "productListing/deadline/" + listingDto.id();
        return restClient.patch()
                .uri(callUrl)
                .retrieve()
                .body(ListingDto.class);
    }
}
