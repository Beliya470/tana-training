public class PortParser {

    // returns the value as an int
    // throws IllegalArgumentException if it is not a number or not in 1 to 65535
    // (same method as the Exceptions task on Day 4, moved into this project so it can be tested)
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
}
