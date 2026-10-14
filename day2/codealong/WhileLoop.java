// Lecture: While Loop Introduction
public class WhileLoop {
    public static void main(String[] args) {
        int battery = 20;

        // keep playing while the battery is above 10%
        while (battery > 10) {
            System.out.println("Play, battery: " + battery);
            battery--;
        }
    }
}
