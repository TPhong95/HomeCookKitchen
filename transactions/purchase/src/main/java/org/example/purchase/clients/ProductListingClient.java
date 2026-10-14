package org.example.purchase.clients;

import org.example.purchase.dtos.ProductListingDto;
import org.example.purchase.dtos.RecipeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductListingClient {
    private final String url;
    private final RestClient restClient;

    public ProductListingClient(
            //@Value("${deck.service.url}") String url
    ) {
        this.url = "http://localhost:8006/api/";

        this.restClient = RestClient.create();
        //this.url = url;
    }

    public ProductListingDto getProductListingInfoById(Long id) {
        String callUrl = this.url + "productListing/" + id;
        ProductListingDto productListing = restClient
                .get()
                .uri(callUrl)
                .retrieve()
                .body(ProductListingDto.class);
        return productListing;
    }

    public RecipeDto getRecipeById(Long id) {
        String callUrl = this.url + "recipe/" + id;
        RecipeDto recipeDto = restClient
                .get()
                .uri(callUrl)
                .retrieve()
                .body(RecipeDto.class);
        return recipeDto;
    }

}
