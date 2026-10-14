public class ScoreStats {

    // adds every score, then divides by how many scores there are
    static double averageScore(int[] scores) {
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        return (double) total / scores.length;
    }

    // starts with the first score and keeps the biggest one seen so far
    static int highestScore(int[] scores) {
        int highest = scores[0];
        for (int score : scores) {
            if (score > highest) {
                highest = score;
            }
        }
        return highest;
    }

    public static void main(String[] args) {
        int[] classA = {85, 90, 72};
        int[] classB = {55, 100, 45, 60, 85};
        int[] single = {42};

        System.out.println("classA average: " + averageScore(classA) + ", highest: " + highestScore(classA));
        System.out.println("classB average: " + averageScore(classB) + ", highest: " + highestScore(classB));
        System.out.println("single average: " + averageScore(single) + ", highest: " + highestScore(single));
    }
}
