package com.company.shop;

import java.util.*;
import java.util.stream.Collectors;

public class Shop {

    private final List<Product> products;
    private Random random = new Random();
    private Map<Product, Integer> storage;

    private Shop() {
        this.products = new ArrayList<>();
        this.storage = new HashMap<>();

        products.add(new FoodProduct(120, FoodSubCategory.FRUIT, "Виноград", "GlobalVillage"));
        products.add(new FoodProduct(180, FoodSubCategory.VEGETABLE, "Огурцы", "GlobalVillage"));
        products.add(new FoodProduct(300, FoodSubCategory.MEET, "Свинина", "УрюпинскАгропром"));
        products.add(new FoodProduct(300, FoodSubCategory.SWEET, "Кекс", "СМАК"));
        products.add(new IndustrialProduct(1900, IndustrialSubCategory.BUILDING, "Пассатижи", "Knipex"));
        products.add(new IndustrialProduct(5000, IndustrialSubCategory.BUILDING, "Нож монтажный", "Knipex"));
        products.add(new IndustrialProduct(600, IndustrialSubCategory.HOUSEHOLD, "Метла", "Ростех"));

        for (Product product : products) {
            storage.put(product, generateCount());
        }
    }

    private static Shop instance;

    public static Shop getInstance() {
        if (instance == null) instance = new Shop();
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<Product, Integer> getStorage() {
        return storage;
    }

    private int generateCount() {
        int storageMaxCapacity = 20;
        int storageMinCapacity = 1;
        return random.nextInt(storageMaxCapacity - storageMinCapacity) + storageMinCapacity;
    }

    public List<String> getCategory() {
        return this.getProducts().stream()
                .map(Product::getCategory)
                .map(Category::getAnnotation)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getMaker() {
        return this.getProducts().stream()
                .map(Product::getMaker)
                .distinct()
                .collect(Collectors.toList());
    }
    /*  при необходимости можно добавть методы для фильтра по другим признакам  */

    public void setCount(Product product, int count) {
        storage.replace(product, count);
    }
}
