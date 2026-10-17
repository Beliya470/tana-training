package inheritancedemo;

// Student is a Person, so it gets name, age, address and printPerson() from Person
public class Student extends Person {

    // only the things that are specific to a student
    String courseName;
    int fees;

    public void printStudent() {
        System.out.println("Course: " + courseName);
        System.out.println("Fees: " + fees);
    }
}
