package oops;

public class Runner {
    public static void main(String[] args) {

        // create the object of the Employee class
        Employee emp1 = new Employee();

        // use the object to run the method
        emp1.printEmployee();

        // use the object to read a variable
        System.out.println(emp1.employeeDepartment);

        // one class can make many objects, each needs its own reference variable
        Employee emp2 = new Employee();
        emp2.employeeName = "John";
        emp2.employeeDepartment = "IT";
        emp2.printEmployee();
    }
}
