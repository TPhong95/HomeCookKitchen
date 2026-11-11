package org.school.exam.recipe;

import lombok.AllArgsConstructor;
import org.school.exam.recipe.dto.CreateRecipeDTO;
import org.school.exam.recipe.dto.RecipeActiveDTO;
import org.school.exam.recipe.exception.BadRequestException.BadRequestException;
import org.school.exam.recipe.exception.NotFoundException.RecipeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepo recipeRepo;

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
                        DTO.cookId(), DTO.name(), DTO.active(), DTO.pictureUrl(), DTO.description(),
                        DTO.instanceOf(), DTO.foodCategory(), DTO.gluten(), DTO.crustaceans(),
                        DTO.egg(), DTO.fish(), DTO.peanuts(), DTO.soybeans(), DTO.milk(),
                        DTO.nuts(), DTO.celery(), DTO.mustard(), DTO.sesame(), DTO.sulfites(),
                        DTO.lupin(), DTO.molluscs()
                );
        return recipeRepo.save(recipe);
    }

/*
    public Recipe updateRecipe(Long id, CreateRecipeDTO DTO){
        Recipe recipe = recipeRepo.findById(id).orElseThrow(
                () -> new RecipeNotFoundException("No recipe with id " + id + " found", Map.of()));
        if (DTO.name() == null || DTO.name().isEmpty()){
            throw new BadRequestException("Name is required", Map.of());
        }
        recipe.setRecipeName(DTO.name());
        recipe.setActive(DTO.active());
        recipe.setPictureUrl(DTO.pictureUrl());
        recipe.setDescription(DTO.description());
        recipe.setInstanceOf(DTO.instanceOf());
        recipe.setFoodCategory(DTO.foodCategory());
        recipe.setGluten(DTO.gluten());
        recipe.setCrustaceans(DTO.crustaceans());
        recipe.setEgg(DTO.egg());
        recipe.setFish(DTO.fish());
        recipe.setPeanuts(DTO.peanuts());
        recipe.setSoybeans(DTO.soybeans());
        recipe.setMilk(DTO.milk());
        recipe.setNuts(DTO.nuts());
        recipe.setCelery(DTO.celery());
        recipe.setMustard(DTO.mustard());
        recipe.setSesame(DTO.sesame());
        recipe.setSulfites(DTO.sulfites());
        recipe.setLupin(DTO.lupin());
        recipe.setMolluscs(DTO.molluscs());
        return recipeRepo.save(recipe);
    }

 */
    public Recipe updateRecipe(Long id, CreateRecipeDTO DTO){
        Recipe recipe = recipeRepo.findById(id).orElseThrow(
                () -> new RecipeNotFoundException("No recipe with id " + id + " found", Map.of()));
        if (DTO.name() == null || DTO.name().isEmpty()){
            throw new BadRequestException("Name is required", Map.of());
        }
        recipe.setActive(false);
        recipe.setInvalidated(true);

        Recipe newRecipe =
                new Recipe(
                        DTO.cookId(), DTO.name(), DTO.active(), DTO.pictureUrl(), DTO.description(),
                        recipe.getRecipeId(), DTO.foodCategory(), DTO.gluten(), DTO.crustaceans(),
                        DTO.egg(), DTO.fish(), DTO.peanuts(), DTO.soybeans(), DTO.milk(),
                        DTO.nuts(), DTO.celery(), DTO.mustard(), DTO.sesame(), DTO.sulfites(),
                        DTO.lupin(), DTO.molluscs()
                );
        return recipeRepo.save(newRecipe);
    }

    public List<Recipe> getRecipesByChef(Long chefId) {
        return recipeRepo.findByCookId(chefId);
    }

    public List<Recipe> getRecipesByChefWhereNotInvalidated(Long chefId) {
        return recipeRepo.findByCookIdAndInvalidatedFalse(chefId);
    }

    public Recipe updateRecipeActive(Long id, RecipeActiveDTO DTO){
        Recipe recipe = recipeRepo.findById(id).orElseThrow(
                () -> new RecipeNotFoundException("No recipe with id " + id + " found", Map.of()));
        recipe.setActive(DTO.active());
        return recipeRepo.save(recipe);
    }
}

