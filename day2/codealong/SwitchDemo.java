// Lecture: Switch Case Introduction
public class SwitchDemo {
    public static void main(String[] args) {
        int day = 2;

        // switch looks at day and runs the matching case
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            default:
                // runs when no case matches, like the else in if / else if
                System.out.println("Other");
        }
    }
}
