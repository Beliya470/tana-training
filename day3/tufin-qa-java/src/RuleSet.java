import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleSet {

    private List<FirewallRule> rules = new ArrayList<>();

    public void addRule(FirewallRule rule) {
        rules.add(rule);
    }

    public List<FirewallRule> getRules() {
        return rules;
    }

    // all rules that use this port
    public List<FirewallRule> findByPort(int port) {
        List<FirewallRule> found = new ArrayList<>();
        for (FirewallRule rule : rules) {
            if (rule.getPort() == port) {
                found.add(rule);
            }
        }
        return found;
    }

    // how many rules for each protocol, for example {TCP=3, UDP=1}
    public Map<String, Integer> countByProtocol() {
        Map<String, Integer> counts = new HashMap<>();
        for (FirewallRule rule : rules) {
            String protocol = rule.getProtocol().toUpperCase();
            if (counts.containsKey(protocol)) {
                counts.put(protocol, counts.get(protocol) + 1);
            } else {
                counts.put(protocol, 1);
            }
        }
        return counts;
    }
}
