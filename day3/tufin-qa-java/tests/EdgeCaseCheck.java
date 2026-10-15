import java.util.ArrayList;
import java.util.List;

public class EdgeCaseCheck {
    public static void main(String[] args) {

        // the rules used in most cases: source 10.0.0.5, port 80, protocol TCP
        List<FirewallRule> one = new ArrayList<>();
        one.add(new FirewallRule("web", "10.0.0.5", 80, "TCP"));

        List<FirewallRule> anyIp = new ArrayList<>();
        anyIp.add(new FirewallRule("any", "*", 80, "TCP"));

        List<FirewallRule> many = new ArrayList<>();
        many.add(new FirewallRule("a", "1.1.1.1", 22, "UDP"));
        many.add(new FirewallRule("b", "2.2.2.2", 53, "UDP"));
        many.add(new FirewallRule("c", "10.0.0.5", 80, "TCP"));

        List<FirewallRule> empty = new ArrayList<>();

        String[] names = {
            "all three match",
            "only source fails",
            "only port fails",
            "only protocol fails",
            "protocol lower case",
            "protocol mixed case",
            "rule source * matches any ip",
            "rule source * but port fails",
            "rule source * but protocol fails",
            "empty rule list",
            "null rule list",
            "only the last rule matches",
            "no rule matches",
            "ip is a prefix of the rule ip",
            "empty ip text",
            "null ip, exact rule",
            "null ip, rule source *",
            "null protocol",
            "port 0 (rule has port 0 too)",
            "port 65535 (rule has 65535 too)",
            "negative port (rule has -1 too)"
        };

        List<List<FirewallRule>> lists = new ArrayList<>();
        String[] ips = {
            "10.0.0.5", "9.9.9.9", "10.0.0.5", "10.0.0.5", "10.0.0.5", "10.0.0.5",
            "8.8.8.8", "8.8.8.8", "8.8.8.8", "10.0.0.5", "10.0.0.5", "10.0.0.5",
            "7.7.7.7", "10.0.0.50", "", null, null, "10.0.0.5", "10.0.0.5", "10.0.0.5", "10.0.0.5"
        };
        int[] ports = {
            80, 80, 443, 80, 80, 80,
            80, 443, 80, 80, 80, 80,
            80, 80, 80, 80, 80, 80, 0, 65535, -1
        };
        String[] protocols = {
            "TCP", "TCP", "TCP", "UDP", "tcp", "TcP",
            "TCP", "TCP", "UDP", "TCP", "TCP", "TCP",
            "TCP", "TCP", "TCP", "TCP", "TCP", null, "TCP", "TCP", "TCP"
        };
        boolean[] expected = {
            true, false, false, false, true, true,
            true, false, false, false, false, true,
            false, false, false, false, true, false, true, true, true
        };

        lists.add(one);      // all three match
        lists.add(one);      // only source fails
        lists.add(one);      // only port fails
        lists.add(one);      // only protocol fails
        lists.add(one);      // lower case
        lists.add(one);      // mixed case
        lists.add(anyIp);    // *
        lists.add(anyIp);    // * port fails
        lists.add(anyIp);    // * protocol fails
        lists.add(empty);    // empty list
        lists.add(null);     // null list
        lists.add(many);     // last rule matches
        lists.add(many);     // none matches
        lists.add(one);      // prefix ip
        lists.add(one);      // empty ip
        lists.add(one);      // null ip, exact
        lists.add(anyIp);    // null ip, *
        lists.add(one);      // null protocol
        lists.add(new ArrayList<>(List.of(new FirewallRule("p0", "*", 0, "TCP")))); // port 0
        lists.add(new ArrayList<>(List.of(new FirewallRule("p1", "*", 65535, "TCP")))); // 65535
        lists.add(new ArrayList<>(List.of(new FirewallRule("p2", "*", -1, "TCP")))); // -1

        int passed = 0;
        int failed = 0;
        for (int i = 0; i < names.length; i++) {
            boolean actual = PolicyUtils.shouldApplyRule(ips[i], ports[i], protocols[i], lists.get(i));
            String status = (actual == expected[i]) ? "PASS" : "FAIL";
            if (actual == expected[i]) {
                passed++;
            } else {
                failed++;
            }
            System.out.println(status + "  " + names[i] + "  expected=" + expected[i] + " actual=" + actual);
        }
        System.out.println(passed + " passed, " + failed + " failed");
    }
}
