import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckpointTest {

    // this test passes: priority 1 is high priority, so the result is true
    @Test
    void highPriorityPasses() {
        assertEquals(true, PolicyUtils.isHighPriority(1));
    }

    // this test fails on purpose: I wrote the expected text in lower case,
    // but formatPolicyName gives upper case
    @Test
    void policyNameFailsOnPurpose() {
        assertEquals("POLICY: allow web", PolicyUtils.formatPolicyName("allow web"));
    }
}
