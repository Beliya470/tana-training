// Lecture: If Else If Introduction
public class IfElseIfDemo {
    public static void main(String[] args) {
        int marks = 35;

        if (marks < 35) {
            System.out.println("C grade");
        } else if (marks >= 35 && marks <= 60) {
            System.out.println("B grade");
        } else if (marks > 60) {
            System.out.println("A grade");
        }
    }
}
