# Day 8: Capstone (Toolshop regression pack)

The capstone is a small regression pack for the Toolshop practice shop: 6 API tests and 2 UI tests, with a Jenkinsfile. I built it on Day 7 (afternoon) and finished it on Day 8 (morning).

The folder `toolshop-regression-pack` here is a copy of my capstone repo, so all my training work is in one place. The repo the trainer reviews is https://github.com/Beliya470/toolshop-regression-pack (the copy here is the state after its PR #2).

## What is in the folder
- `toolshop-regression-pack/README.md`: how to run the pack in 3 steps, the settings, and how to run it against the with-bugs shop.
- `toolshop-regression-pack/PLAN.md`: the Day 7 plan (scope, levels, test data, the 8 Jira cases).
- `toolshop-regression-pack/REPORT.md`: the Day 8 Final Automation Report (5 headings, 5 bugs, recommendation: flag).
- `toolshop-regression-pack/api-tests` and `ui-tests`: the tests.

## Run it
```
cd day8/toolshop-regression-pack
mvn test
```

Against the with-bugs shop (the registration option is explained in the pack README):
```
mvn test -Dmaven.test.failure.ignore=true -Dregister.flat=true "-Dui.url=https://with-bugs.practicesoftwaretesting.com/#" -Dbase.uri=https://api-with-bugs.practicesoftwaretesting.com
```

## Jira
Story TF40-95, precondition TF40-96, test cases TF40-97 to TF40-104, bugs TF40-105 to TF40-109.
