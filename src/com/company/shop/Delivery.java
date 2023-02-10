package com.company.shop;

import java.util.*;

public class Delivery {

    private List<Product> products;
    private Map<Product, Integer> deliveryBox;
    private List<Product> lastDeliveryList;
    private  Map<Product, Integer> lastDelivery;
    Client client = Client.getInstance();

    private Delivery() {
        this.products = new ArrayList<>();
        this.deliveryBox = new HashMap<>();
        this.lastDeliveryList = new ArrayList<>();
        this.lastDelivery = new HashMap<>();
    }

    private static Delivery instance;

    public static Delivery getInstance() {
        if (instance == null) instance = new Delivery();
        return instance;
    }

    public Map<Product, Integer> getLastDelivery() {
        return lastDelivery;
    }

    public List<Product> getLastDeliveryList() {
        return lastDeliveryList;
    }

    public List<Product> getProducts() {
        return lastDeliveryList;
    }

    public void setProductToDelivery(List<Product> product, Map<Product, Integer> productBox) throws InterruptedException {
        lastDelivery.clear();
        lastDeliveryList.clear();
        products = product;
        deliveryBox = productBox;
        for (Product product1 : product) {
            lastDeliveryList.add(product1);
            lastDelivery.put(product1, productBox.get(product1));
        }
        System.out.println("В доставку переданы следующие товары: " + this.products.toString());
        Thread.sleep(3_000);
        System.out.println("Товар успешно доставлен!");
        deliveryComplete();
    }

//ввиду того, что доставка должна идти параллельным потоком, нижеприведенный метод по сути не нужен
    public void getStatusDelivery() {
            if (products == null) {
                System.out.println("В настоящий момент товаров в доставке нету");
        } else {
                System.out.println("В настоящий момент в доставке находятся: " + deliveryBox);
            }
    }

    public boolean deliveryComplete() {
        if (client.putToClientStorage(deliveryBox)) {
            products.clear();
            deliveryBox.clear();
            return true;
        } else {
            return false;
        }
    }
}
