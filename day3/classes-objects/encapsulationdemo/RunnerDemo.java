package encapsulationdemo;

public class RunnerDemo {
    public static void main(String[] args) {
        Employee emp1 = new Employee();

        // emp1.name = "John";  // error: name is private

        emp1.setName("John");
        String nm = emp1.getName();
        System.out.println(nm);

        emp1.setDepartment("QA");
        System.out.println(emp1.getDepartment());
    }
}
