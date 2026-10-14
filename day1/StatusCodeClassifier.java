public class StatusCodeClassifier {
    public static void main(String[] args) {
        int statusCode = 200;

        if (statusCode >= 200 && statusCode <= 299) {
            System.out.println("Status code " + statusCode + " -> Success");
        } else if (statusCode >= 300 && statusCode <= 399) {
            System.out.println("Status code " + statusCode + " -> Redirect");
        } else if (statusCode >= 400 && statusCode <= 499) {
            System.out.println("Status code " + statusCode + " -> Client error");
        } else if (statusCode >= 500 && statusCode <= 599) {
            System.out.println("Status code " + statusCode + " -> Server error");
        } else {
            System.out.println("Status code " + statusCode + " -> Invalid");
        }
    }
}
