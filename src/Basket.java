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

    public void saveBin(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
            oos.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
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