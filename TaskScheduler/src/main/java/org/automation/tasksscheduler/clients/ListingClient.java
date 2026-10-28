package org.automation.tasksscheduler.clients;

import org.automation.tasksscheduler.dto.GetListingDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
    public GetListingDto getListings(Long id) {
        String callUrl = this.url + "chef/" + id;
        ChefDto chefDto = restClient
                .get()
                .uri(callUrl)
                .retrieve()
                .body(ChefDto.class);
        return chefDto;
    }
}

/*
    How the ChefDto looks it only receives the "name" variable in the json payload

        public record ChefDto(
                String name
        ) {
        }
*/