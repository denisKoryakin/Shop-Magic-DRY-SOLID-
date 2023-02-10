package com.company.shop;

import java.util.InputMismatchException;

public abstract class Product {
    private final float price;
    private final Category category;
    private final String name;
    private final String maker;
    private float rating;
    private int scoreCount;

    public Product(float price, Category category, String name, String maker) {
        this.price = price;
        this.category = category;
        this.name = name;
        this.maker = maker;
    }

    public float getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        try {
            while (true) {
                if (rating <= 10) {
                    this.rating = (((float) rating + this.rating) / (scoreCount + 1));
                    scoreCount++;
                    break;
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Рейтинг может выставляться только числом от 0 до 10");
        }
    }

    @Override
    public String toString() {
        return String.format("%15s", name) +
                ", цена: "
                + String.format("%7s", price) +
                ", производитель: "
                + String.format("%17s", maker) +
                ", рейтинг: " + rating;
    }

    public abstract Object getSubCategory();
}
