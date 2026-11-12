package org.automation.initdata.client;

import org.automation.initdata.dtos.CreateRecipeDTO;
import org.automation.initdata.dtos.CustomerDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RecipeClient {
    private final String url = "http://gateway:8100/api/recipe";
    private final RestClient restClient;

    public RecipeClient() {
        this.restClient = RestClient.create();
    }

    public void createRecipe(CreateRecipeDTO recipeDTO) {
        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipeDTO)
                .retrieve()
                .toBodilessEntity();
    }


}
