class Student {

    // instance variable: each student has their own name
    String name;

    // static variable: one copy shared by every student
    static String schoolName = "Tana School";
    static int studentCount = 0;

    Student(String name) {
        this.name = name;
        studentCount++;
    }
}

public class InstanceStaticDemo {
    public static void main(String[] args) {
        Student s1 = new Student("Anne");
        Student s2 = new Student("John");

        System.out.println(s1.name);
        System.out.println(s2.name);

        // static variables are used with the class name
        System.out.println(Student.schoolName);
        System.out.println("Students created: " + Student.studentCount);

        // change the static variable once, and everyone sees the change
        Student.schoolName = "Tufin School";
        System.out.println(Student.schoolName);
    }
}
