public class Runner {
    public static void main(String[] args) {
        // object made with the default constructor
        Employee emp1 = new Employee();
        emp1.printEmployee();

        // object made with the parameterized constructor
        Employee emp2 = new Employee("Smith", "IT", 200);
        emp2.printEmployee();

        // reading one variable with the object
        System.out.println("Department of emp2: " + emp2.employeeDepartment);
    }
}
