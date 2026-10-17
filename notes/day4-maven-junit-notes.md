# Day 4 notes: Maven and JUnit 5

From the three Udemy Maven lessons, Maven in 5 Minutes and the JUnit 5 User Guide (Writing Tests). Simple words.

## Maven project (lesson: Create First Maven Project)
- Maven is a build management tool. A Maven project has three mandatory details:
  - archetype: the project structure. Maven has many ready ones. For automation I make a simple project, so I do not choose an archetype (the "create a simple project" tick box in Eclipse).
  - artifactId: the name of the project.
  - groupId: a unique ID for the project. In the lesson: `com.test` and `QA-automation`.
- The groupId and artifactId are in `pom.xml`.
- Folders:
  - `src/main/java`: framework code.
  - `src/main/resources`: files the framework code needs.
  - `src/test/java`: the test classes and test cases.
  - `src/test/resources`: files with test data.
  - `target`: the compiled classes and the reports. It was empty before I ran anything.
  - `pom.xml`: the main file of the project.
- A Maven dependency is just a jar file. I find them on mvnrepository.com, copy the dependency code, and paste it inside `<dependencies>` in `pom.xml`.
- The lesson added Selenium Java 4.11.0 and TestNG 7.8.0.
- After editing `pom.xml`, Maven downloads the jar files (in Eclipse: right click project, Maven, Update Project; in the terminal Maven does it when I run a command).
- The jar files go to the `.m2` folder in my user folder. It is the local Maven repository. I checked: `C:\Users\user\.m2\repository\org\seleniumhq\selenium\selenium-java\4.11.0\selenium-java-4.11.0.jar`.
- For IntelliJ the lesson says use New Project, then Maven. I used VS Code and the terminal, so I made the folders and `pom.xml` by hand.

## pom.xml (lesson: Understanding the pom.xml file)
- POM means Project Object Model. It is an XML file. Without it the Maven project does not work. Do not delete or rename it.
- When I run a Maven project, Maven reads the pom, checks that every dependency is in the local repository, and downloads it if not. If the pom has a syntax error, nothing runs.
- What is inside:
  - Project information: groupId, artifactId, packaging (jar or war). Packaging is the format used when I run `mvn package`. It is not very useful for automation. The lesson added `<packaging>jar</packaging>` by hand because Eclipse did not generate it.
  - Dependencies: each has groupId, artifactId, version, and sometimes a scope.
  - Scope: says when the dependency is used. `compile` means while compiling the code. `test` means only while running the test scripts. TestNG had scope `test`.
  - Plugins: tools that do a task like compile, run or package the code. For automation there are two main ones: the Maven compiler plugin (compiles the code) and the Surefire plugin (runs the tests).

## Maven test phase and Surefire plugin
- The test phase runs the test cases. The command is `mvn test`.
- In the lesson the steps are:
  1. Make a TestNG XML file for the tests (`testng.xml`, the name Eclipse gives when I convert the class to TestNG; it lists the test classes).
  2. Write the Surefire plugin in the pom, inside `<plugins>` (the lesson used version 3.1.2).
  3. Put the XML file name in the plugin: `<suiteXmlFiles><suiteXmlFile>testng.xml</suiteXmlFile></suiteXmlFiles>`.
  4. Run the `test` goal. Maven compiles the code, then Surefire runs the XML file.
- The lesson result: 3 tests run, 0 failures, 0 errors, 0 skipped, BUILD SUCCESS. Mine gave the same.
- The Orange test in the lesson opens a browser with Selenium. I do not have Selenium code yet (Day 5), so my three test cases only print their step.
- The compiler plugin was taught in the video before these three, so I wrote it myself with the Java release number (17).

## Maven in 5 Minutes
- Check Maven with `mvn --version`.
- A goal is one task from a plugin (like `archetype:generate`). A phase is one step of the build lifecycle (like `package`). When I run a phase, Maven runs all the phases before it. The default phases: validate, compile, test, package, integration-test, verify, install, deploy.
- The standard layout is `src/main/java` and `src/test/java`.
- With Java 9 or newer, set `maven.compiler.release` (I used 17).

## JUnit 5 (Writing Tests)
- JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage. Jupiter is the part I use to write tests. Vintage runs old JUnit 3 and 4 tests.
- The current guide page is for version 6.1.3, but I am working with JUnit 5, so I used the 5.13.4 guide and dependency. The annotations and assertions I use are the same.
- Dependency: `org.junit.jupiter:junit-jupiter` with scope `test`. It brings the API, the engine and `junit-jupiter-params` (needed for `@ParameterizedTest`). Surefire must be a new version (2.22 or newer) to run JUnit 5. I used 3.2.5.
- `@Test`: marks a method as a test method.
- `@BeforeEach`: runs before each test method, so every test starts fresh.
- `@DisplayName("...")`: a custom name for the test class or method. It shows in the report. It always wins over the default name.
- Assertions are static methods of `org.junit.jupiter.api.Assertions`:
  - `assertEquals(expected, actual)`: expected goes first. The optional failure message is the last parameter.
  - `assertTrue(condition)` and `assertFalse(condition)`.
  - `assertThrows(Type.class, () -> code)`: checks that this exception is thrown. It also gives me the exception, so I can check its message.
- `@ParameterizedTest`: runs one test method many times with different values. Each run is reported as its own test.
- `@CsvSource`: each string is one row, and the comma separated values go to the method parameters in order. Single quotes keep a comma inside one value. An empty quoted value `''` is an empty String; an unquoted empty value is `null`.
- The guide page does not show `assertFalse`. It works the opposite way to `assertTrue`.

## Reading the failure output
The failing test printed:

    CheckpointTest.policyNameFailsOnPurpose:17 expected: <POLICY: allow web> but was: <POLICY: ALLOW WEB>

- Which line failed: `CheckpointTest.java`, line 17 (the line with `assertEquals`).
- Expected: `POLICY: allow web` (what I wrote in the test, on purpose).
- Actual: `POLICY: ALLOW WEB` (what the method gave back).
- At the end: `Tests run: 2, Failures: 1, Errors: 0, Skipped: 0` and BUILD FAILURE. The build fails when a test fails.
- A failure means the check was wrong. An error would mean the test crashed with an exception.
