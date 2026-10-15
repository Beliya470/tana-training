import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Step 1: FirewallRule
        System.out.println("--- FirewallRule ---");
        FirewallRule web = new FirewallRule("web", "10.0.0.5", 80, "TCP");
        FirewallRule ssh = new FirewallRule("ssh", "*", 22, "tcp");
        FirewallRule dns = new FirewallRule("dns", "192.168.1.10", 53, "UDP");
        System.out.println(web);
        System.out.println(ssh.getName() + " " + ssh.getSourceRange() + " "
                + ssh.getPort() + " " + ssh.getProtocol());

        // Step 2: isHighPriority
        System.out.println("--- isHighPriority ---");
        System.out.println("priority 1: " + PolicyUtils.isHighPriority(1));
        System.out.println("priority 2: " + PolicyUtils.isHighPriority(2));
        System.out.println("priority 3: " + PolicyUtils.isHighPriority(3));

        // Step 3: formatPolicyName
        System.out.println("--- formatPolicyName ---");
        System.out.println(PolicyUtils.formatPolicyName("allow web"));

        // Step 4: shouldApplyRule
        System.out.println("--- shouldApplyRule ---");
        List<FirewallRule> rules = new ArrayList<>();
        rules.add(web);
        rules.add(ssh);
        rules.add(dns);

        // matching: exact IP, port and protocol
        System.out.println("10.0.0.5, 80, TCP: "
                + PolicyUtils.shouldApplyRule("10.0.0.5", 80, "TCP", rules));
        // matching: rule source is *, protocol in different case
        System.out.println("8.8.8.8, 22, TCP: "
                + PolicyUtils.shouldApplyRule("8.8.8.8", 22, "TCP", rules));
        // not matching: wrong port
        System.out.println("10.0.0.5, 443, TCP: "
                + PolicyUtils.shouldApplyRule("10.0.0.5", 443, "TCP", rules));
        // not matching: wrong source IP
        System.out.println("1.1.1.1, 80, TCP: "
                + PolicyUtils.shouldApplyRule("1.1.1.1", 80, "TCP", rules));

        // Step 5: RuleSet
        System.out.println("--- RuleSet ---");
        RuleSet ruleSet = new RuleSet();
        ruleSet.addRule(web);
        ruleSet.addRule(ssh);
        ruleSet.addRule(dns);
        ruleSet.addRule(new FirewallRule("web2", "*", 80, "TCP"));

        System.out.println("Rules on port 80:");
        for (FirewallRule rule : ruleSet.findByPort(80)) {
            System.out.println(rule);
        }
        System.out.println("Count by protocol: " + ruleSet.countByProtocol());
    }
}
