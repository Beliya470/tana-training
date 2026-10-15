import java.util.ArrayList;

public class ListDemo {
    public static void main(String[] args) {

        // create the object of ArrayList that stores Strings
        // (for numbers we would write ArrayList<Integer>)
        ArrayList<String> names = new ArrayList<String>();

        // add elements to the ArrayList
        names.add("Mike");
        names.add("Smith");
        names.add("John");
        names.add("Ali");

        // names.add(10);  // compile error: this list only accepts Strings (type safe)

        // get one element by its index (the first index is 0)
        System.out.println(names.get(2));   // John

        // get all the elements with a for loop
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }

        // remove an element by its index, Smith is at index 1
        names.remove(1);
        System.out.println("After removing index 1:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }

        // a list allows duplicates and keeps the insertion order
        names.add("Mike");
        System.out.println("After adding Mike again:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }

        // a list of numbers needs the wrapper class Integer, not int
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(10);
        numbers.add(20);
        System.out.println(numbers);
    }
}
