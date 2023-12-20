package com.bonappetit.service;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public void initCategories() {
        if (this.categoryRepo.count() != 0) {
            return;
        }

        Arrays.stream(CategoryEnum.values())
                .forEach(c -> {
                    Category category = new Category();
                    category.setName(c);
                    switch (c.getValue()) {
                        case "Main dish":
                            category.setDescription("Heart of the meal, substantial and satisfying; main dish delights taste buds.");
                            break;
                        case "Dessert":
                            category.setDescription("Sweet finale, indulgent and delightful; dessert crowns the dining experience with joy.");
                            break;
                        default:
                            category.setDescription("Sip of sophistication, cocktails blend flavors, creating a spirited symphony in every glass.");
                            break;
                    }

                    this.categoryRepo.save(category);
                });
    }

    public Category findCategory(CategoryEnum name) {
        return this.categoryRepo.findByName(name);
    }
}
