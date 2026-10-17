package abstractiondemo;

public interface Printable {

    // abstract methods: no body. They are public abstract by default.
    void print();

    public abstract void testMethod();

    // void wrongMethod() { }   // error: interface abstract methods cannot have body

    // variables are public static final by default, with or without the words
    public static final String name = "Java";
    String address = "India";

    // a static method can have a body
    public static void staticTestMethod() {
        System.out.println("This is static test method of Printable interface");
    }
}
