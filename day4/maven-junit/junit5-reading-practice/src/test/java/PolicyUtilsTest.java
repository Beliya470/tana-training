import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// practice for the JUnit 5 User Guide, Writing Tests:
// @Test, assertEquals, assertTrue, assertFalse, assertThrows, @BeforeEach, @DisplayName
// and @ParameterizedTest with @CsvSource
@DisplayName("PolicyUtils practice")
public class PolicyUtilsTest {

    private List<FirewallRule> rules;

    // runs before EACH test, so every test starts with a fresh list
    @BeforeEach
    void setUp() {
        rules = new ArrayList<>();
        rules.add(new FirewallRule("web", "10.0.0.5", 80, "TCP"));
        rules.add(new FirewallRule("ssh", "*", 22, "tcp"));
    }

    // assertEquals(expected, actual)
    @Test
    @DisplayName("formatPolicyName puts POLICY: in front and uses upper case")
    void formatPolicyNameUsesUpperCase() {
        assertEquals("POLICY: ALLOW WEB", PolicyUtils.formatPolicyName("allow web"));
    }

    // assertEquals with the optional failure message as the LAST parameter
    @Test
    @DisplayName("assertEquals with a failure message")
    void assertEqualsWithMessage() {
        assertEquals("POLICY: DNS", PolicyUtils.formatPolicyName("dns"),
                "The name should be in upper case");
    }

    // assertTrue
    @Test
    @DisplayName("priority 1 is high priority")
    void priorityOneIsHigh() {
        assertTrue(PolicyUtils.isHighPriority(1));
    }

    // assertFalse
    @Test
    @DisplayName("priority 3 is not high priority")
    void priorityThreeIsNotHigh() {
        assertFalse(PolicyUtils.isHighPriority(3));
    }

    @Test
    @DisplayName("a rule matches when source, port and protocol match")
    void ruleMatches() {
        assertTrue(PolicyUtils.shouldApplyRule("10.0.0.5", 80, "TCP", rules));
    }

    @Test
    @DisplayName("protocol ignores case")
    void protocolIgnoresCase() {
        assertTrue(PolicyUtils.shouldApplyRule("8.8.8.8", 22, "TCP", rules));
    }

    @Test
    @DisplayName("an empty list never matches")
    void emptyListDoesNotMatch() {
        assertFalse(PolicyUtils.shouldApplyRule("10.0.0.5", 80, "TCP", new ArrayList<>()));
    }

    // assertThrows: a null rule inside the list crashes the method with a NullPointerException
    // (this is the real risk I found on Day 3). The test checks this current behavior.
    @Test
    @DisplayName("a null rule inside the list throws NullPointerException")
    void nullRuleInsideListThrows() {
        rules.add(null);
        assertThrows(NullPointerException.class,
                () -> PolicyUtils.shouldApplyRule("1.1.1.1", 443, "TCP", rules));
    }

    // @ParameterizedTest runs the same test once for each @CsvSource row
    @ParameterizedTest(name = "priority {0} -> high priority is {1}")
    @CsvSource({
            "1, true",
            "2, true",
            "3, false",
            "0, false",
            "-1, false"
    })
    @DisplayName("isHighPriority with many priorities")
    void isHighPriorityWithManyValues(int priority, boolean expected) {
        assertEquals(expected, PolicyUtils.isHighPriority(priority));
    }

    // the four values of each row go to the four parameters in order
    @ParameterizedTest(name = "{0}, {1}, {2} -> {3}")
    @CsvSource({
            "10.0.0.5, 80, TCP, true",
            "8.8.8.8, 22, TCP, true",
            "8.8.8.8, 22, TcP, true",
            "10.0.0.5, 443, TCP, false",
            "1.1.1.1, 80, TCP, false",
            "10.0.0.5, 80, UDP, false"
    })
    @DisplayName("shouldApplyRule with many cases")
    void shouldApplyRuleWithManyCases(String sourceIp, int port, String protocol, boolean expected) {
        assertEquals(expected, PolicyUtils.shouldApplyRule(sourceIp, port, protocol, rules));
    }
}
