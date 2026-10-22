# Day 7 morning: red-test triage and CI

## Reading: Debugging Techniques for QA Automation Engineers (DEV Community)
What I took from it, in the order of the article:
- When a test fails, the problem is not always the product. Sometimes the test script itself is wrong.
- Logs and screenshots on failure show what the page looked like. Breakpoints in the IDE let me stop on one line and look at the variables.
- The Jenkins build log has the stack trace and the logs of the test run, so I can read a failure there without running anything on my computer.
- Run the failing test alone and several times. If it fails once and passes the next time, it is a flake and not a bug. The article also says to use explicit waits and never `Thread.sleep`, which is what I did in my Day 6 login tests.
- "Don't ignore stack traces": the first lines tell me what failed and where.

## Normal run of my API suite (from the terminal, not the IDE)
I ran `mvn test` in `day5/toolshop-api-tests` on `main`:

    Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
    BUILD SUCCESS   Total time: 03:16 min

This is what a normal run looks like for me: 8 tests, no failures. It took more than 3 minutes today because the Toolshop API answered slowly, so a slow run is not a failure by itself. This is the picture I compare the three red runs with.

## Triage drill

| Test | Classification | Evidence |
|---|---|---|
| `getBrandsReturnsList` | Test defect | `Expected status code <201> but was <200>` and the test line `get("/brands").then().statusCode(201)`. A GET that reads a list must answer 200. 201 means "created" and belongs to a POST. My own test TF40-73 expects 200 and passes. |
| `loginReturnsToken` | Test defect (it looks like an environment issue, but it is not) | `UnknownHost api.practicesoftwaretesting.co: Name or service not known` and `baseURI set in BaseApiTest: https://api.practicesoftwaretesting.co`. The host ends in `.co`, the real one ends in `.com`. The typo is in my own test setup. The `.com` host answers (I got 200 from `/brands`). |
| `unknownProductReturns404` | Product defect | `Expected status code <404> but was <500>`, body `{"message": "Server Error"}`, request `GET /products/does-not-exist`. The test is right to expect 404 for an unknown id. A 500 means the server crashed on the request. |

How I decided each one:
1. **Failure 1:** the number in the message is the whole story. The server did the right thing (200) and my expected value was wrong. I would fix line 31 and not report anything to the developers.
2. **Failure 2:** "Name or service not known" sounds like a network problem. I checked where the host name comes from. It comes from `BaseApiTest`, and the name is misspelled. If the name was correct and still did not resolve, it would be an environment issue. The fix is one letter in one place, which is the reason I have a base class.
3. **Failure 3:** the request is a normal one and the status is a server error with a generic body. The server should answer 404 for an id that does not exist. One caution: when I sent the same request to the live API, I got 404 with `{"message":"Requested item not found"}` 5 times out of 5, and on Day 5 one 500 on an unknown id could not be reproduced in 46 tries. So before I report it I would run the test again and look at the server log. If the 500 comes back every time, it is a product defect. If it never comes back, it was a one-off problem of the environment.

**I reproduced the first two on my own suite (in a copy, nothing changed in the repo):**
- Failure 1: I changed the expected status of my `/brands` test to 201 and ran it. Result: `Expected status code <201> but was <200>.` at `ToolshopApiTest.java:65`. The same message as in the task, from my own code.
- Failure 2: I ran my brands test with `-Dbase.uri=https://api.practicesoftwaretesting.co`. Result: `java.net.UnknownHostException: No such host is known (api.practicesoftwaretesting.co)`. The same kind of error as in the task, caused only by the wrong host name in the setup.
- Failure 3 I could not reproduce (404 five times out of five), as written above.

## CI and Jenkins

**Continuous Integration Explained (Harness)**
- CI means every developer merges code into one shared repository often, many times a day. Each commit starts an automatic build that compiles the code, runs the tests and gives feedback.
- It finds conflicts and bugs early, when they are small and cheap to fix. It removes repeated manual work and keeps the code base stable.
- The hard parts the video names: merge conflicts, a test suite that gets slower as it grows, and builds that act differently on different machines.

**How to Write a Pipeline Script in Jenkins**
- A Jenkins pipeline is a job that is defined in a file, so it can be saved in git and reviewed like code.
- There are two syntaxes: scripted (older, 2014) and declarative (2017). Both did the same "hello world" in the video: the scripted one with about 5 lines, the declarative one with about 11.
- `node`/`agent` says where the job runs, `stage` is a part of the work, and `echo` or `sh` is the step that does something.
- The advice: start with declarative. Use scripted only if nothing else works. Do not use either as a general programming language; write complex things in a real language and call them with `sh`.

**Jenkins Scripted vs Declarative Pipeline**
- Declarative is structured and easy to read and review. It has stages, `post` conditions, environment variables and parallel stages built in, and it checks the syntax before the run starts.
- Scripted is plain Groovy code: loops, if/else, try/catch. It is powerful but harder to read and to maintain.
- Best practice is a mix: declarative as the base, and a `script { }` block in the one stage that needs real code.

**Using a Jenkinsfile (Jenkins docs)** was my reference when I wrote my Jenkinsfile. The parts I used: `pipeline`, `agent`, `stages`/`stage`/`steps`, `sh`, `checkout scm`, `junit` and `post { always { ... } }`. `junit` reads the XML reports of the tests. In the doc, the `junit` step records the failures so Jenkins can show them in the web UI, and a `post { always }` block runs whatever the result of the stages. In my file `mvn test` returns an error code when a test fails, so that stage fails and the next stages are skipped, and `post { always }` still publishes the results. The doc also says that `agent` checks out the source by default; I kept `checkout scm` in the Checkout stage because the task asks for that stage.

### Answers

**In your own words, what is CI and what problem does it solve?**
CI is a robot that builds my code and runs my tests every time somebody pushes a change. Without it, I find out that the code is broken days later, when many changes are mixed and nobody knows which one broke it. With CI, the answer comes a few minutes after the push, and it points to one commit.

**What do `pipeline`, `agent` and `stages` each do in a Jenkinsfile?**
- `pipeline { ... }` is the outer block. It tells Jenkins that everything inside is one declarative pipeline.
- `agent any` says where to run it: on any machine (agent) that is free. Without an agent the pipeline is not valid and cannot do any work.
- `stages { ... }` holds the list of `stage` blocks that run in order. In my Jenkinsfile they are Checkout, Build, API Tests and UI Tests (headless). If one stage fails, the next ones do not run.

**Where do you find test results in a Jenkins build, and what would your first steps be if you saw a red build?**
The test results are recorded by the `junit` step and shown in the web UI of the build, and the full log is in the console output of the build. If the build is red, I would:
1. Open the build and see which stage is red.
2. Open Console Output and go to the first `[ERROR]` line, with the test name and the line number.
3. Decide whose problem it is: my test, the product, or the environment (the same three questions as in my triage table).
4. Run that one test again on my computer to see if it fails every time.
5. Fix my test, log a bug with the evidence, or tell the team about the environment.

**Why does a QA engineer need to understand the CI pipeline even if they don't build it?**
The pipeline is where my tests really run, and nobody sees my computer. When a build is red, I am the one who must say if it is the tests, the product or the server. I cannot do that if I do not know which stage ran what, where the logs are and where the reports are. Also, a test that passes on my machine can fail in CI (for example no screen, a different Chrome, a slower network), and I must know why.

## Headless Chrome
**Execute test script in Chrome headless mode (Udemy, 2:57)**
- The instructor has a script that opens Chrome, goes to the Google page and prints the page title and the current URL. He runs it normally first: Chrome opens on the screen and the console shows the title and the URL.
- To run it headless he makes one change. He creates a `ChromeOptions` object, calls `options.addArguments("--headless")`, and passes the `options` object into the `ChromeDriver` constructor.
- He runs it again: no Chrome window appears, but the console shows the same title and URL. The browser runs in the background and Selenium does the same actions. His point: in normal mode the browser window is visible, in headless mode the browser runs without showing the window.

**What I did with it**
- I added `DriverFactory` to my UI project. It does the same three steps as the lecture: `ChromeOptions`, `addArguments("--headless")`, and the options passed to `new ChromeDriver(options)`. It only does this when I run Maven with `-Dheadless=true`. Without the option it starts a normal window like before. `FirstTest` and `LoginTest` use it.
- The window size `--window-size=1920,1080` is my own addition, it is not in the lecture. I added it so the headless page has a normal desktop size.
- `mvn test -Dheadless=true` in `day6/toolshop-ui-tests`: 3 tests, 0 failures, `BUILD SUCCESS` in 18 seconds.
- Earlier the same day (when I used `--headless=new` instead of the lecture's `--headless`), the same command failed 4 times out of 5. Each time one test stopped for 3 to 4 minutes on `driver.get(...)` with a `TimeoutException`, in the visible Chrome as well as in headless mode, while `curl` loaded the same page in under 1 second. So headless was not the cause: Chrome did not finish loading the page, which I count as an environment issue on my network at that time. I tried `PageLoadStrategy.EAGER` once, it did not fix it for sure (one run passed, the next failed), so I took it out. Later the runs were green again.

## The Jenkinsfile, run in a real Jenkins
`Jenkinsfile` is in the root of the repo. The task says I do not need to run Jenkins, but I did, so that Jenkins itself checks the syntax and the stages.

**How I set it up (on my own computer, only for this test)**
- I downloaded the official `jenkins.war` (LTS 2.580.1, 54 MB) from get.jenkins.io and started it with `java -jar jenkins.war --httpPort=8081`. No installer was needed. I turned off the setup wizard and I stopped Jenkins after the test, because I started it without a login.
- I installed the plugins Pipeline (`workflow-aggregator`), Git, JUnit and Pipeline Stage View.
- I made one Pipeline job, "Pipeline script from SCM", that reads the `Jenkinsfile` from my repo (a local copy of the repo with my Jenkinsfile branch merged with `main`, so it had the refactored 8-test API suite).
- Two things stopped the first builds, and I fixed them: Jenkins refuses to check out from a local folder (I allowed it with `-Dhudson.plugins.git.GitSCM.ALLOW_LOCAL_CHECKOUT=true`), and the Jenkins folder path was too long for Git on Windows (I moved it to `C:\Users\user\jk`). The `sh` steps worked on Windows because Git for Windows has an `sh`.

**Result**
| Build | What happened |
|---|---|
| #3, green | Checkout, Build, API Tests (8 passed), UI Tests headless (3 passed). `junit` published 11 results. |
| #4, red on purpose | I changed the `/brands` test to expect 201 (the same bug as Failure 1 in my triage). The API Tests stage failed with `Expected status code <201> but was <200>`, the UI stage was skipped (`Stage "UI Tests (headless)" skipped due to earlier failure(s)`), and `post { always }` still published the results. |

**A problem Jenkins showed me, and the fix**
In the red build #4 the test report still counted my 3 UI tests, although that stage was skipped. They were old report files from build #3, left in the same workspace. That gives a wrong picture. I changed the Build stage to `mvn clean compile` for both projects, so old reports are deleted at the start of every build.
- After the fix: build #5 green, 11 of 11 passed. Build #6 red on purpose: only the API suite is in the report, 7 passed and 1 failed (`brandsReturnsNonEmptyListWithNames`), the UI stage skipped, no old results.

**Where I look when a build is red** (from this run): the Stage View shows which stage is red; the Tests page of the build lists the failed test; the Console Output has the `[ERROR]` line with the test name, the line number and `Expected status code <201> but was <200>`.

## "My test failed, now what" checklist
1. **Read the first error line.** Write down the test name, the line number, and what was expected and what happened.
2. **Check my own test first.** Is the expected value right? Is the URL, the host name or the locator spelled right? (`statusCode(201)` for a GET, `.co` instead of `.com`.) If it is my mistake, it is a test defect and I fix it.
3. **Check if the system can be reached.** Does the same host answer from the terminal (`curl`, `nslookup`)? Do other tests pass? If not, it is an environment issue, and I tell the team with the exact message.
4. **Run it again, alone, a few times.** Fails every time with a correct test: it is real. Fails once and passes next time: it is flaky, so I do not report a bug yet.
5. **Write the evidence and decide.** Fix the test, log a bug with the request, the response and the steps, or report the environment. Put the exact line that told me in the ticket.

## Linux log reading and the broken Pod (Killercoda)
I did the Linux part in the Killercoda **Ubuntu 24.04** playground and the Pod part in the **Kubernetes** playground (one playground at a time, I exited the first before I started the second).

**Logs in `/var/log` (Ubuntu playground)**
- `cd /var/log` and `ls` show the log files: `syslog`, `dpkg.log`, `auth.log`, `kern.log`, `cloud-init.log`, `dmesg` and others.
- `cat README` reads a small file. It says the traditional logs are being replaced by the systemd journal (`journalctl`). `less dpkg.log` opens a long file page by page (`q` to leave). `tail -f syslog` follows the file live, new lines appear at the bottom (Ctrl+C to stop).
- `grep -i error /var/log/*.log` found lines that are not problems: the package name `libgpg-error-dev` in `dpkg.log`, and the kernel line `RAS: Correctable Errors collector initialized` in `kern.log` (it is a normal start message with the word "Errors" in it). The real one was in `cloud-init.log`: `RuntimeError: Runparts: 1 failures (runcmd) in 1 attempted commands`. So grep gives me candidates, and I must read each line to know which is a real error.

**Broken Pod (Kubernetes playground)**
1. `kubectl create namespace tufin-qa`, then `nano debug-pod-1.yaml`, I typed the YAML of the task, saved with Ctrl+O and Enter, and left with Ctrl+X.
2. `kubectl apply -f debug-pod-1.yaml`, then `kubectl get pods -n tufin-qa`: `debug-pod-1   0/1   ImagePullBackOff`.
3. `kubectl describe pod debug-pod-1 -n tufin-qa`, Events: `Failed to pull image "tufin/securechange:v99.9": ... docker.io/tufin/securechange:v99.9: not found`, then `Error: ErrImagePull` and `Error: ImagePullBackOff`.
4. In `nano` I replaced the `image` line with `image: nginx`, saved, and ran `kubectl apply -f debug-pod-1.yaml` again (`pod/debug-pod-1 configured`). After about 30 seconds: `debug-pod-1   1/1   Running`.

Why it failed: the image `tufin/securechange:v99.9` does not exist in the registry, so Kubernetes could not pull it and the container never started. The fix was in the YAML and not in the app, so I count it as an environment problem. The Events section at the bottom of `describe` is the first place to look, because it names the exact reason.
