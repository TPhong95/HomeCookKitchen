package org.school.exam.productlisting.client;

import org.school.exam.productlisting.dto.GetRecipeDTO;
import org.school.exam.productlisting.exception.BadRequestException.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class RecipeClient {
    private final String url = "http://gateway:8100/api/recipe";
    private final RestClient restClient;

    public RecipeClient() {
        this.restClient = RestClient.create();
    }

    public GetRecipeDTO getRecipeById(Long recipeId) {
        String callUri = url + "/" + recipeId;
        GetRecipeDTO DTO = restClient
                .get()
                .uri(callUri)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new BadRequestException("Recipe with id " + recipeId + " not found", Map.of());
                })
                .body(GetRecipeDTO.class);
        return DTO;
    }
}
