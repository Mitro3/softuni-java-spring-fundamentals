package com.bonappetit.controller;

import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.service.RecipeService;
import com.bonappetit.service.UserService;
import com.bonappetit.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping(name = "/")
public class HomeController {
    private final LoggedUser loggedUser;
    private final RecipeService recipeService;
    private final UserService userService;

    public HomeController(LoggedUser loggedUser, RecipeService recipeService, UserService userService) {
        this.loggedUser = loggedUser;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping
    public String index() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }

        Set<Recipe> allMainDishes = recipeService.getMainDishesRecipes();
        Set<Recipe> allDesserts = recipeService.getDessertsRecipes();
        Set<Recipe> allCocktails = recipeService.getCocktailsRecipes();

        model.addAttribute("allMainDishes", allMainDishes);
        model.addAttribute("allDesserts", allDesserts);
        model.addAttribute("allCocktails", allCocktails);

        User user = userService.findUserById(loggedUser.getId());
        model.addAttribute("currentUserInfo", user);

        return "home";
    }

}
