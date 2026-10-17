package exceptiondemo;

// the two W3Schools examples that crash, because nothing catches the exception
// run with the argument "array" or "age"
public class UncaughtDemo {

    static void checkAge(int age) {
        if (age < 18) {
            throw new ArithmeticException("Access denied - You must be at least 18 years old.");
        } else {
            System.out.println("Access granted - You are old enough!");
        }
    }

    public static void main(String[] args) {
        if (args[0].equals("array")) {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]); // error! index 10 does not exist
        } else {
            checkAge(15);
        }
    }
}
