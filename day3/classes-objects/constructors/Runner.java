package constructors;

public class Runner {
    public static void main(String[] args) {

        // two values: the two parameter constructor runs, salary stays 0
        Employee employee1 = new Employee("Mike", "QA");
        employee1.printEmployee();

        // three values: the three parameter constructor runs
        Employee employee2 = new Employee("Smith", "IT", 200);
        employee2.printEmployee();

        // new Employee() would be an error now.
        // We wrote our own constructors, so Java does not add the default one.
    }
}
