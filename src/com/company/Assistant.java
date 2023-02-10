package com.company;

import com.company.shop.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Assistant implements Closeable {

    private Shop shop;
    private Basket basket;
    private Delivery delivery;
    private Filter filter;
    private Scanner scanner;
    private Client client;

    public Assistant() {
        this.shop = Shop.getInstance();
        this.basket = Basket.getInstance();
        this.delivery = Delivery.getInstance();
        this.filter = new Filter();
        this.scanner = new Scanner(System.in);
        this.client = Client.getInstance();
    }

    public void speak() throws IOException {
        System.out.println("\n Рад приветствовать Вас, в нашем магазине, я Ваш личный помощник. " +
                "Выберите из нижеперечисленных категорий,интересующую Вас, введите для этого номер операции");
        while (true) {
            System.out.println("""

                    1 - показать ассортимент магазина\s
                    2 - совершить покупку\s
                    3 - воспользоваться нашими рекомендациями\s
                    4 - отфильтровать товар\s
                    5 - узнать статус доставки\s
                    6 - вернуть товар с последней доставки\s
                    7 - повторить последнюю покупку\s
                    end - Закончить выполнение программы""");
            String input = scanner.next();
            if ("end".equals(input)) {
                System.out.println("До свидания!");
                break;
            } else {
                try {
                    int operation = Integer.parseInt(input);
                    switch (operation) {
//  меню отображения ассортимента---------------------------------------------------------------------------------------
                        case 1 -> showProducts(shop.getProducts(), shop.getStorage());
//  --------------------------------------------------------------------------------------------------------------------
//  меню покупок--------------------------------------------------------------------------------------------------------
                        case 2 -> {
                            buyProducts();
                            if (delivery.deliveryComplete()) {
                                setRating();
                            }
                        }
//  --------------------------------------------------------------------------------------------------------------------
//  меню рекомендаций---------------------------------------------------------------------------------------------------
                        case 3 -> recommender();
//  --------------------------------------------------------------------------------------------------------------------
//  меню фильтра--------------------------------------------------------------------------------------------------------
                        case 4 -> filterMenu();
//  --------------------------------------------------------------------------------------------------------------------
//  меню статуса доставки-----------------------------------------------------------------------------------------------
                        case 5 -> delivery.getStatusDelivery();
//  --------------------------------------------------------------------------------------------------------------------
//  меню возврата-------------------------------------------------------------------------------------------------------
                        case 6 -> returnProducts();
//  --------------------------------------------------------------------------------------------------------------------
//  меню повторения последней покупки-----------------------------------------------------------------------------------
                        case 7 -> runLastPurchase();
//  --------------------------------------------------------------------------------------------------------------------
                        default -> System.out.println("Такой операции нет");
                    }
                } catch (NumberFormatException | InterruptedException ex) {
                    System.out.println("В данном меню вводятся только цифры или end");
                }
            }
        }
    }

    protected void showProducts(List<Product> productList, Map<Product, Integer> storage) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            builder.append(i + 1)
                    .append(" - ")
                    .append(productList.get(i).toString())
                    .append(", количество: ")
                    .append(storage.get(productList.get(i)))
                    .append(", ")
                    .append(productList.get(i).getCategory().equals(Category.FOOD) ? " кг" : " шт.")
                    .append("\n");
        }
        System.out.print(builder);
    }

    public void buyProducts() throws InterruptedException {
        while (true) {
            System.out.println("Выберите товары из ассортимента магазина, для окончания покупок введите end");
            showProducts(shop.getProducts(), shop.getStorage());
            String input = scanner.next();
            if ("end".equals(input)) {
                break;
            } else {
                try {
                    int productNumber = Integer.parseInt(input);
                    System.out.println("Введите количество товара");
                    String input1 = scanner.next();
                    int count = Integer.parseInt(input1);
                    Product product = shop.getProducts().get(productNumber - 1);
//                    Если в магазине хватает товара в заданном количестве
                    if (shop.getStorage().containsKey(product) && shop.getStorage().get(product) >= count) {
//                        кладем товар в нужном количестве в корзину
                        if (basket.putToBasket(product, count)) {
                            System.out.println("Товар " + product.getName() +
                                    " в количестве " + basket.getBasket().get(product) + (product.getCategory().equals(Category.FOOD) ? " кг" : " шт.")
                                    + " добавлен в корзину");
                            System.out.println("Ваша корзина: ");
                            showProducts(basket.getProducts(), basket.getBasket());
                        }
                        shop.setCount(product, shop.getStorage().get(product) - count);
                    } else {
                        System.out.println("Товар в заданном количестве отсутствует в магазине");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    System.out.println("В данном меню вводятся только цифры или end!");
                }
            }
        }
        if (basket.getProducts().isEmpty()) {
            System.out.println("Передавать в доставку нечего, корзина пуста");
        } else {
            delivery.setProductToDelivery(basket.getProducts(), basket.getBasket());
            basket.getProducts().clear();
            basket.getBasket().clear();
        }
    }

    protected void recommender() {
        List<Product> recomended =
                shop.getProducts().stream()
                        .filter(elem -> elem.getRating() > 5)
                        .collect(Collectors.toList());
        if (!recomended.isEmpty()) {
            System.out.println("Рекомендуем Вам следующие товары: " + recomended.toString());
        } else {
            System.out.println("К сожалению порекомендовать сейчас нечего");
        }
    }

    protected void filterMenu() {
        while (true) {
            System.out.println("""
                    Выберите по какому признаку отфильтровать товар:\s
                    1 - цена\s
                    2 - категория\s
                    3 - производитель\s
                    end - выйти из меню фильтра""");
            String inputFilter = scanner.next();
            if ("end".equals(inputFilter)) {
                break;
            } else {
                int operationFilter = Integer.parseInt(inputFilter);
                switch (operationFilter) {
                    case 1 -> {
                        System.out.println("Введите сумму и товары с бОльшей ценой отфильтруются.");
                        String field = scanner.next();
                        filter.filterByPrice(shop.getProducts(), field);
                    }
                    case 2 -> {
                        System.out.println("Выберите из представленных категорий товаров: ");
                        filter.printFields(shop.getCategory());
                        String category = scanner.next();
                        filter.filterByCategory(shop.getProducts(), shop.getCategory(), category);
                    }
                    case 3 -> {
                        System.out.println("Выберите из представленных производителей товаров: ");
                        filter.printFields(shop.getMaker());
                        String price = scanner.next();
                        filter.filterByMaker(shop.getProducts(), shop.getMaker(), price);
                    }
                }
            }
        }
    }

    protected void returnProducts() {
        if (!client.getLastPurchaseList().isEmpty()) {
            Map<Product, Integer> returnedProducts = new HashMap<>();
            List<Product> returnedProductsList = new ArrayList<>();
            while (true) {
                System.out.println("Выберите товары для возврата, после введите end");
                showProducts(client.getLastPurchaseList(), client.getLastPurchase());
                String input = scanner.next();
                if ("end".equals(input)) {
                    if (!returnedProducts.isEmpty()) {
                        System.out.println("Список возвращаемых товаров: ");
                        showProducts(returnedProductsList, returnedProducts);
                    }
                    break;
                } else {
                    try {
                        int operation = Integer.parseInt(input);
                        Product returnedProduct = client.getProductList().get(operation - 1);
//                        кладем в список возвращаемых товаров товар с последней доставки
                        returnedProducts.put(returnedProduct, client.getLastPurchase().get(returnedProduct));
                        returnedProductsList.add(returnedProduct);
                        client.takeOfClientStorage(returnedProduct, client.getLastPurchase().get(returnedProduct));
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        System.out.println("Некорректные данные");
                    }
                }
            }
            try {
//            товары из ассортимента магазина
                for (Product item : shop.getProducts()) {
//                возвращаемые товары
                    for (Product value : returnedProductsList) {
//                        увеличить количество товара в магазине
                        shop.setCount(item, shop.getStorage().get(item) + returnedProducts.get(value));
                        System.out.println("Товар " + value.getName() +
                                " в количестве " + returnedProducts.get(value) + (value.getCategory().equals(Category.FOOD) ? " кг" : " шт.")
                                + " возвращен");
                    }
                    break;
                }
                client.getLastPurchase().clear();
                client.getLastPurchaseList().clear();
            } catch (NullPointerException ex) {
                System.out.println("Ничего не возвращено");
            }
        } else {
            System.out.println("Возвращать нечего");
        }
    }

    protected void setRating() {
        System.out.println("""
                Хотите ли выставить рейтинг, полченному товару?\s
                1 - Да\s
                2 - Нет""");
        String enter = scanner.next();
        try {
            if (enter.equals("1")) {
                while (true) {
                    System.out.println("Выберите на какой товар хотите выставить оценку, для завершения наберите end");
                    showProducts(client.getLastPurchaseList(), client.getLastPurchase());
                    String input = scanner.next();
                    if (input.equals("end")) {
                        break;
                    } else {
                        try {
                            int position = Integer.parseInt(input);
                            System.out.println("Введите оценку от 0 до 10");
                            String input1 = scanner.next();
                            int rating = Integer.parseInt(input1);
                            (client.getProductList().get(position - 1)).setRating(rating);
                        } catch (NumberFormatException ex) {
                            System.out.println("Введите корректное значение");
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("В списке нет товара под данным номером");
                        }
                    }
                }
            } else if (enter.equals("2")) {
                System.out.println("Как хотите!");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Нет такой команды!");
        }
    }

    protected void runLastPurchase() throws InterruptedException {
        if (delivery.getLastDeliveryList().isEmpty()) {
            System.out.println("Информация о последнем заказе отсутствует");
        } else {
            int counter = 0;
            for (Product product : delivery.getLastDeliveryList()) {
                if (delivery.getLastDelivery().get(product) > shop.getStorage().get(product)) {
                    System.out.println("К сожалению на складе магазина недостаточно товаров для повторения заказа");
                    break;
                } else {
//        добавление товаров с последней покупки в корзину
                    basket.putToBasket(product, delivery.getLastDelivery().get(product));
                    shop.setCount(product, shop.getStorage().get(product) - delivery.getLastDelivery().get(product));
                    counter++;
                }
            }
            if (counter == delivery.getLastDeliveryList().size()) {
                delivery.setProductToDelivery(basket.getProducts(), basket.getBasket());
                basket.getProducts().clear();
                basket.getBasket().clear();
            }
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}

