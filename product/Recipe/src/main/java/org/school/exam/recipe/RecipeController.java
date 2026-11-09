package org.school.exam.recipe;

import org.school.exam.recipe.dto.CreateRecipeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<Recipe>> getRecipesByChef(@PathVariable Long chefId) {
        return ResponseEntity.ok(recipeService.getRecipesByChef(chefId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRecipeDTO DTO) {
        return ResponseEntity.ok(recipeService.createRecipe(DTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody CreateRecipeDTO DTO) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, DTO));
    }
}
