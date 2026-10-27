package org.school.exam.productlisting.client;

import org.school.exam.productlisting.dto.GetRecipeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RecipeClient {
    private final String url = "http://gateway:8100/api/recipe";
    private final RestClient restClient;

    public RecipeClient() {
        this.restClient = RestClient.create();
    }

    public GetRecipeDTO getRecipeById(int recipeId) {
        String callUri = url + "/" + recipeId;
        GetRecipeDTO DTO = restClient
                .get()
                .uri(callUri)
                .retrieve()
                .body(GetRecipeDTO.class);
        return DTO;
    }
}
