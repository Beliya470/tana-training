public class StatusCodeClassifier {
    public static void main(String[] args) {
        // the 11 status codes from the Day 1 test table
        int[] statusCodes = {199, 200, 299, 300, 399, 400, 499, 500, 599, 600, -1};

        // for-each: run the same if / else if chain for every code
        for (int statusCode : statusCodes) {
            String category;
            if (statusCode >= 200 && statusCode <= 299) {
                category = "Success";
            } else if (statusCode >= 300 && statusCode <= 399) {
                category = "Redirect";
            } else if (statusCode >= 400 && statusCode <= 499) {
                category = "Client error";
            } else if (statusCode >= 500 && statusCode <= 599) {
                category = "Server error";
            } else {
                category = "Invalid";
            }
            System.out.println(statusCode + " -> " + category);
        }
    }
}
