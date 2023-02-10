package com.company;

import com.company.shop.Delivery;
import com.company.shop.Client;
import com.company.shop.Product;
import com.company.shop.Shop;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Assistant {

    public void speak() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Shop shop = Shop.getInstance();
        Delivery delivery = Delivery.getInstance();
        Filter filter = new Filter();
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
                        case 1:
                            System.out.println(shop);
                            break;
//  --------------------------------------------------------------------------------------------------------------------
//  меню покупок--------------------------------------------------------------------------------------------------------
                        case 2:
                            shop.buyProducts();
                            break;
//  --------------------------------------------------------------------------------------------------------------------
//  меню рекомендаций---------------------------------------------------------------------------------------------------                      
                        case 3:
                            shop.recommender();
                            break;
//  --------------------------------------------------------------------------------------------------------------------
//  меню фильтра--------------------------------------------------------------------------------------------------------
                        case 4:
                            while (true) {
                                System.out.println("Выберите по какому признаку отфильтровать товар: \n" +
                                        "1 - цена \n" +
                                        "2 - категория \n" +
                                        "3 - производитель \n" +
                                        "end - выйти из меню фильтра");
                                String inputFilter = scanner.next();
                                if ("end".equals(inputFilter)) {
                                    break;
                                } else {
                                    int operationFilter = Integer.parseInt(inputFilter);
                                    switch (operationFilter) {
                                        case 1:
                                            System.out.println("Введите сумму, товары с бОльшей ценой отфильтруются.");
                                            filter.filterByPrice();
                                            break;
                                        case 2:
                                            System.out.println("выберите из представленных категорий товаров: ");
                                            filter.printFields(shop.getCategory());
                                            filter.filterByCategory(shop.getCategory());
                                            break;
                                        case 3:
                                            System.out.println("выберите из представленных производителей товаров: ");
                                            filter.printFields(shop.getMaker());
                                            filter.filterByMaker(shop.getMaker());
                                            break;
                                    }
                                }
                            }
                            break;
//  --------------------------------------------------------------------------------------------------------------------
//  меню статуса доставки-----------------------------------------------------------------------------------------------
                        case 5:
                            delivery.getStatusDelivery();
                            break;
//  --------------------------------------------------------------------------------------------------------------------
//  меню возврата-------------------------------------------------------------------------------------------------------
                        case 6:
                            Client client = Client.getInstance();
                            client.returnProducts();
                            break;
//  --------------------------------------------------------------------------------------------------------------------
                        default:
                            System.out.println("Такой операции нет");
                    }
                } catch (NumberFormatException | InterruptedException ex) {
                    System.out.println("В данном меню вводятся только цифры или end");
                }
            }
        }
        scanner.close();
    }

    protected void showProducts(List<Product> productList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            builder.append(i + 1)
                    .append(" - ")
                    .append(productList.get(i).toString())
                    .append("\n");
        }
    }
}
