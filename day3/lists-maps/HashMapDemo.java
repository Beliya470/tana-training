import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        // a HashMap keeps key and value pairs
        HashMap<String, String> capitalCities = new HashMap<>();

        capitalCities.put("England", "London");
        capitalCities.put("India", "New Delhi");
        capitalCities.put("Austria", "Wien");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("Norway", "Oslo");  // same key again, no duplicate

        System.out.println(capitalCities);
        System.out.println("Size: " + capitalCities.size());
        System.out.println("England: " + capitalCities.get("England"));

        capitalCities.remove("England");
        System.out.println("After removing England: " + capitalCities);

        for (String country : capitalCities.keySet()) {
            System.out.println(country + " -> " + capitalCities.get(country));
        }
    }
}
