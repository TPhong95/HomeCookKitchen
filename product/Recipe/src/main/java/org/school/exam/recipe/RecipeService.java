package org.school.exam.recipe;

import lombok.AllArgsConstructor;
import org.school.exam.recipe.dto.CreateRecipeDTO;
import org.school.exam.recipe.exception.BadRequestException.BadRequestException;
import org.school.exam.recipe.exception.NotFoundException.RecipeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepo recipeRepo;

    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = recipeRepo.findAll();
        if (recipes.isEmpty()){
            throw new RecipeNotFoundException("No recipe(s) found", Map.of());
        }
        return recipes;
    }

    public Recipe getRecipeById(Long id){
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe == null){
            throw new RecipeNotFoundException("No recipe with id " + id + " found", Map.of());
        }
        return recipe;
    }

    public Recipe createRecipe(CreateRecipeDTO DTO){
        if (DTO.cookId() == 0){
            throw new BadRequestException("Cook id is required and cannot be zero", Map.of());
        } else if (DTO.name() == null || DTO.name().isEmpty()){
            throw new BadRequestException("Name is required", Map.of());
        }
        Recipe recipe =
                new Recipe(
                        DTO.cookId(), DTO.name(), DTO.active(), DTO.pictureId(),
                        DTO.instanceOf(), DTO.foodCategory(), DTO.gluten(), DTO.crustaceans(),
                        DTO.egg(), DTO.fish(), DTO.peanuts(), DTO.soybeans(), DTO.milk(),
                        DTO.nuts(), DTO.celery(), DTO.mustard(), DTO.sesame(), DTO.sulfites(),
                        DTO.lupin(), DTO.molluscs()
                );
        return recipeRepo.save(recipe);
    }

    public Recipe updateRecipe(Long id, CreateRecipeDTO DTO){
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe == null){
            throw new RecipeNotFoundException("No recipe with id " + id + " found", Map.of());
        }
        if (!DTO.name().equals(recipe.getRecipeName())){
            recipe.setRecipeName(DTO.name());
        }
        if (!(DTO.cookId() == recipe.getCookId())){
            recipe.setCookId(DTO.cookId());
        }
        return recipeRepo.save(recipe);
    }
}
