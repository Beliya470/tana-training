import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionsPractice {
    public static void main(String[] args) {

        // 1. ArrayList of 5 product names
        List<String> products = new ArrayList<>();
        products.add("Laptop");
        products.add("Mouse");
        products.add("Keyboard");
        products.add("Monitor");
        products.add("Webcam");

        products.add("Headset");   // add one
        products.remove("Mouse");  // remove one

        System.out.println("Products:");
        for (String product : products) {
            System.out.println(product);
        }

        // 2. HashMap of product name to stock count
        HashMap<String, Integer> stock = new HashMap<>();
        stock.put("Laptop", 10);
        stock.put("Keyboard", 25);
        stock.put("Monitor", 7);
        stock.put("Webcam", 0);
        stock.put("Headset", 15);

        System.out.println();
        System.out.println("Stock:");
        for (String product : stock.keySet()) {
            System.out.println(product + " = " + stock.get(product));
        }

        // 3. Look up a product that does not exist
        Integer result = stock.get("Printer");
        System.out.println();
        System.out.println("Stock of Printer: " + result);  // prints null
    }
}
