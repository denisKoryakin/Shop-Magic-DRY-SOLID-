package com.company;

import com.company.shop.Shop;

import java.util.List;
import java.util.Scanner;

public class Filter {

    Shop shop = Shop.getInstance();
    Scanner scanner = new Scanner(System.in);

    public void printFields(List<String> fields) {
        for (int i = 0; i < fields.size(); i++) {
            System.out.println((i + 1) + " - " + fields.get(i));
        }
    }

    public void filterByPrice() {
        String price = scanner.next();
        shop.getProducts().stream()
                .filter(elem -> elem.getPrice() <= Float.parseFloat(price))
                .forEach(System.out::println);
    }

    public void filterByCategory(List<String> fields) {
        String enter = scanner.next();
        shop.getProducts().stream()
                .filter(elem -> elem.getCategory().getAnnotation().equals(fields.get(Integer.parseInt(enter) - 1)))
                .forEach(System.out::println);
    }

    public void filterByMaker(List<String> fields) {
        String maker = scanner.next();
        shop.getProducts().stream()
                .filter(elem -> elem.getMaker().equals(fields.get(Integer.parseInt(maker) - 1)))
                .forEach(System.out::println);
    }
}
