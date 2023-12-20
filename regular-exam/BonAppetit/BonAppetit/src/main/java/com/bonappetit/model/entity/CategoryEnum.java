package com.bonappetit.model.entity;

public enum CategoryEnum {
    MAIN_DISH("Main dish"),
    DESSERT("Dessert"),
    COCKTAIL("Cocktail");

    private final String value;

    private CategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
