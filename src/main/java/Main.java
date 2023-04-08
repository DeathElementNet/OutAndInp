import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static File textFileMain = new File("basket.json");
    public static String[] products = {"Пиво", "Водка", "Виски", "Чипсы", "Вобла", "Таранька", "Шашлык (цена за кг)"};
    public static int[] prices = {120, 700, 1500, 80, 60, 50, 300};

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        XMLSettings settings = new XMLSettings (new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);


        System.out.println("Задача 1\n");

        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();

        if (textFileMain.exists()) {
            System.out.println("Корзина уже существует и будет использована:");
            basket = Basket.loadFromJSON(textFileMain);
        } else {
            System.out.print("Корзина пуста. ");
            basket = new Basket(products, prices);

        }
        groceryList(basket);

        Scanner scanner = new Scanner(System.in);

       // ClientLog log = new ClientLog();
        while (true) {
            System.out.println("\nВыберите напиток или закусон и количество через пробел " +
                    "или введите \"end\" для выхода:");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                if (settings.isLog) {
                    log.exportAsCSV(logFile);
                }

                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println(String.format("Надо вводить два числа \"Номер Количество\"" +
                        " через пробел, а не '%s'", input));
                continue;
            }


            try {
                if (Integer.parseInt(parts[0]) < 0 || Integer.parseInt(parts[0]) > products.length) {
                    System.out.println(String.format("Надо вводить номер напитка от '1' до '%s'",
                            (products.length)));
                } else if (Integer.parseInt(parts[1]) >= 0) {
                    int productNumber = Integer.parseInt(parts[0]) - 1;
                    int productCount = Integer.parseInt(parts[1]);
                    basket.addToCart(productNumber, productCount);
                    if (settings.isLog) {
                        log.Log(productNumber, productCount);
                    }
                    if (settings.isSave) {
                        switch (settings.saveFormat) {

                        }
                    }
                    if (settings.isSave) {
                        switch (settings.saveFormat) {
                            case "json" -> basket.saveToJSON(saveFile);
                            case "txt" -> basket.saveTxt(saveFile);

                        }
                    }

                    basket.saveToJSON(textFileMain);

                } else
                    System.out.println(String.format("Количество товара не может быть отрицательным" +
                            " '%s'", Integer.parseInt(parts[1])));
            } catch (NumberFormatException e) {
                System.out.println(String.format("Ошибка ввода, вы ввели не два числа через пробел:" +
                        " '%s'", input));
            }
        }

        System.out.println("Ваша корзина:");

        basket.printCart();

    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat)
        throws FileNotFoundException {
        Basket basket;
        if (isLoad && loadFile.exists()){
           basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJSON(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(products, prices);
            };

        } else {
            basket = new Basket(products,prices);
            System.out.print("Корзина не существует или пуста. ");

        }
        return basket;

    }



    private static void groceryList(Basket basket) {
        System.out.println("Список доступных для покупки напитков и товаров:");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%d. %s %d руб/шт \n", i + 1, products[i], prices[i]);


        }
    }
}