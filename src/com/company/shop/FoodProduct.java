package com.company.shop;

public class FoodProduct extends Product {
    private final FoodSubCategory subCategory;

    public FoodProduct(float price, FoodSubCategory subCategory, String name, String maker, int count) {
        super(price, Category.FOOD, name, maker, count);
        this.subCategory = subCategory;
    }

    @Override
    public FoodSubCategory getSubCategory() {
        return subCategory;
    }
}
