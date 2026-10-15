public class LoginValidator {

    // Rules:
    // 1. Username and password must not be null or empty.
    // 2. Username must be 3 to 15 characters long.
    // 3. Username may contain only letters and digits.
    // 4. Password must be at least 8 characters long.
    // 5. Password must contain at least one digit.
    public static boolean isValid(String username, String password) {
        if (username == null || password == null) return false;
        if (username.isEmpty() || password.isEmpty()) return false;
        if (username.length() < 3 || username.length() > 15) return false;

        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }

        if (password.length() < 8) return false;

        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasDigit;
    }
}
