package inheritancedemo;

// parent class: the common code that student, professor and employee all need
public class Person {

    String name;
    int age;
    String address;

    // common method, prints the common information
    public void printPerson() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
    }
}
