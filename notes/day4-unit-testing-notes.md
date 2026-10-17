# Day 4 notes: unit tests for PolicyUtils and parsePort

Notes from the Microsoft Learn page "Best practices for writing unit tests", the JUnit 5.13.4 User Guide (Writing Tests, parameterized tests, assertions), and what I found while writing the tests.

## Arrange, Act, Assert (AAA)
- Arrange: create and set up the objects and the data.
- Act: call the one method I am testing.
- Assert: check that the result is what I expected.
- Why: it separates what is being tested from the setup and the checks, so the test is easy to read. Readability is the main goal of a test.
- I write the three comments in every test. When the Act and the Assert are one line (assertThrows), I write `// Act and Assert`.
- One Act per test. If a test has two Acts and the first assert fails, I never see the second one, and I do not know which call is wrong.

## Naming and display names
- Microsoft says a test name has three parts: the method, the scenario and the expected behavior. Example: `Add_EmptyString_ReturnsZero`.
- In JUnit 5, `@DisplayName` lets me write that as a sentence with spaces, like a test case title. It always wins over the method name. Example: `Rule does not apply when only the port differs`.
- The method name still matters because it shows in the stack trace, so I keep it short and clear too.
- For `@ParameterizedTest`, the `name` attribute sets the name of every run. It is a `MessageFormat` pattern. Placeholders: `{index}` (starts at 1), `{0}` `{1}` (each argument), `{arguments}`, `{displayName}`.
- Note: a number argument like `65535` can be shown with a comma by MessageFormat. I made the port parameter a `String`, so it shows exactly as written.

## What a good unit test is
- Fast, isolated, repeatable, self-checking, timely.
- Executable documentation: reading the tests tells me what the method does with a blank string or null.
- Do not put logic (`if`, loops, `try`) in the test. If a test has logic, the test can have a bug. I moved the `try/catch` of the port boundaries into a small helper method `isAccepted`, so the test body stays a straight line.
- Use simple inputs, and give repeated values a name (`WEB_IP`) instead of "magic strings".
- Test the public behavior, not the private parts.
- Microsoft prefers helper methods over setup methods (`@BeforeEach`) because everything is visible in the test. I followed that here and built each rule list inside the test.
- High code coverage does not mean good tests. It only says which lines ran.

## Test design: from QA skill to code
Equivalence classes: inputs in one class behave the same, so I test one value from each class. Boundaries: the bugs are at the edges of a class, so I test the value on each side of an edge.

| Method | Classes | Boundaries and edges | Failure cases |
|---|---|---|---|
| `parsePort` | a number in 1 to 65535 (443) | 0, 1, 65535, 65536 | text (`abc`, `80.5`, ` 443`, `4 43`, `-`), empty, null, too small (0, -1), too big (65536, 70000), too big for an int (2147483648) |
| `isHighPriority` | high (1, 2), not high (3 and up, 0 and down) | 0, 1, 2, 3 | -1, Integer.MIN_VALUE, Integer.MAX_VALUE |
| `formatPolicyName` | a normal name, an already upper case name | empty string | null (throws NullPointerException) |
| `shouldApplyRule` | source: `*`, exact IP, different IP. Port: same, different. Protocol: any case, different. List: one rule, many rules | ports 79, 80, 81 against a rule on 80 | empty list, null list, a null rule inside the list (throws NullPointerException) |

- For `shouldApplyRule` there are three conditions joined with AND (source, port, protocol). The best way to test AND logic is "only one thing differs": keep two conditions right and break one. If the rule then does not apply, that one condition is really being checked. I did this for source, port and protocol.

## JUnit 5 things I used
- `@ParameterizedTest` + `@CsvSource`: each string is one row, and the comma separated values go to the method parameters in order. Spaces around values are trimmed. JUnit converts the text to the parameter type (`"true"` to boolean, `"65535"` to int).
- `@ValueSource(strings = {...})`: one value per run.
- `@NullAndEmptySource`: runs the test with null and with an empty String. It cannot be used on a primitive parameter. I put it together with `@ValueSource` so one test covers null, empty and bad text.
- `assertThrows(Type.class, () -> code)`: passes if that type (or a subclass) is thrown. It returns the exception, so I can check the message with `assertEquals`. `assertThrowsExactly` does not accept a subclass.
- `assertAll` runs several assertions and reports all the failures together. I did not need it because I keep one assertion per test.
- Test classes and methods do not have to be `public`, but they must not be `private`.

## What I found
- `formatPolicyName(null)` throws a NullPointerException. The method does not check for null.
- `shouldApplyRule` is safe for a null list, but a null rule inside the list throws a NullPointerException (I found this on Day 3).
- `parsePort(" 443")` (with a space) is rejected, because `Integer.parseInt` does not accept spaces. I decided this is fine for a port, so the test says it is rejected.
- I did not change any of this code. Tests like `A null rule inside the list throws NullPointerException` write down the behavior as it is today. If the code is fixed one day, the test fails on purpose and I update it.

## How I checked that the tests are good
- All 51 test runs (27 test methods, some with many rows) pass.
- Mutation check by hand: I broke the code in 14 small ways, one at a time, in a throw-away copy (`>` to `>=`, `||` to `&&`, remove a null check, and so on). The tests failed every time, so none of the 14 broken versions survived. My first run showed two survivors, but that was my script changing a code comment and not matching Windows line endings. When I fixed the script, both were caught.
- Coverage with JaCoCo (run once from the command line, not added to the pom): `PolicyUtils` 20 of 20 branches and 13 of 14 lines; `PortParser` 4 of 4 branches and 7 of 8 lines. The one missed line in each is the hidden empty constructor of a class with only static methods.
- Lesson: a green suite only means the tests agree with the code. The mutation check shows the tests can also say no.

## What I changed in the project
- Added `PortParser.java` (the `parsePort` method from the Exceptions task) to `src/main/java` of `tufin-qa-maven`, so it can be tested.
- Removed `CheckpointTest.java`, the test that fails on purpose. It was for the earlier task (one pass, one fail). This task needs a fully green `mvn test`. It is still in the git history.
- Added `PolicyUtilsTest.java` in `src/test/java`.
