public class StringCompare {
    public static void main(String[] args) {
        String a = "tufin";
        String b = new String("tufin");

        System.out.println("a == b: " + (a == b));
        System.out.println("a.equals(b): " + a.equals(b));
    }
}
