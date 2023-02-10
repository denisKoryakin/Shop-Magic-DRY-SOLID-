package com.company.shop;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    Shop shop = Shop.getInstance();
    Delivery delivery = Delivery.getInstance();
    private List<Product> products = new ArrayList<>();

    private static Basket instance;

    public static Basket getInstance() {
        if (instance == null) instance = new Basket();
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void putToBasket(Product product) {
        int counter = 0;
        for (int i = 0; i < shop.getProducts().size(); i++) {
//            если данный товар имеется в магазине
            if (shop.getProducts().get(i).getName().equals(product.getName())) {
//                если в магазине хватает этого товара
                if (shop.getProducts().get(i).getCount() >= product.getCount()) {
//                    убрать его с полки магазина
                    shop.getProducts().get(i).setCount(shop.getProducts().get(i).getCount() - product.getCount());
//                    помещаем в корзину
                    for (Product value : this.products) {
//                        если такой товар уже есть в корзине
                        if (product.getName().equals(value.getName())) {
//                            только увеличить его количество
                            value.setCount(value.getCount() + product.getCount());
                            break;
                        } else {
                            counter++;
                        }
                    }
                    if(counter == this.products.size()) {
//                    иначе добавить новую позицию
                        this.products.add(product);
                        counter = 0;
                    }
                }
                break;
            }
        }
        System.out.println("Товар " + product.getName() +
                " в количестве " + product.getCount() + (product.getCategory().equals(Category.FOOD) ? " кг" : " шт.")
                + " добавлен в корзину");
        System.out.println("Ваша корзина: " + this.products.toString());
    }

    // TODO: 09.02.2023 сделать в асисте
    public void transferToDelivery() throws InterruptedException {
        delivery.setProductToDelivery(this.products);
        this.products.clear();
    }
}
