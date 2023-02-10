package com.company.shop;

public enum FoodSubCategory {
    FRUIT ("Фрукты"),
    VEGETABLE ("Овощи"),
    SWEET ("Сладости"),
    MEET ("Мясо"),
    BREAD ("Хлеб");

    private final String annotation;

    FoodSubCategory(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }
}
