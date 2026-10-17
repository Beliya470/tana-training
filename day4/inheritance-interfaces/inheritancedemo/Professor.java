package inheritancedemo;

// Professor is a Person, so it also gets name, age, address and printPerson()
public class Professor extends Person {

    // only the things that are specific to a professor
    int salary;
    int experience;

    public void printProfessor() {
        System.out.println("Salary: " + salary);
        System.out.println("Experience: " + experience + " years");
    }
}
