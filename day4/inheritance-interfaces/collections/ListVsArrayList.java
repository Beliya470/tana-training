package collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// why List is an interface and ArrayList is a class
public class ListVsArrayList {
    public static void main(String[] args) {

        System.out.println("List is an interface: " + List.class.isInterface());
        System.out.println("ArrayList is an interface: " + ArrayList.class.isInterface());
        System.out.println("ArrayList implements List: " + List.class.isAssignableFrom(ArrayList.class));
        System.out.println("LinkedList implements List: " + List.class.isAssignableFrom(LinkedList.class));

        // List<String> list = new List<String>();   // error: List is abstract; cannot be instantiated

        // the object is made from a class, the interface is only the type
        List<String> names = new ArrayList<String>();
        names.add("Mike");
        names.add("John");
        System.out.println(names);

        // same type, different class: the List methods work the same way
        List<String> names2 = new LinkedList<String>();
        names2.add("Ali");
        names2.add("Smith");
        System.out.println(names2);
    }
}
