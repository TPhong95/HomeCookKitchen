package org.example.purchase.clients;

import org.example.purchase.dtos.ChefDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ChefClient {
    private final String url;
    private final RestClient restClient;

    public ChefClient(
            //@Value("${deck.service.url}") String url
    ) {
        this.url = "http://gateway:8100/api/";

        this.restClient = RestClient.create();
        //this.url = url;
    }

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
