public class Snippet1 {
    public static void main(String[] args) {
        int[] scores = {70, 85, 90};
        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            total += scores[i];
        }
        System.out.println(total);
    }
}
