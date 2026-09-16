package org.example.recipeproduct.recipe;

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

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Long longId = (long) id;
        return ResponseEntity.ok(recipeService.getRecipeById(longId));
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO DTO) {
        return ResponseEntity.ok(recipeService.createRecipe(DTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable int id, @RequestBody RecipeDTO DTO) {
        Long longId = (long) id;
        return ResponseEntity.ok(recipeService.updateRecipe(longId, DTO));
    }
}
