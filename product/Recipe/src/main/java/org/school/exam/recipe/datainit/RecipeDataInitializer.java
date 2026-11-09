package org.school.exam.recipe.datainit;

import org.school.exam.recipe.Recipe;
import org.school.exam.recipe.RecipeRepo;
import org.school.exam.recipe.RecipeService;
import org.school.exam.recipe.dto.CreateRecipeDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.school.exam.recipe.FoodCategory.*;

@Component
public class RecipeDataInitializer implements CommandLineRunner {

    private final RecipeRepo repo;
    private final RecipeService service;

    public RecipeDataInitializer(RecipeRepo repo, RecipeService service) {
        this.repo = repo;
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {

            CreateRecipeDTO recipe1 = new CreateRecipeDTO(1L, "Soup", true, 0L, 0L, ASIAN,
                    false, true, false, true, false, true, false, false, false, false, false, false, false, true);
            Recipe savedRecipe1 = service.createRecipe(recipe1);

            CreateRecipeDTO recipe2 = new CreateRecipeDTO(2L, "Burger", true, 0L, 0L, BURGER,
                    true, true, false, false, false, false, false, false, false, false, false, false, false, false);
            Recipe savedRecipe2 = service.createRecipe(recipe2);

            CreateRecipeDTO recipe3 = new CreateRecipeDTO(3L, "Noodles", true, 0L, 0L, UNDEFINED,
                    false, true, false, true, false, true, false, false, false, false, false, false, false, true);
            Recipe savedRecipe3 = service.createRecipe(recipe3);

            CreateRecipeDTO recipe4 = new CreateRecipeDTO(4L, "Spaghetti", true, 0L, 0L, UNDEFINED,
                    false, true, false, true, false, true, false, false, false, false, false, false, false, true);
            Recipe savedRecipe4 = service.createRecipe(recipe4);

            CreateRecipeDTO recipe5 = new CreateRecipeDTO(1L, "Salad", true, 0L, 0L, UNDEFINED,
                    false, true, false, true, false, true, false, false, false, false, false, false, false, true);
            Recipe savedRecipe5 = service.createRecipe(recipe5);

            System.out.println(">>> " + repo.count() + " recipe were added to the recipe database! <<<");
        }
    }
}
