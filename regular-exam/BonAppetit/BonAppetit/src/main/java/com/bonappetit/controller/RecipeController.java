package com.bonappetit.controller;

import com.bonappetit.model.dtos.AddRecipeDTO;
import com.bonappetit.service.RecipeService;
import com.bonappetit.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final LoggedUser loggedUser;

    private final RecipeService recipeService;

    public RecipeController(LoggedUser loggedUser, RecipeService recipeService) {
        this.loggedUser = loggedUser;
        this.recipeService = recipeService;
    }

    @GetMapping("add-recipe")
    public String addRecipe() {
        if (!loggedUser.isLogged()) {
            return "redirect:/users/login";
        }

        return "recipe-add";
    }

    @PostMapping("/add-recipe")
    public String addRecipe(@Valid AddRecipeDTO addRecipeDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addRecipeDTO", addRecipeDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addRecipeDTO", result);

            return "redirect:/recipes/add-recipe";
        }

        this.recipeService.addRecipe(addRecipeDTO, this.loggedUser.getId());
        return "redirect:/home";
    }

    @GetMapping("/like-recipe/{id}")
    public String likeRecipe(@PathVariable Long id) {
        recipeService.likeRecipeById(id, loggedUser.getId());
        return "redirect:/home";
    }

    @ModelAttribute
    public AddRecipeDTO initAddRecipeDTO() {
        return new AddRecipeDTO();
    }
}
