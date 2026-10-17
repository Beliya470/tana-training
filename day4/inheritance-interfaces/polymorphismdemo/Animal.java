package polymorphismdemo;

public class Animal {

    // the generic implementation
    public void printAnimal() {
        System.out.println("This is an animal");
    }

    // a static method cannot be overridden
    public static void testMethod() {
        System.out.println("This is test method");
    }
}
