// Lecture: Arrays Introduction and Single Dimensional Array (looping through an array)
public class StudentNames {
    public static void main(String[] args) {
        String[] students = {"John", "Smith", "Mike", "Michael", "Larry"};

        // the long way: one line per index
        System.out.println(students[0]);
        System.out.println(students[1]);
        System.out.println(students[2]);
        System.out.println(students[3]);
        System.out.println(students[4]);

        // the better way: a for loop that works for any array size
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
    }
}
