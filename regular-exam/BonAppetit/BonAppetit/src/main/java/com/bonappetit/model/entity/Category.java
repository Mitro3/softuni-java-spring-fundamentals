package com.bonappetit.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Category extends BaseEntity {

    @Column(unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum name;

    @Column
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Recipe> recipes;

    public Category() {
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
