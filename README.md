# Mots Pendule (Hangman)

Professional-grade hangman game in Java with automated build, tests, and docs.

## Requirements
- Java 17+ (or compatible)
- Maven 3.9+ or Ant 1.10+

## Build with Maven
- `mvn clean test`
- `mvn javadoc:javadoc`
- `mvn package`

## Build with Ant
Ant expects JUnit 4 jars in `lib/`:
- `lib/junit-4.13.2.jar`
- `lib/hamcrest-core-1.3.jar`

Then run:
- `ant clean test`
- `ant javadoc`
- `ant jar`

## Sonar
Example (configure your server/project):
- `mvn sonar:sonar -Dsonar.projectKey=mots-pendule -Dsonar.host.url=http://localhost:9000 -Dsonar.login=YOUR_TOKEN`

## Run
- `java -jar target/mots-pendule.jar`

## Notes
- Dictionary file: `src/main/resources/dictionary.txt`

## Maven test output
```
e:\github\mots-pendule>"C:\Users\Lenovo\AppData\Local\Programs\Apache\apache-maven-3.9.9\bin\mvn.cmd" test

[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------< com.mots:mots-pendule >------------------------
[INFO] Building mots-pendule 1.0.0
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ mots-pendule ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO]
[INFO] --- compiler:3.13.0:compile (default-compile) @ mots-pendule ---
[INFO] Recompiling the module because of added or removed source files.
[INFO] Compiling 6 source files with javac [debug target 17] to target\classes
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ mots-pendule ---
[INFO] skip non existing resourceDirectory e:\github\mots-pendule\src\test\resources
[INFO]
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ mots-pendule ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 1 source file with javac [debug target 17] to target\test-classes
[INFO] 
[INFO] --- surefire:3.2.5:test (default-test) @ mots-pendule ---
[INFO] Using auto detected provider org.apache.maven.surefire.junit4.JUnit4Provider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.mots.pendule.core.GameEngineTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.054 s -- in com.mots.pendule.core.GameEngineTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.759 s
[INFO] Finished at: 2026-01-12T17:29:58+01:00
[INFO] ------------------------------------------------------------------------
```
