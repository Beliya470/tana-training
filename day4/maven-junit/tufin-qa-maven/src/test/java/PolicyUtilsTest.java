import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

// Unit tests for every method of PolicyUtils and for parsePort (PortParser).
// Every test uses Arrange-Act-Assert and has a @DisplayName that reads like a test case title.
@DisplayName("PolicyUtils and parsePort")
public class PolicyUtilsTest {

    // values used again and again, named so the tests are easy to read
    private static final String WEB_IP = "10.0.0.5";
    private static final String OTHER_IP = "1.1.1.1";
    private static final int HTTP_PORT = 80;

    // ------------------------------------------------------------
    // 1. Port boundaries: 0, 1, 65535 and 65536 (parsePort)
    // ------------------------------------------------------------

    // 0 and 65536 are just outside the valid range 1 to 65535. 1 and 65535 are the edges inside.
    @ParameterizedTest(name = "port {0}: accepted = {1}")
    @CsvSource({
            "0, false",
            "1, true",
            "65535, true",
            "65536, false"
    })
    @DisplayName("Port boundaries: only 1 to 65535 is accepted")
    void portBoundaries(String port, boolean expectedAccepted) {
        // Arrange: the port text and the expected answer come from the CsvSource row

        // Act
        boolean accepted = isAccepted(port);

        // Assert
        assertEquals(expectedAccepted, accepted);
    }

    // ------------------------------------------------------------
    // 2. Protocol matching ignores case (shouldApplyRule)
    // ------------------------------------------------------------

    @ParameterizedTest(name = "rule protocol {0}, request protocol {1} -> applies = {2}")
    @CsvSource({
            "TCP, tcp, true",
            "tcp, TCP, true",
            "TCP, TCP, true",
            "TCP, TcP, true",
            "TCP, UDP, false"
    })
    @DisplayName("Protocol is matched without looking at upper or lower case")
    void protocolIgnoresCase(String ruleProtocol, String requestProtocol, boolean expected) {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, ruleProtocol));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, requestProtocol, rules);

        // Assert
        assertEquals(expected, applies);
    }

    // ------------------------------------------------------------
    // 3. Source matching (shouldApplyRule)
    // ------------------------------------------------------------

    @Test
    @DisplayName("Rule with source * applies to any source IP")
    void wildcardSourceMatchesAnyIp() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("any", "*", HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(OTHER_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertTrue(applies);
    }

    @Test
    @DisplayName("Rule applies when the source IP is exactly the same")
    void exactSourceIpMatches() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertTrue(applies);
    }

    @Test
    @DisplayName("Rule does not apply when only the source IP differs")
    void ruleDoesNotApplyWhenOnlySourceDiffers() {
        // Arrange: port and protocol are the same as the rule, only the source IP is different
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(OTHER_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertFalse(applies);
    }

    // ------------------------------------------------------------
    // 4. assertThrows for parsePort
    // ------------------------------------------------------------

    @Test
    @DisplayName("parsePort rejects text that is not a number, with a clear message")
    void parsePortRejectsText() {
        // Arrange
        String notANumber = "abc";

        // Act
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> PortParser.parsePort(notANumber));

        // Assert
        assertEquals("Port must be a number, but got: abc", error.getMessage());
    }

    @Test
    @DisplayName("parsePort rejects a port above 65535, with a clear message")
    void parsePortRejectsPortAboveRange() {
        // Arrange
        String tooBig = "70000";

        // Act
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> PortParser.parsePort(tooBig));

        // Assert
        assertEquals("Port must be between 1 and 65535, but got: 70000", error.getMessage());
    }

    // null, empty text "" and the values in ValueSource are all things that are not a plain whole number
    @ParameterizedTest(name = "[{index}] value = {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"abc", "80.5", " 443", "4 43", "-"})
    @DisplayName("parsePort rejects null, empty and not-a-number values")
    void parsePortRejectsBadText(String badValue) {
        // Arrange: the bad value comes from the sources above

        // Act and Assert: the call must throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PortParser.parsePort(badValue));
    }

    // ------------------------------------------------------------
    // 5. The rest of the methods: normal case, boundaries, failure case
    // ------------------------------------------------------------

    // ---- parsePort ----

    @Test
    @DisplayName("parsePort returns the number for a normal port")
    void parsePortReturnsNormalPort() {
        // Arrange
        String value = "443";

        // Act
        int port = PortParser.parsePort(value);

        // Assert
        assertEquals(443, port);
    }

    @ParameterizedTest(name = "text {0} -> number {1}")
    @CsvSource({
            "1, 1",
            "65535, 65535"
    })
    @DisplayName("parsePort returns the number for the lowest and the highest valid port")
    void parsePortReturnsEdgePorts(String value, int expectedPort) {
        // Arrange: the text and the expected number come from the CsvSource row

        // Act
        int port = PortParser.parsePort(value);

        // Assert
        assertEquals(expectedPort, port);
    }

    @ParameterizedTest(name = "port {0} is rejected")
    @ValueSource(strings = {"0", "65536", "-1", "2147483648"})
    @DisplayName("parsePort rejects ports outside 1 to 65535 and numbers too big for an int")
    void parsePortRejectsOutOfRange(String value) {
        // Arrange: the value comes from the ValueSource

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> PortParser.parsePort(value));
    }

    // ---- isHighPriority ----

    @Test
    @DisplayName("Priority 1 is high priority")
    void priorityOneIsHigh() {
        // Arrange
        int priority = 1;

        // Act
        boolean high = PolicyUtils.isHighPriority(priority);

        // Assert
        assertTrue(high);
    }

    @ParameterizedTest(name = "priority {0} -> high = {1}")
    @CsvSource({
            "0, false",
            "1, true",
            "2, true",
            "3, false"
    })
    @DisplayName("Priority boundaries: only 1 and 2 are high")
    void priorityBoundaries(int priority, boolean expectedHigh) {
        // Arrange: the priority and the expected answer come from the CsvSource row

        // Act
        boolean high = PolicyUtils.isHighPriority(priority);

        // Assert
        assertEquals(expectedHigh, high);
    }

    @ParameterizedTest(name = "priority {0} is not high")
    @ValueSource(ints = {-1, Integer.MIN_VALUE, Integer.MAX_VALUE})
    @DisplayName("Negative and very large priorities are not high")
    void invalidPrioritiesAreNotHigh(int priority) {
        // Arrange: the priority comes from the ValueSource

        // Act
        boolean high = PolicyUtils.isHighPriority(priority);

        // Assert
        assertFalse(high);
    }

    // ---- formatPolicyName ----

    @Test
    @DisplayName("Policy name gets POLICY: in front and is changed to upper case")
    void formatPolicyNameNormalCase() {
        // Arrange
        String name = "allow web";

        // Act
        String formatted = PolicyUtils.formatPolicyName(name);

        // Assert
        assertEquals("POLICY: ALLOW WEB", formatted);
    }

    @Test
    @DisplayName("An empty policy name gives just the POLICY: prefix")
    void formatPolicyNameEmptyName() {
        // Arrange
        String name = "";

        // Act
        String formatted = PolicyUtils.formatPolicyName(name);

        // Assert
        assertEquals("POLICY: ", formatted);
    }

    @Test
    @DisplayName("A name that is already upper case stays the same")
    void formatPolicyNameAlreadyUpperCase() {
        // Arrange
        String name = "DENY ALL";

        // Act
        String formatted = PolicyUtils.formatPolicyName(name);

        // Assert
        assertEquals("POLICY: DENY ALL", formatted);
    }

    // this checks the behavior as it is today: the method does not handle null
    @Test
    @DisplayName("A null policy name throws NullPointerException")
    void formatPolicyNameNullName() {
        // Arrange
        String name = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> PolicyUtils.formatPolicyName(name));
    }

    // ---- shouldApplyRule ----

    @Test
    @DisplayName("Rule applies when source, port and protocol all match")
    void ruleAppliesWhenAllThreeMatch() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertTrue(applies);
    }

    @ParameterizedTest(name = "request port {0} against a rule on port 80 -> applies = {1}")
    @CsvSource({
            "79, false",
            "80, true",
            "81, false"
    })
    @DisplayName("Port must match exactly: the ports next to 80 do not apply")
    void portMustMatchExactly(int requestPort, boolean expected) {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, requestPort, "TCP", rules);

        // Assert
        assertEquals(expected, applies);
    }

    @Test
    @DisplayName("Rule does not apply when only the port differs")
    void ruleDoesNotApplyWhenOnlyPortDiffers() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, 443, "TCP", rules);

        // Assert
        assertFalse(applies);
    }

    @Test
    @DisplayName("Rule does not apply when only the protocol differs")
    void ruleDoesNotApplyWhenOnlyProtocolDiffers() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "UDP", rules);

        // Assert
        assertFalse(applies);
    }

    @Test
    @DisplayName("Wildcard source does not help when the port is wrong")
    void wildcardSourceDoesNotHelpWhenPortIsWrong() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("any", "*", HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(OTHER_IP, 443, "TCP", rules);

        // Assert
        assertFalse(applies);
    }

    @Test
    @DisplayName("Rule applies when only the second rule in the list matches")
    void secondRuleInListMatches() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(new FirewallRule("dns", "192.168.1.10", 53, "UDP"));
        rules.add(new FirewallRule("web", WEB_IP, HTTP_PORT, "TCP"));

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertTrue(applies);
    }

    @Test
    @DisplayName("Rule does not apply when the rule list is empty")
    void emptyRuleListDoesNotApply() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertFalse(applies);
    }

    @Test
    @DisplayName("Rule does not apply when the rule list is null")
    void nullRuleListDoesNotApply() {
        // Arrange
        List<FirewallRule> rules = null;

        // Act
        boolean applies = PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "TCP", rules);

        // Assert
        assertFalse(applies);
    }

    // this checks the behavior as it is today: a null rule inside the list crashes the method
    // (the risk I found on Day 3)
    @Test
    @DisplayName("A null rule inside the list throws NullPointerException")
    void nullRuleInsideListThrows() {
        // Arrange
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(null);

        // Act and Assert
        assertThrows(NullPointerException.class,
                () -> PolicyUtils.shouldApplyRule(WEB_IP, HTTP_PORT, "TCP", rules));
    }

    // ------------------------------------------------------------
    // helper
    // ------------------------------------------------------------

    // true if parsePort takes the value, false if it throws IllegalArgumentException
    // (this keeps the try/catch out of the test, so the test stays Arrange-Act-Assert)
    private static boolean isAccepted(String port) {
        try {
            PortParser.parsePort(port);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
