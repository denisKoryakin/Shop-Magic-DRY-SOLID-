package com.company.shop;

public class IndustrialProduct extends Product {
    private final IndustrialSubCategory subCategory;

    public IndustrialProduct(float price, IndustrialSubCategory subCategory, String name, String maker, int count) {
        super(price, Category.INDUSTRIAL, name, maker, count);
        this.subCategory = subCategory;
    }

    @Override
    public IndustrialSubCategory getSubCategory() {
        return subCategory;
    }
}
