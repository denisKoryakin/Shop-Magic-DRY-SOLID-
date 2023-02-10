package com.company;

import com.company.shop.Product;
import com.company.shop.Shop;

import java.util.List;
import java.util.Scanner;

public class Filter {

    public void printFields(List<String> fields) {
        for (int i = 0; i < fields.size(); i++) {
            System.out.println((i + 1) + " - " + fields.get(i));
        }
    }

    public void filterByPrice(List<Product> products, String field) {
        products.stream()
                .filter(elem -> elem.getPrice() <= Float.parseFloat(field))
                .forEach(System.out::println);
    }

    public void filterByCategory(List<Product> products, List<String> fields, String category) {
        products.stream()
                .filter(elem -> elem.getCategory().getAnnotation().equals(fields.get(Integer.parseInt(category) - 1)))
                .forEach(System.out::println);
    }

    public void filterByMaker(List<Product> products, List<String> fields, String price) {
        products.stream()
                .filter(elem -> elem.getMaker().equals(fields.get(Integer.parseInt(price) - 1)))
                .forEach(System.out::println);
    }
}
