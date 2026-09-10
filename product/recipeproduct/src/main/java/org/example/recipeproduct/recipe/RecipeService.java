package org.example.recipeproduct.recipe;

import lombok.AllArgsConstructor;
import org.example.recipeproduct.exception.RecipeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepo recipeRepo;

    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = recipeRepo.findAll();
        if (recipes.isEmpty()){
            throw new RecipeNotFoundException("No recipe(s) found");
        }
        return recipes;
    }

    public Recipe getRecipeById(Long id){
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe == null){
            throw new RecipeNotFoundException("No recipe with id " + id + " found");
        }
        return recipe;
    }
}
