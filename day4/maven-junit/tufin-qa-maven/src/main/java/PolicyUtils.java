import java.util.List;

public class PolicyUtils {

    // priority 1 or 2 is high priority
    public static boolean isHighPriority(int priority) {
        return priority == 1 || priority == 2;
    }

    // uppercase the name and put "POLICY: " in front
    public static String formatPolicyName(String name) {
        return "POLICY: " + name.toUpperCase();
    }

    // true if any rule matches on source, port and protocol
    public static boolean shouldApplyRule(String sourceIp, int destinationPort,
                                          String protocol, List<FirewallRule> rules) {
        if (rules == null) {
            return false;
        }

        for (FirewallRule rule : rules) {
            boolean sourceMatches = rule.getSourceRange().equals("*")
                    || rule.getSourceRange().equals(sourceIp);
            boolean portMatches = rule.getPort() == destinationPort;
            boolean protocolMatches = rule.getProtocol().equalsIgnoreCase(protocol);

            if (sourceMatches && portMatches && protocolMatches) {
                return true;
            }
        }
        return false;
    }
}
