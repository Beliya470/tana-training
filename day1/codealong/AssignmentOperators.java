// Lecture: Assignment Operators
public class AssignmentOperators {
    public static void main(String[] args) {
        int marks = 80;

        // Long way: take the current value, add 5, store it back
        marks = marks + 5;
        System.out.println("marks = marks + 5: " + marks);

        // Short way: add and assign operator
        marks = 80;
        marks += 5;
        System.out.println("marks += 5: " + marks);
    }
}
