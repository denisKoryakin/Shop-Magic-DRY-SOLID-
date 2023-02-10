package com.company.shop;

public class FoodProduct extends Product {
    private final FoodSubCategory subCategory;

    public FoodProduct(float price, FoodSubCategory subCategory, String name, String maker) {
        super(price, Category.FOOD, name, maker);
        this.subCategory = subCategory;
    }

    @Override
    public FoodSubCategory getSubCategory() {
        return subCategory;
    }
}
