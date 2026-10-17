package abstractiondemo;

public class RunnerDemo {
    public static void main(String[] args) {

        // create the object of the class, not of the interface
        Book b1 = new Book();
        b1.print();
        b1.testMethod();
        b1.m1();

        // Printable pr = new Printable();   // error: Printable is abstract; cannot be instantiated

        // variables of an interface are static, so use the interface name
        System.out.println(Printable.name);
        System.out.println(Printable.address);

        // the static method also needs no object
        Printable.staticTestMethod();
    }
}
