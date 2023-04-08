import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {
    private String[] productsBasket;
    private int[] pricesBasket;
    private int[] amountsBasket;

    public Basket(String[] productsBasket, int[] pricesBasket) {
        this.productsBasket = productsBasket;
        this.pricesBasket = pricesBasket;
        this.amountsBasket = new int[productsBasket.length];
    }
    public Basket(String[] productsBasket, int[] pricesBasket, int[] amountsBasket) {
        this.productsBasket = productsBasket;
        this.pricesBasket = pricesBasket;
        this.amountsBasket = amountsBasket;
    }
    public void addToCart(int productNum, int amount) {
        amountsBasket[productNum] += amount;
    }

    public void printCart() {
        int summaryBasket = 0;
        for (int i = 0; i < amountsBasket.length; i++) {
            if (!(amountsBasket[i] == 0)) {
                int currentPrice = (amountsBasket[i] * pricesBasket[i]);
                summaryBasket += currentPrice;
                System.out.printf("%s %d шт. по %d руб./шт. - %d руб в сумме; \n",
                        productsBasket[i], amountsBasket[i], pricesBasket[i], currentPrice);
            }
        }
        System.out.printf("Итого: %d руб. \n", summaryBasket);
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            // первая строка
            out.println(String.join(" ", productsBasket));
            // вторая строка
            out.println(String.join(" ", Arrays.stream(pricesBasket)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new)));
            // третья строка
            for (var amount : amountsBasket)
                out.print(amount + " ");
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            // первая строка файла
            String[] productsLoad = (br.readLine()).split(" ");
            // вторая строка файла
            int[] pricesLoad = Arrays.stream((br.readLine()).split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            // третья строка файла
            int[] amountsLoad = Arrays.stream((br.readLine()).split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            // возврат new Basket по второму конструктору
            return new Basket(productsLoad, pricesLoad, amountsLoad);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void saveToJSON(File textFile) throws IOException {
        FileWriter fileWriter = new FileWriter(textFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        fileWriter.write(gson.toJson(this, Basket.class));
        fileWriter.close();
    }

    public static Basket loadFromJSON(File textFile) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(textFile);
        return gson.fromJson(fileReader, Basket.class);
    }

    @Override
    public String toString() {
        return "Basket:" +
                "\nproductsBasket=" + Arrays.toString(productsBasket) +
                "\npricesBasket=" + Arrays.toString(pricesBasket) +
                "\namountsBasket=" + Arrays.toString(amountsBasket);
    }
}