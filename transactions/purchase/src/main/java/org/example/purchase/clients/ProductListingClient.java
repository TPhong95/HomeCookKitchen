package org.example.purchase.clients;

import org.example.purchase.dtos.ProductListingDto;
import org.example.purchase.dtos.RecipeDto;
import org.example.purchase.dtos.UpdatePurchaseProductListingDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductListingClient {
    private final String url;
    private final RestClient restClient;

    public ProductListingClient(
            //@Value("${deck.service.url}") String url
    ) {
        this.url = "http://gateway:8100/api/";

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

    public void UpdatePurchaseValue(UpdatePurchaseProductListingDTO listingDTO, Long productListingId) {
        restClient.patch()
                .uri(url + "productListing/" + productListingId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(listingDTO)
                .retrieve()
                .toBodilessEntity();
    }

    //INITDATA
    public void UpdateOldPurchaseValue(UpdatePurchaseProductListingDTO listingDTO, Long productListingId) {
        restClient.patch()
                .uri(url + "productListing/" + productListingId + "/initdata")
                .contentType(MediaType.APPLICATION_JSON)
                .body(listingDTO)
                .retrieve()
                .toBodilessEntity();
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
