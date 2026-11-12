package org.automation.initdata.client;

import org.automation.initdata.dtos.CreateProductListingDTO;
import org.automation.initdata.dtos.CreateRecipeDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ListingClient {
    private final String url = "http://gateway:8100/api/productListing";
    private final RestClient restClient;

    public ListingClient() {
        this.restClient = RestClient.create();
    }

    public void createListing(CreateProductListingDTO listingDTO) {
        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(listingDTO)
                .retrieve()
                .toBodilessEntity();
    }

    public void createUnactiveListing(CreateProductListingDTO listingDTO) {
        restClient.post()
                .uri(url + "/initdata")
                .contentType(MediaType.APPLICATION_JSON)
                .body(listingDTO)
                .retrieve()
                .toBodilessEntity();
    }
}
