package com.bonappetit.service;

import com.bonappetit.model.dtos.AddRecipeDTO;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.CategoryEnum;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepo;

    private final UserService userService;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepo;

    public RecipeService(RecipeRepository recipeRepo, UserService userService, CategoryService categoryService, CategoryRepository categoryRepo) {
        this.recipeRepo = recipeRepo;
        this.userService = userService;
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
    }

    public void initRecipes() {
        if (this.recipeRepo.count() != 0) {
            return;
        }

        User admin1 = userService.findUserById(Long.parseLong("1"));
        User test1 = userService.findUserById(Long.parseLong("2"));

        Recipe cakeRecipe = new Recipe();
        cakeRecipe.setName("Cake");
        cakeRecipe.setIngredients("1 Cup sugar, 2 eggs, 1/2 cup milk, 1 tsp baking powder");
        cakeRecipe.setCategory(categoryService.findCategory(CategoryEnum.DESSERT));
        assignRecipeToUser(admin1, cakeRecipe);
//        assignFavouriteRecipeToUser(admin1, cakeRecipe);

        Recipe steakRecipe = new Recipe();
        steakRecipe.setName("Steak");
        steakRecipe.setIngredients("125g fillet steaks, 1 tbsp thyme, 1 tsp paprika");
        steakRecipe.setCategory(categoryService.findCategory(CategoryEnum.MAIN_DISH));
        assignRecipeToUser(admin1, steakRecipe);
//        assignFavouriteRecipeToUser(admin1, steakRecipe);


        Recipe cocktailRecipe = new Recipe();
        cocktailRecipe.setName("Aperol spritz");
        cocktailRecipe.setIngredients("ice, 100ml Aperol, 150ml prosecco, soda");
        cocktailRecipe.setCategory(categoryService.findCategory(CategoryEnum.COCKTAIL));
        assignRecipeToUser(test1, cocktailRecipe);
//        assignFavouriteRecipeToUser(test1, cocktailRecipe);

        userService.saveUser(admin1);
        userService.saveUser(test1);
    }

    private void assignFavouriteRecipeToUser(User user, Recipe recipe) {
        Set<Recipe> likedRecipes = user.getFavouriteRecipes();
        likedRecipes.add(recipe);
        user.setFavouriteRecipes(likedRecipes);

        userService.saveUser(user);
    }

    private void assignRecipeToUser(User user, Recipe recipe) {
        Set<Recipe> assignedRecipes = user.getAddedRecipes();
        assignedRecipes.add(recipe);
        user.setAddedRecipes(assignedRecipes);

        recipe.setAddedBy(user);

        recipeRepo.save(recipe);
    }

    public Set<Recipe> getMainDishesRecipes() {
        Category category = categoryRepo.findByName(CategoryEnum.MAIN_DISH);
        Set<Recipe> recipes = recipeRepo.findAllByCategory(category);

        return recipes;
    }

    public Set<Recipe> getDessertsRecipes() {
        Category category = categoryRepo.findByName(CategoryEnum.DESSERT);
        Set<Recipe> recipes = recipeRepo.findAllByCategory(category);

        return recipes;
    }

    public Set<Recipe> getCocktailsRecipes() {
        Category category = categoryRepo.findByName(CategoryEnum.COCKTAIL);
        Set<Recipe> recipes = recipeRepo.findAllByCategory(category);

        return recipes;
    }

    public void likeRecipeById(Long recipeId, Long userId) {
        Recipe currentRecipe = recipeRepo.findById(recipeId).orElse(null);
        User currentUser = userService.findUserById(userId);
        currentUser.getFavouriteRecipes().add(currentRecipe);
        this.userService.saveUser(currentUser);

    }

    public void addRecipe(AddRecipeDTO dto, Long userId) {
        Recipe recipe = new Recipe();
        Category category = this.categoryService.findCategory(dto.getCategory());
        User userById = userService.findUserById(userId);

        recipe.setName(dto.getName());
        recipe.setIngredients(dto.getIngredients());
        recipe.setCategory(category);
        recipe.setAddedBy(userById);

        Set<Recipe> userByIdAssignedRecipes = userById.getAddedRecipes();
        userByIdAssignedRecipes.add(recipe);
        userById.setAddedRecipes(userByIdAssignedRecipes);

        this.recipeRepo.save(recipe);
        this.userService.saveUser(userById);
    }
}
