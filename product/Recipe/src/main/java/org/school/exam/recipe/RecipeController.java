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

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        Long longId = (long) id;
        return ResponseEntity.ok(recipeService.getRecipeById(longId));
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRecipeDTO DTO) {
        return ResponseEntity.ok(recipeService.createRecipe(DTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable int id, @RequestBody CreateRecipeDTO DTO) {
        Long longId = (long) id;
        return ResponseEntity.ok(recipeService.updateRecipe(longId, DTO));
    }
}
