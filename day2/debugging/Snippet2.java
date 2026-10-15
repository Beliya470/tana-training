public class Snippet2 {
    public static void main(String[] args) {
        String expected = "Success";
        String actual = new String("Success");
        if (actual.equals(expected)) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }
}
