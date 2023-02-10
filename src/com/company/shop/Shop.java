package com.company.shop;

import java.util.*;
import java.util.stream.Collectors;

public class Shop {

    private List<Product> products;
    private Random random = new Random();
//    private Map<Product, Integer> storage;

    private Shop() {
        this.products = new ArrayList<>();
        products.add(new FoodProduct(120, FoodSubCategory.FRUIT, "Виноград", "GlobalVillage", generateCount()));
        products.add(new FoodProduct(180, FoodSubCategory.VEGETABLE, "Огурцы", "GlobalVillage", generateCount()));
        products.add(new FoodProduct(300, FoodSubCategory.MEET, "Свинина", "УрюпинскАгропром", generateCount()));
        products.add(new FoodProduct(300, FoodSubCategory.SWEET, "Кекс", "СМАК", generateCount()));
        products.add(new IndustrialProduct(1900, IndustrialSubCategory.BUILDING, "Пассатижи", "Knipex", generateCount()));
        products.add(new IndustrialProduct(5000, IndustrialSubCategory.BUILDING, "Нож монтажный", "Knipex", generateCount()));
        products.add(new IndustrialProduct(600, IndustrialSubCategory.HOUSEHOLD, "Метла", "Ростех", generateCount()));
    }



    private static Shop instance;

    public static Shop getInstance() {
        if (instance == null) instance = new Shop();
        return instance;
    }

    public List<Product> getProducts() {
        return products;
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

    public void buyProducts() throws InterruptedException {
        Basket basket = Basket.getInstance();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите товары из ассортимента магазина, для окончания покупок введите end");
            for (int i = 0; i < this.products.size(); i++) {
                System.out.println((i + 1) + " - " + this.products.get(i).toString());
            }
            String input = scanner.next();
            if ("end".equals(input)) {
                break;
            } else {
                try {
                    int productNumber = Integer.parseInt(input);
                    if (productNumber <= this.products.size()) {
                        System.out.println("Введите количество товара");
                        String input1 = scanner.next();
                        int count = Integer.parseInt(input1);
                        if (products.get(productNumber - 1).getCount() >= count) {
                            Product product;
//  определяем тип необходимого товара
                            if (products.get(productNumber - 1).getCategory().equals(Category.FOOD)) {
                                product = new FoodProduct(
                                        products.get(productNumber - 1).getPrice(),
                                        (FoodSubCategory) products.get(productNumber - 1).getSubCategory(),
                                        products.get(productNumber - 1).getName(),
                                        products.get(productNumber - 1).getMaker(),
                                        count);
                                basket.putToBasket(product);
                            } else if (products.get(productNumber - 1).getCategory().equals(Category.INDUSTRIAL)) {
                                product = new IndustrialProduct(
                                        products.get(productNumber - 1).getPrice(),
                                        (IndustrialSubCategory) products.get(productNumber - 1).getSubCategory(),
                                        products.get(productNumber - 1).getName(),
                                        products.get(productNumber - 1).getMaker(),
                                        count);
                                basket.putToBasket(product);
                            }
                        }
                    } else {
                        System.out.println("Введите корректное значение");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("В данном меню вводятся только цифры или end!");
                }
            }
        }
        if (basket.getProducts().isEmpty()) {
            System.out.println("Передавать в доставку нечего, корзина пуста");
        } else {
            basket.transferToDelivery();
        }
    }

    public void returnProducts(List<Product> product) {
        try {
            Shop shop = Shop.getInstance();
            for (Product item : this.products) {
                for (Product value : product) {
//                        увеличить количество товара в магазине
                    item.setCount(item.getCount() + value.getCount());
                    System.out.println("Товар " + value.getName() +
                            " в количестве " + value.getCount() + (value.getCategory().equals(Category.FOOD) ? " кг" : " шт.")
                            + " возвращен");
                }
                break;
            }
        } catch (NullPointerException ex) {
            System.out.println("Ничего не возвращено");
        }
    }

    public void recommender() {
        // TODO: 09.02.2023 доделать метод
        List<Product> recomended =
        products.stream()
                .filter(elem -> elem.getRating() > 5)
                .collect(Collectors.toList());
        if (!recomended.isEmpty()) {
            System.out.println("Рекомендуем Вам следующие товары: " + recomended.toString());
        } else {
            System.out.println("К сожалению порекомендовать сейчас нечего");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.products.size(); i++) {
            builder.append(i + 1)
                    .append(" - ")
                    .append(this.products.get(i).toString())
                    .append("\n");
        }
        return "На складе содержаться следующие продукты: \n" + builder.toString();
    }
}
