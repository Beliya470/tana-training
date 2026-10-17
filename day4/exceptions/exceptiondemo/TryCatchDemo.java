package exceptiondemo;

// the W3Schools examples: try...catch, finally and throw
public class TryCatchDemo {

    static void checkAge(int age) {
        if (age < 18) {
            throw new ArithmeticException("Access denied - You must be at least 18 years old.");
        } else {
            System.out.println("Access granted - You are old enough!");
        }
    }

    public static void main(String[] args) {

        // try...catch: the program does not crash
        System.out.println("--- try...catch ---");
        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

        // finally runs after try...catch, whatever the result
        System.out.println("--- finally ---");
        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        } finally {
            System.out.println("The 'try catch' is finished.");
        }

        // finally also runs when there is no exception
        System.out.println("--- finally, no error ---");
        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[1]);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        } finally {
            System.out.println("The 'try catch' is finished.");
        }

        // throw: make my own exception (checkAge)
        System.out.println("--- throw ---");
        checkAge(20);
        try {
            checkAge(15);
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // a few of the exception types from the table
        System.out.println("--- exception types ---");
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }
        try {
            String text = null;
            text.length();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught");
        }
        try {
            "abc".charAt(5);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("StringIndexOutOfBoundsException: " + e.getMessage());
        }
        try {
            System.out.println(10 / 0);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: " + e.getMessage());
        }
    }
}
