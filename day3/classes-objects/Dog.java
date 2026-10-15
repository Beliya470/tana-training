public class Dog {

    // instance variables
    String name;
    String color;

    // parameterized constructor
    public Dog(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static void main(String[] args) {
        Dog dog = new Dog("Bruno", "Brown");
        System.out.println("Name: " + dog.name);
        System.out.println("Color: " + dog.color);
    }
}
