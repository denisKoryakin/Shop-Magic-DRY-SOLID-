package com.company.shop;

public enum IndustrialSubCategory {
    BUILDING ("Стройматериалы"),
    HOUSEHOLD ("Хозтовары");

    private final String annotation;

    IndustrialSubCategory(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }
}
