// Lecture: Arrays Introduction and Single Dimensional Array
public class BrowserArray {
    public static void main(String[] args) {
        String[] browsers = {"Chrome", "Firefox", "Edge"};

        // access elements by index: the first index is 0
        System.out.println(browsers[0]);
        System.out.println(browsers[1]);
        System.out.println(browsers[2]);

        // browsers[5] does not exist, so it would throw ArrayIndexOutOfBoundsException
        // System.out.println(browsers[5]);

        // length gives the number of elements
        int size = browsers.length;
        System.out.println(size);

        // check the index is valid before using it
        int index = 5;
        if (index < size) {
            System.out.println(browsers[index]);
        } else {
            System.out.println("Invalid index");
        }
    }
}
