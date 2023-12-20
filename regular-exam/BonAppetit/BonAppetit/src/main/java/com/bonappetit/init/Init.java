package com.bonappetit.init;

import com.bonappetit.service.CategoryService;
import com.bonappetit.service.RecipeService;
import com.bonappetit.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
    private final UserService userService;

    private final RecipeService recipeService;

    private final CategoryService categoryService;

    public Init(UserService userService, RecipeService recipeService, CategoryService categoryService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.initAdmin();
        this.userService.initTestUser();
        this.categoryService.initCategories();
        this.recipeService.initRecipes();
    }
}
