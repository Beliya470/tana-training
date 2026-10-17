package abstractiondemo;

// a class can implement many interfaces, separated by a comma
// (a class can extend only one class)
// Book must give the body of ALL abstract methods of both interfaces
public class Book implements Printable, Interface2 {

    public void print() {
        System.out.println("Book is printed");
    }

    public void testMethod() {
        System.out.println("This is test method of Book class");
    }

    public void m1() {
        System.out.println("This is M1 from Book class");
    }
}
