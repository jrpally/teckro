# Craigslist Housing Automation

I build this UI testing framework to check search so I can verify sorting functionalities on Craigslist Madrid.
Its using **Java 21**, **Playwright** and **JUnit 5**, and it generate a standalone HTML report with **xunit-viewer**.

## What you need
Make sure you instaled:
- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/) 
- [Node.js & npm](https://nodejs.org/) (for the html reports)

## Run Tests
Normal run (open browser):
```bash
mvn clean test
```
Headless Mode (for CI pipeline or no popups):
```bash
mvn clean test -Dheadless=true
```

## HTML Report
To turn the xml files into a single cool html report just run:
```bash
npx xunit-viewer -r target/surefire-reports -o target/test-results.html
```
*(Note: I specificly muted the console output during tests because `xunit-viewer` was crashing parsing long `@DisplayName` texts!)*

## Folders
- `src/main/java...` - Browser setup, Page objects and Utils (`TestLogger` is silence here during tests).
- `src/test/java...` - The JUnit 5 tests.

## CI pipeline
GitHub Actions is configured. On push or PR it will automaticly compile, install dependencies, run the tests headless, and save the html report.
