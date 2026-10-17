package exceptiondemo;

public class PortParser {

    // returns the value as an int
    // throws IllegalArgumentException if it is not a number or not in 1 to 65535
    public static int parsePort(String value) {
        int port;
        try {
            port = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Port must be a number, but got: " + value);
        }

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port must be between 1 and 65535, but got: " + port);
        }
        return port;
    }

    public static void main(String[] args) {
        String[] values = {"443", "abc", "70000"};

        for (String value : values) {
            try {
                int port = parsePort(value);
                System.out.println("\"" + value + "\" -> " + port);
            } catch (IllegalArgumentException e) {
                System.out.println("\"" + value + "\" -> " + e.getMessage());
            }
        }

        // my own extra checks on the edges
        System.out.println("--- edges ---");
        String[] edges = {"1", "65535", "0", "65536", "-5", "", " 443", "80.5", null};

        for (String value : edges) {
            try {
                int port = parsePort(value);
                System.out.println(value + " -> " + port);
            } catch (IllegalArgumentException e) {
                System.out.println(value + " -> " + e.getMessage());
            }
        }
    }
}
