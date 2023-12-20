package com.bonappetit.model.dtos;

import com.bonappetit.model.entity.CategoryEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddRecipeDTO {

    @Size(min = 2, max = 40, message = "The name length must be between 2 and 40 characters!")
    @NotNull
    private String name;

    @Size(min = 2, max = 150, message = "The ingredients length must be between 2 and 150 characters!")
    private String ingredients;


    @NotNull(message = "You must select a category!")
    private CategoryEnum category;

    public AddRecipeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
