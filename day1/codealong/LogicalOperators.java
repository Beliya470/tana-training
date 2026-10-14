// Lecture: Logical Operators
public class LogicalOperators {
    public static void main(String[] args) {
        int marks = 70;
        int attendance = 90;

        // AND: both conditions must be true to pass
        System.out.println("Passed: " + (marks >= 40 && attendance >= 75));

        // OR: at least one condition must be true for the award
        System.out.println("Special award: " + (marks > 90 || attendance > 90));
    }
}
