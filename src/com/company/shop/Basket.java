package com.company.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private List<Product> products;
    private Map<Product, Integer> basket;

    private Basket() {
        this.products = new ArrayList<>();
        this.basket = new HashMap<>();
    }

    private static Basket instance;

    public static Basket getInstance() {
        if (instance == null) instance = new Basket();
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<Product, Integer> getBasket() {
        return basket;
    }

    public boolean putToBasket(Product product, Integer count) {
        if (basket.containsKey(product)) {
//                находим и увеличиваем количество
            basket.replace(product, (basket.get(product) + count));
        } else {
//                не находим и добавляем
            basket.put(product, count);
//                добавляем в перепись товаров
            products.add(product);
        }
        return true;
    }
}
