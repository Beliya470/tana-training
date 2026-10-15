package encapsulationdemo;

public class Employee {

    // private: only this class can use it
    private String name;
    private String department;

    // getter: prefix get, returns the value
    public String getName() {
        return name;
    }

    // setter: prefix set, puts a value in
    public void setName(String n) {
        name = n;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String d) {
        department = d;
    }
}
