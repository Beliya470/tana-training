public class LoginValidatorTest {

    public static void main(String[] args) {
        // Each row: {username, password}, with the expected result at the same index below.
        String[][] inputs = {
            {"anne1", "password1"},            // 1. valid login
            {null, "password1"},               // 2. rule 1: null username
            {"anne1", null},                   // 3. rule 1: null password
            {"", "password1"},                 // 4. rule 1: empty username
            {"anne1", ""},                     // 5. rule 1: empty password
            {"ab", "password1"},               // 6. rule 2: 2 chars, too short
            {"abc", "password1"},              // 7. rule 2: exactly 3 chars, lower limit
            {"abcdefghijklmno", "password1"},  // 8. rule 2: exactly 15 chars, upper limit
            {"abcdefghijklmnop", "password1"}, // 9. rule 2: 16 chars, too long
            {"anne_1", "password1"},           // 10. rule 3: underscore not allowed
            {"anne 1", "password1"},           // 11. rule 3: space not allowed
            {"anne1", "pass1234"},             // 12. rule 4: exactly 8 chars, lower limit
            {"anne1", "pass123"},              // 13. rule 4: 7 chars, too short
            {"anne1", "password"},             // 14. rule 5: no digit
            {"anne1", "12345678"},             // 15. rule 5: digits only is fine
        };
        boolean[] expected = {
            true, false, false, false, false,
            false, true, true, false,
            false, false,
            true, false,
            false, true
        };

        int passed = 0;
        int failed = 0;

        for (int i = 0; i < inputs.length; i++) {
            String username = inputs[i][0];
            String password = inputs[i][1];
            boolean actual = LoginValidator.isValid(username, password);

            if (actual == expected[i]) {
                passed++;
                System.out.println("PASS  row " + (i + 1) + "  username=" + username
                        + " password=" + password + "  expected=" + expected[i] + " actual=" + actual);
            } else {
                failed++;
                System.out.println("FAIL  row " + (i + 1) + "  username=" + username
                        + " password=" + password + "  expected=" + expected[i] + " actual=" + actual);
            }
        }

        System.out.println(passed + " passed, " + failed + " failed");
    }
}
