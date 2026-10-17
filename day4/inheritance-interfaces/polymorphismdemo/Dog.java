package polymorphismdemo;

public class Dog extends Animal {

    // method overriding: same name and signature as the parent method,
    // only the code inside the curly brackets is different
    // (@Override is optional, it makes the compiler check that I really override)
    @Override
    public void printAnimal() {
        System.out.println("This is a dog");
    }

    // @Override
    // public static void testMethod() { }   // error: static methods cannot be annotated with @Override
}
