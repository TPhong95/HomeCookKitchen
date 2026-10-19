package org.example.purchase.clients;

import org.example.purchase.dtos.ChefDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ChefClient {
    private final String url;
    private final RestClient restClient;

    public ChefClient(
    ) {
        this.url = "http://localhost:8006/api/"; //The call to the controller in the other microservice
        this.restClient = RestClient.create();
    }

    //Example functions using get from another service and receiving with a dto
    public ChefDto getChefInfoById(Long id) {
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