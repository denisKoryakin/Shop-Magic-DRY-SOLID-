# 1. Magic

Магические числа полностью исключены из кода. Везде имеются только ссылки на объекты и их методы.

# 2. DRY
   
Повторения допустил только в классе Filter:

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

Данное решение обусловлено отсутствием необходимости усложнения методов фильтрации, для упрощения кода.
Пример применения принципа DRY можно наблюдать в методе protected void showProducts() в классе Assistant, 
служащего для отображения списка товаров:

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

# 3. SOLID

## 3.1 Single-responsibility principle

Весь проект разделен на классы. Каждый выполняет только свой логический объяснимый функционал.
Класс Shop описывает магазин, его хранилище и основные методы по взаимодействию с магазином.
Класс Assistent выполняет роль асистента для функционального взаимодействия с магазином и его методами.
Класс Filter предназначен только для фильтрации товаров про определенным параметрам.
Классы товаров Product, FoodProduct, IndustrialProduct предназначены только для описания товаров. 
При реализации классов применено наследование.
Классы типа Enum необходимы для категорирования товаров.
Класс Basket предназначен для формирования корзины покупок и передачи их в доставку.
Класс Delivery только доставляет.

## 3.2 Open-closed principle

Классы товаров FoodProduct, IndustrialProduct расширяют класс Product не меняя его. Можно легко добавить дополнительный 
дочерний клас, расширяющий Product либо расширить любой из классов FoodProduct или IndustrialProduct, при этом менять 
имеющиеся классы не требуется.

## 3.3 Liskov substitution principle

Наследование имеется только классов FoodProduct, IndustrialProduct от Product. Помимо этого применение наследования
для реализации требуемого функционала не потребовалось.

## 3.4 Interface segregation principle



## 3.5 Dependency inversion principle

