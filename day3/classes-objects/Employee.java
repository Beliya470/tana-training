public class Employee {

    // instance variables: every Employee object has its own copy
    String employeeName;
    String employeeDepartment;
    int salary;

    // default constructor (no parameters)
    public Employee() {
        employeeName = "Mike";
        employeeDepartment = "QA";
        salary = 100;
    }

    // parameterized constructor: the values come from outside
    public Employee(String n, String d, int s) {
        employeeName = n;
        employeeDepartment = d;
        salary = s;
    }

    public void printEmployee() {
        System.out.println(employeeName);
        System.out.println(employeeDepartment);
        System.out.println(salary);
    }
}
