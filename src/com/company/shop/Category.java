package com.company.shop;

public enum Category {
    INDUSTRIAL("Промтовары"),
    FOOD("Продукты питания");

    private final String annotation;

    Category(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }
}
