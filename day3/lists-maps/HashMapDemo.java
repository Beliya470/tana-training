import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {

        // Create a HashMap with String keys and String values
        HashMap<String, String> capitalCities = new HashMap<String, String>();

        // Add Items with put()
        capitalCities.put("England", "London");
        capitalCities.put("India", "New Delhi");
        capitalCities.put("Austria", "Wien");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("Norway", "Oslo");   // same key again, the latest value is kept
        capitalCities.put("USA", "Washington DC");
        System.out.println(capitalCities);

        // Access an Item with get()
        System.out.println(capitalCities.get("England"));

        // HashMap Size (only counts unique keys)
        System.out.println("Size: " + capitalCities.size());

        // Remove an Item with remove()
        capitalCities.remove("England");
        System.out.println(capitalCities);

        // Loop through the keys
        for (String i : capitalCities.keySet()) {
            System.out.println(i);
        }

        // Loop through the values
        for (String i : capitalCities.values()) {
            System.out.println(i);
        }

        // Loop through keys and values
        for (String i : capitalCities.keySet()) {
            System.out.println("key: " + i + " value: " + capitalCities.get(i));
        }

        // Remove all items with clear()
        capitalCities.clear();
        System.out.println("After clear, size: " + capitalCities.size());

        // Other types: use the wrapper class Integer, not int
        HashMap<String, Integer> people = new HashMap<String, Integer>();
        people.put("John", 32);
        people.put("Steve", 30);
        people.put("Angie", 33);
        for (String i : people.keySet()) {
            System.out.println("key: " + i + " value: " + people.get(i));
        }

        // The Map interface: the variable is a Map, the object is a HashMap
        Map<String, String> cities = new HashMap<>();
        cities.put("Kenya", "Nairobi");
        System.out.println(cities);

        // var (Java 10): the compiler works out the type
        var codes = new HashMap<String, String>();
        codes.put("KE", "Kenya");
        System.out.println(codes);
    }
}
