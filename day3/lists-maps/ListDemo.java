import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        // a List keeps many values in one variable, in order
        List<String> names = new ArrayList<>();

        names.add("Mike");
        names.add("John");
        names.add("Smith");
        names.add("Ali");

        System.out.println(names);
        System.out.println("Size: " + names.size());
        System.out.println("First: " + names.get(0));

        names.add("John");  // a List allows duplicates
        System.out.println("After adding John again: " + names);

        names.remove("Smith");
        System.out.println("After removing Smith: " + names);

        System.out.println("Has Ali? " + names.contains("Ali"));

        for (String name : names) {
            System.out.println(name);
        }
    }
}
