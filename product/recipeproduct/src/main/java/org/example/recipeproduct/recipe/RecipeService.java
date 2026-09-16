package org.example.recipeproduct.recipe;

import lombok.AllArgsConstructor;
import org.example.recipeproduct.exception.BadRequestException;
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

    public Recipe createRecipe(RecipeDTO DTO){
        if (DTO.cookId() == null || DTO.cookId().equals(0L)){
            throw new BadRequestException("Cook id is required and cannot be zero");
        } else if (DTO.name() == null || DTO.name().isEmpty()){
            throw new BadRequestException("Name is required");
        }
        Recipe recipe = new Recipe(DTO.cookId(), DTO.name(), DTO.active());
        return recipeRepo.save(recipe);
    }

    public Recipe updateRecipe(Long id, RecipeDTO DTO){
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe == null){
            throw new RecipeNotFoundException("No recipe with id " + id + " found");
        }
        if (!DTO.name().equals(recipe.getRecipeName())){
            recipe.setRecipeName(DTO.name());
        }
        if (!DTO.cookId().equals(recipe.getRecipeId())){
            recipe.setRecipeId(DTO.cookId());
        }
        return recipeRepo.save(recipe);
    }
}
