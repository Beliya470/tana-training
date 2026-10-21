# Day 6 notes: QA automation, when to use it and when not

## What I read and watched
**BrowserStack, What is QA Automation**
- QA automation means scripts and tools run the tests, so the same tests can be repeated with no human mistakes. It also frees testers to spend time on tests that cannot be automated.
- When to automate: repeated tests (every release), data driven tests (same steps, many data sets), a big number of test cases, regression and smoke suites, tests that must run in a fixed order, and cross platform runs (many browsers and systems at once).
- When not to: a small project with few cases, features that are new or change every week, and tests that need a person to judge (look and feel, exploratory). Not every case is a good candidate.
- Best practices: pick cases by value, choose a tool the team can maintain, keep test data outside the scripts, and store screenshots and logs of failures so a real failure can be told from a false one.

**TestRail, What is Automated Testing**
- An automated test is a script that runs against the code and raises a flag when the result is not what we expected.
- Three kinds: unit (one piece in isolation), integration (two or more pieces together), end-to-end (a user journey through the whole app).
- End-to-end tests are brittle because they are tightly tied to the page. If a button changes or is removed, the test breaks. They are also slow.
- The suggested split is about 70% unit, 20% integration, 10% end-to-end. The point is that each extra end-to-end test costs more to keep than it gives back ("is the juice worth the squeeze").

**BugBug, QA Automation Without a QA Team (optional)**
- The test pyramid is a cost model. The higher the layer, the slower, flakier and more expensive. API tests are the best value for most products. UI tests should be few and only for the important journeys (login, checkout).
- Automate what breaks and what is risky, not what is easy to record. Skip one-time edge cases and features still changing.
- A flaky test is a production bug for the suite. If nobody owns the red tests, the team stops trusting the suite.
- Count the value, not the number of tests: bugs that escaped, flaky rate, time of the pipeline.

## What is the difference between manual and automated testing?
In manual testing a person runs the steps, looks at the result and decides if it is right. In automated testing a script runs the steps and checks the result, and it can run again and again with no extra effort.

| | Manual | Automated |
|---|---|---|
| Who runs it | A tester | A script or tool |
| Speed on repeats | Slow, the same effort each time | Fast, runs at any time (for example on every build) |
| Cost | Cheap to start, costly to repeat | Costly to build and to keep up to date, cheap to repeat |
| Good at | New features, exploring, looks, usability, "does this feel right" | Regression, data driven checks, many browsers, smoke tests |
| Weak at | Boring repeats, human mistakes when tired | Anything that needs judgement, and anything that keeps changing |

Both are needed. Automation tells me quickly that the known things still work. Manual testing finds the things I did not think of.

## 3 tests to automate and 3 to leave manual
Examples from the Toolshop and from Swag Labs.

**Automate**
1. **Search for "pliers" returns the 4 pliers products (TF40-31).** I run it on every release, it has a clear expected result (4 products), and a script can check it in a fraction of a second. It is also the first thing that breaks if search is broken.
2. **Login with a valid and an invalid user (Swag Labs TF40-11 and TF40-13).** Login is the door of the app and every release can break it. The steps never change and I can run them with many users from a list (data driven).
3. **Searching with special characters like ">" and "%%%" (TF40-39, TF40-40).** Many inputs, the same check. A person gets bored and skips some. The API showed both bugs (500 error and all 29 products returned), so a short script catches them every time.

**Do not automate (keep manual)**
1. **The search button is labelled "Search" and not "Serch" (TF40-43).** It is a one-word look check that I see in a second just by using the page. A script for it costs more than it saves. A person sees it for free while doing other tests.
2. **Every product card shows a photo, and "does the page look right" (TF40-45).** Whether a picture looks right is a human judgement. A script can only check that a file name exists, and that missed my bug TF40-53.
3. **A first look at a new feature (exploratory testing).** Nobody knows what to check until the tester plays with it. The feature may also change next week, so the script would be thrown away.

## Which Toolshop cases go where
The pyramid idea: most checks at the API, only a few on the screen, and the rest by hand. The 18 search cases are TF40-31 to TF40-48. The 6 API cases TF40-73 to TF40-78 are already automated (Day 5).

| Level | Cases | Why |
|---|---|---|
| **API (10)** | TF40-32 upper case, TF40-33 part of a word, TF40-34 spaces, TF40-35 no match, TF40-36 only spaces, TF40-39 ">" character, TF40-40 "%" characters, TF40-44 pages of 9, TF40-47 sort high to low, TF40-48 price range | These are data rules. The API shows them with a status and the data, with no page needed. They are fast and do not break when the design changes. |
| **API and one UI check (2)** | TF40-31 search "pliers", TF40-46 sort low to high | The API checks the data. One UI check is also kept because two of my screen bugs (TF40-50 wrong sort order, TF40-51 price range) were on the page only and the API was correct. |
| **UI only (2)** | TF40-37 fewer than 3 characters is blocked, TF40-42 clicking a result opens the product page | The first is a rule of the search box. The second is the main journey of a shopper (search, click, product page). They cannot be seen at the API. |
| **Manual (4)** | TF40-38 empty box keeps the list, TF40-41 X button clears, TF40-43 button label, TF40-45 photo on each card | They are small screen behaviors or looks. A UI test for each would be slow and break on small changes. A person checks them in seconds. |

So the plan is 12 API, 4 UI (TF40-31, TF40-46, TF40-37, TF40-42) and 4 manual, which keeps the top of the pyramid small. The Swag Labs cases (login, cart, checkout) follow the same idea: login, add to cart and checkout are the important journeys, so a few UI tests stay there, and the looks bugs (same dog photo on every product) stay with manual testing.

## What I learned
- First ask: can this be checked at the API? If yes, do it there. It is faster and breaks less.
- Keep UI tests for the journeys that matter and for bugs that only the screen shows.
- Do not automate what is new, changing, or a matter of looking.

## Flaky tests and failed tests

**What I watched and read**
- Video (What is a Flaky Test, What Causes Flaky Tests): a flaky test sometimes passes and sometimes fails, with no change in the code. Developers start to say "just rerun the build", and then nobody knows if a red test is a bug or not.
- Ranorex, Flaky Tests in Automation: flaky tests waste QA time, slow the release, lower the trust in automation and hurt morale. The common causes are timing (hard-coded waits), an unstable environment or network, reused or shared data, and fragile locators. The way to deal with them: find the pattern, collect the logs and screenshots, stabilize the test, quarantine it (take it out of the main run) and run it again until it is stable.

**What is the difference between a flaky test and a failed test?**
A failed test fails every time with the same input, because there is a real bug or a real problem in the environment. It is clear and can be fixed once. A flaky test gives different results on the same code and the same input: green on one run and red on the next. A failed test tells me something is wrong. A flaky test tells me nothing, because I cannot tell if the failure is real.

| | Failed test | Flaky test |
|---|---|---|
| Result on the same code | Always the same | Changes between runs |
| What it means | A real bug, or the test is wrong | Unknown: a bug, or just noise |
| Fix | One fix, and it stays green | Hard to find, because it does not fail on demand |
| Trust | Keeps the trust | Destroys the trust |

**Why flaky tests are especially dangerous in a CI pipeline**
- The pipeline runs by itself, so people trust it without looking. When a test is flaky, the team learns to rerun the build and to ignore red results.
- Then a real bug can be called "just the flaky test" and reach the users. A flaky test on an important flow is worse than no test, because it looks like protection.
- Every rerun costs time and blocks pull requests and releases.

**One example of each cause**
- *Failed test:* In my suite, `GET /products/search?q=pliers` must return 4 products. If a developer breaks the search so that it returns nothing, the test fails on every run until the bug is fixed. This is a real failure.
- *Flaky test:* The Day 5 login test used the seeded demo customer of the Toolshop. That account is shared with everyone on the internet. It passed in the morning of 21 October and later the same day the same request gave 401, with no change in my code. The cause is shared data that someone else changes. The fix is what the refactor does: each run registers its own new user with a unique email.
- Other causes I know now: hard-coded waits in UI tests, a slow or down network, and tests that depend on each other or on the order they run.
