import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] productsBasket;
    private int[] pricesBasket;
    private int[] amountsBasket;
    private int summaryBasket;

    public Basket(String[] productsBasket, int[] pricesBasket) {
        this.productsBasket = productsBasket;
        this.pricesBasket = pricesBasket;
        this.amountsBasket = new int[productsBasket.length];
        this.summaryBasket = 0;
    }

    public Basket(String[] productsBasket, int[] pricesBasket, int[] amountsBasket, int summaryBasket) {
        this.productsBasket = productsBasket;
        this.pricesBasket = pricesBasket;
        this.amountsBasket = amountsBasket;
        this.summaryBasket = summaryBasket;
    }


    public void addToCart(int productNum, int amount) {
        int currentPrice = pricesBasket[productNum];
        amountsBasket[productNum] += amount;
        summaryBasket += currentPrice * amount;
    }


    public void printCart() {
        for (int i = 0; i < amountsBasket.length; i++) {
            if (!(amountsBasket[i] == 0)) {
                System.out.printf("%s %d шт. по %d руб./шт. - %d руб в сумме; \n",
                        productsBasket[i], amountsBasket[i], pricesBasket[i],
                        (amountsBasket[i] * pricesBasket[i]));
            }
        }
        System.out.printf("Итого: %d руб. \n", summaryBasket);
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : productsBasket)
                out.print(product + " ");
            out.println();

            for (int price : pricesBasket)
                out.print(price + " ");
            out.println();

            for (int amount : amountsBasket)
                out.print(amount + " ");
            out.print("\n" + summaryBasket);
            out.flush();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        String[] productsLoad;
        int[] pricesLoad;
        int[] amountsLoad;
        int summaryLoad;
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            productsLoad = (br.readLine()).split(" ");     // первая строка файла
            String[] interim2 = (br.readLine()).split(" ");// вторая строка файла
            pricesLoad = new int[productsLoad.length];
            for (int i = 0; i < interim2.length; i++) {
                pricesLoad[i] = Integer.parseInt(interim2[i]);
            }

            String[] interim3 = (br.readLine()).split(" ");// третья строка файла
            amountsLoad = new int[interim3.length];
            for (int i = 0; i < interim3.length; i++) {
                amountsLoad[i] = Integer.parseInt(interim3[i]);
            }

            summaryLoad = Integer.parseInt(br.readLine());       // четвертая строка файла
            return new Basket(productsLoad, pricesLoad, amountsLoad, summaryLoad);
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "Basket:" +
                "\nproductsBasket=" + Arrays.toString(productsBasket) +
                "\npricesBasket=" + Arrays.toString(pricesBasket) +
                "\namounts=" + Arrays.toString(amountsBasket) +
                "\nsummary=" + summaryBasket;

    }
}