package com.company.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private List<Product> products = new ArrayList<>();
    // TODO: 09.02.2023 сделать лист последней доставки

    private static Client instance;

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public void setProductsToHome(List<Product> product) {
        int counter = 0;
        for (Product item : product) {
            for (Product value : this.products) {
//          если такой товар уже есть в холодильнике
                if (value.getName().equals(item.getName())) {
//          только увеличить его количество
                    value.setCount(value.getCount() + item.getCount());
                    break;
                } else {
                    counter++;
                }
            }
            if (counter == this.products.size()) {
//          иначе добавить новую позицию
                products.add(item);
                counter = 0;
            }
        }
    }

    public void returnProducts() {
        Shop shop = Shop.getInstance();
        List<Product> returned = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        if (!products.isEmpty()) {


            while (true) {
                System.out.println("Выберите товары для возврата, после введите end");
                for (int i = 0; i < this.products.size(); i++) {
                    System.out.println((i + 1) + this.products.get(i).toString());
                }
                String input = scanner.next();
                if ("end".equals(input)) {
                    if (!returned.isEmpty()) {
                        System.out.println("Список возвращаемых товаров: " + returned.toString());
                    }
                    break;
                } else {
                    try {
                        int operation = Integer.parseInt(input);
                        if (operation <= this.products.size()) {
                            returned.add(this.products.get(operation - 1));
                            System.out.println();
                            this.products.remove(operation - 1);
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Нет такой команды");
                    }
                }
            }
            shop.returnProducts(returned);
        } else {
            System.out.println("Возвращать нечего");
        }
    }

    public void setRating() {
        while (true) {
            Shop shop = Shop.getInstance();
            System.out.println("Выберите на какой товар хотите выставить оценку, для завершения наберите end");
            for (int i = 0; i < this.products.size(); i++) {
                System.out.println((i + 1) + this.products.get(i).toString());
            }
            Scanner scanner = new Scanner(System.in);
            String enter = scanner.next();
            if (enter.equals("end")) {
                break;
            } else {
                try {
                    int choice = Integer.parseInt(enter);
                    System.out.println("Введите оценку от 0 до 10");
                    int rating = scanner.nextInt();
                    for (int i = 0; i < shop.getProducts().size(); i++) {
                        if (shop.getProducts().get(i).getName().equals(this.products.get(choice - 1).getName())) {
                            shop.getProducts().get(i).setRating(rating);
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Введите корректное значение");
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("В списке нет товара под данным номером");
                }
            }
        }
    }
}
