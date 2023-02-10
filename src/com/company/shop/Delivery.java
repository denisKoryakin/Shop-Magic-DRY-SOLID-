package com.company.shop;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Delivery {

    private List<Product> products;
    Client client = Client.getInstance();


    private static Delivery instance;

    public static Delivery getInstance() {
        if (instance == null) instance = new Delivery();
        return instance;
    }

    public void setProductToDelivery(List<Product> products) throws InterruptedException {
        this.products = products;
        System.out.println("В доставку переданы следующие товары: " + this.products.toString());
        Thread.sleep(3_000);
        deliveryComplete();
    }

    // TODO: 09.02.2023 уточнить по надобности этого метода 
    public void getStatusDelivery() {
        try {
            System.out.println("В настоящий момент в доставке находятся следующие товары: " + this.products.toString());
        } catch (NullPointerException ex) {
            if (products == null) {
                System.out.println("В настоящий момент товаров в доставке нету");
            }
        }
    }

    private void deliveryComplete() {
        client.setProductsToHome(this.products);
        System.out.println("Товар успешно доставлен!");
        System.out.println("""
                Хотите ли выставить рейтинг, полченному товару?\s
                1 - Да\s
                2 - Нет""");
        Scanner scanner = new Scanner(System.in);
        String enter = scanner.next();
        try {
            if (enter.equals("1")) {
                client.setRating();
            } else if (enter.equals("2")){
                System.out.println("Как хотите!");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Нет такой команды!");
        }
        this.products.clear();

    }
}
