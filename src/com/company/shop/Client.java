package com.company.shop;

import java.util.*;

public class Client {

    private List<Product> productList;
    private List<Product> lastPurchaseList;
    private Map<Product, Integer> clientStorage;
    private Map<Product, Integer> lastPurchase;

    private Client() {
        this.productList = new ArrayList<>();
        this.lastPurchaseList = new ArrayList<>();
        this.clientStorage = new HashMap<>();
        this.lastPurchase = new HashMap<>();
    }

    private static Client instance;

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public Map<Product, Integer> getLastPurchase() {
        return lastPurchase;
    }

    public List<Product> getLastPurchaseList() {
        return lastPurchaseList;
    }

    public Map<Product, Integer> getClientStorage() {
        return clientStorage;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void takeOfClientStorage(Product product, Integer count) {
        if (count.equals(clientStorage.get(product))) {
            clientStorage.remove(product);
        } else if ((clientStorage.get(product)) > count) {
            clientStorage.replace(product, clientStorage.get(product) - count);
        }
        lastPurchaseList.clear();
        lastPurchase.clear();
    }

    public boolean putToClientStorage(Map<Product, Integer> product) {
        Set<Product> set = product.keySet();
        for (Product item : set) {
//            ищем содержится ли товар в домашнем хранилище
            if (clientStorage.containsKey(item)) {
//                находим и увеличиваем количество
                clientStorage.replace(item, (clientStorage.get(item) + product.get(item)));
            } else {
//                не находим и добавляем
                clientStorage.put(item, product.get(item));
//                добавляем в перепись товаров
                productList.add(item);
            }
//            добавляем в перечень последней доставки
            lastPurchaseList.add(item);
            lastPurchase.put(item, product.get(item));
        }
        return true;
    }
}
