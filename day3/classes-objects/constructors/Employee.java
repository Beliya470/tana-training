package constructors;

public class Employee {

    String employeeName;
    String employeeDepartment;
    int salary;

    // parameterized constructor with three parameters
    public Employee(String n, String d, int s) {
        employeeName = n;
        employeeDepartment = d;
        salary = s;
    }

    // another constructor with two parameters (the parameters must be different)
    public Employee(String n, String d) {
        employeeName = n;
        employeeDepartment = d;
    }

    // A second constructor with the same parameters (String, String) would give
    // an error, because it would be a duplicate.

    public void printEmployee() {
        System.out.println(employeeName);
        System.out.println(employeeDepartment);
        System.out.println(salary);
    }
}
