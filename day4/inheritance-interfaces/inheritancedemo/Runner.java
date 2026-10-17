package inheritancedemo;

public class Runner {
    public static void main(String[] args) {

        // Student: the variables name, age, address are not written in Student
        Student s1 = new Student();
        s1.name = "Anne";                 // from Person
        s1.age = 22;                      // from Person
        s1.address = "Nairobi";           // from Person
        s1.courseName = "QA Automation";  // from Student
        s1.fees = 500;                    // from Student
        System.out.println("--- Student ---");
        s1.printPerson();   // method from the parent class
        s1.printStudent();  // method of the child class

        // Professor: same parent, different specific information
        Professor p1 = new Professor();
        p1.name = "John";
        p1.age = 45;
        p1.address = "Mombasa";
        p1.salary = 3000;
        p1.experience = 15;
        System.out.println("--- Professor ---");
        p1.printPerson();
        p1.printProfessor();

        // Employee: two variables, department and name (name comes from Person)
        Employee e1 = new Employee();
        e1.name = "Mike";
        e1.department = "QA";
        System.out.println("--- Employee ---");
        System.out.println(e1.name + " works in " + e1.department);

        // the parent of each class
        System.out.println("--- Parent of each class ---");
        System.out.println("Student parent: " + Student.class.getSuperclass().getName());
        System.out.println("Professor parent: " + Professor.class.getSuperclass().getName());
        System.out.println("Person parent: " + Person.class.getSuperclass().getName());
        System.out.println("Plain parent: " + Plain.class.getSuperclass().getName());
    }
}
