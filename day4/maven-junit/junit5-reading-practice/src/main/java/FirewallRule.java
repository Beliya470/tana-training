public class FirewallRule {

    private String name;
    private String sourceRange;
    private int port;
    private String protocol;

    public FirewallRule(String name, String sourceRange, int port, String protocol) {
        this.name = name;
        this.sourceRange = sourceRange;
        this.port = port;
        this.protocol = protocol;
    }

    public String getName() {
        return name;
    }

    public String getSourceRange() {
        return sourceRange;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "FirewallRule{name=" + name
                + ", sourceRange=" + sourceRange
                + ", port=" + port
                + ", protocol=" + protocol + "}";
    }
}
