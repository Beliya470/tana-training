# Edge cases for shouldApplyRule

The method returns true if any rule in the list matches on all three: source, port and protocol.

- Source matches if the rule's sourceRange is the exact IP or "*".
- Port must match exactly.
- Protocol ignores upper and lower case.

## My list (written first, before the AI)

### Only one condition fails

| # | Case | Expected |
|---|------|----------|
| 1 | Source wrong, port and protocol right | false |
| 2 | Port wrong, source and protocol right | false |
| 3 | Protocol wrong, source and port right | false |

### Edge cases

| # | Case | Expected |
|---|------|----------|
| 4 | All three match | true |
| 5 | Protocol in lower case (`tcp` against `TCP`) | true |
| 6 | Protocol in mixed case (`TcP`) | true |
| 7 | Rule source is `*` and any IP is used | true |
| 8 | Rule source is `*` but the port is wrong | false |
| 9 | Rule source is `*` but the protocol is wrong | false |
| 10 | Empty rule list | false |
| 11 | Null rule list | false |
| 12 | Only the last rule in the list matches | true |
| 13 | No rule in the list matches | false |
| 14 | IP is only the start of the rule IP (`10.0.0.50` against `10.0.0.5`) | false |
| 15 | Empty IP text | false |
| 16 | Null IP, rule has an exact IP | false |
| 17 | Null IP, rule source is `*` | true |
| 18 | Null protocol | false |
| 19 | Port 0, 65535 and -1 when the rule has the same port | true (the method does not check the port range) |

I wrote these as a PASS/FAIL check in `tests/EdgeCaseCheck.java`. Result: **21 passed, 0 failed**.

## AI task

### My exact prompt

```
Here is my Java method. It is used to check if a firewall rule applies to a
network connection. Please explain what it does, then list edge cases,
boundary conditions and negative cases worth testing, with a reason for each.
Think about the three conditions: source (exact IP or "*"), port (exact match)
and protocol (case-insensitive), and about the rule list itself.

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
```

### What the AI said the method does

It goes through the rules one by one. For each rule it checks source, port and protocol. As soon as one rule matches on all three, it returns true. If no rule matches, or the list is null or empty, it returns false.

### The AI's list

1. **Empty list.** Nothing to match, so it should return false.
2. **Null list.** The method has a null check, so it should return false and not crash.
3. **Null rule inside the list.** `rule.getSourceRange()` would throw a NullPointerException.
4. **Rule with a null field.** A rule with a null sourceRange or protocol would throw a NullPointerException.
5. **Source only fails.** Right port and protocol, wrong IP. Proves the source check is really used.
6. **Port only fails.** Right IP and protocol, wrong port. Same reason.
7. **Protocol only fails.** Right IP and port, wrong protocol. Same reason.
8. **Protocol case.** `tcp`, `TCP` and `TcP` should all match.
9. **Protocol with spaces.** `" TCP"` or `"TCP "` will not match, because the method does not trim.
10. **Wildcard source.** A rule with `*` should match any IP.
11. **Wildcard typed as the input IP.** If the input IP is `*` and the rule has an exact IP, it should not match.
12. **Null input IP.** An exact rule should give false. A `*` rule gives true.
13. **IP that is only the start of another IP.** `10.0.0.50` must not match `10.0.0.5`.
14. **IP ranges (CIDR) like `10.0.0.0/24`.** The method only does exact match or `*`, so a CIDR rule will not match any real IP. Worth a test so people know it is not supported.
15. **Port boundaries.** 0, 1, 65535, 65536 and negative numbers. The method does not check the range.
16. **Several rules where only the last one matches.** Proves the loop does not stop early.
17. **Several rules where the first one matches.** Proves it returns true right away.
18. **Duplicate rules.** The result should be the same as one rule.
19. **IPv6 addresses.** A rule with an IPv6 address should match only the exact same text.
20. **Very large list.** Check it still returns the right answer with thousands of rules.

### My notes after comparing

Things the AI had that I did **not** have in my first list:

- Null rule inside the list (#3). I tried it after reading this. It really does crash with a NullPointerException at `rule.getSourceRange()`.
- Rule with a null field (#4). Same crash.
- Protocol with spaces (#9). Not trimmed, so `" TCP"` does not match.
- `*` typed as the input IP (#11).
- CIDR ranges (#14). Not supported.
- Duplicate rules, IPv6, and a very large list (#18 to #20).
- Port 1 and 65536 (#15). I only tested 0, 65535 and -1.

Things I had that the AI did not list:

- A `*` rule where the port or protocol is wrong (my #8 and #9). The AI only tested `*` when it should match.
- Empty IP text (my #15).
- Null protocol (my #18).

What I learned: my list was good on the three conditions, but I forgot about bad data inside the list (null rules, null fields). The prompt worked because it named the three conditions and the rule list, so the answer was about this method and not generic.

I did not change `shouldApplyRule`. The null rule crash is a real bug risk to mention to the trainer.
