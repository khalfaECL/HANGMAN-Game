# Mots Pendule (Hangman)

Professional-grade hangman game in Java with automated build, tests, and docs.

------------------------------------------------------------
SECTION :: Quick Start (Run the game)
------------------------------------------------------------

Step 1 - Set Java 17

CMD:
```
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
java -version
```

PowerShell:
```powershell
$env:JAVA_HOME='C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot'
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
java -version
```

Step 2 - Build and run (Maven)
```
mvn clean test package
java -jar target\mots-pendule-1.0.0.jar
```

Optional - Run the GUI (Swing)
```
java -jar target\mots-pendule-1.0.0.jar --gui
```

Gameplay flow
- Choose max errors
- Choose mode (1=solo, 2=two players)
- Enter letters until win/lose

------------------------------------------------------------
SECTION :: Download (Windows)
------------------------------------------------------------

GitHub Releases (installer link):
- https://github.com/khalfaECL/HANGMAN-Game/releases/latest

------------------------------------------------------------
SECTION :: Windows Installer (EXE)
------------------------------------------------------------

Requirements
- JDK 17+ with `jpackage` (included in the JDK)
- WiX Toolset 3.11+ (for Windows installers)
- Maven 3.9+

Build installer
```
powershell -ExecutionPolicy Bypass -File scripts\package-windows.ps1
```

Output
- `dist` (contains the .exe installer)

After install
- Search Start Menu for `MotsPendule`
- If you do not see it, uninstall the old version and re-run the installer

------------------------------------------------------------
SECTION :: Project Map
------------------------------------------------------------

- Core engine: `src/main/java/com/mots/pendule/core`
- Dictionary: `src/main/resources/dictionary.txt`
- CLI entry: `src/main/java/com/mots/pendule/app/Main.java`
- Tests: `src/test/java/com/mots/pendule/core/GameEngineTest.java`

------------------------------------------------------------
SECTION :: Build, Tests, Docs
------------------------------------------------------------

Requirements
- Java 17+
- Maven 3.9+ or Ant 1.10+

Maven commands
```
mvn clean test
mvn javadoc:javadoc
mvn package
```

Javadoc output
- `target/site/apidocs`

Ant commands (JUnit 4 jars required in `lib/`)
- `lib/junit-4.13.2.jar`
- `lib/hamcrest-core-1.3.jar`

```
ant clean test
ant javadoc
ant jar
```

Sonar example
```
mvn sonar:sonar -Dsonar.projectKey=mots-pendule -Dsonar.host.url=http://localhost:9000 -Dsonar.login=YOUR_TOKEN
```

------------------------------------------------------------
SECTION :: Troubleshooting
------------------------------------------------------------

Problem: "JNI error" or "Unsupported major.minor version"
Fix: Set JAVA_HOME to Java 17 (see Quick Start).

Problem: "mvn not recognized"
Fix: Use the full path to Maven:
```
C:\Users\Lenovo\AppData\Local\Programs\Apache\apache-maven-3.9.9\bin\mvn.cmd
```

------------------------------------------------------------
SECTION :: Maven Test Output (Saved)
------------------------------------------------------------

<details>
<summary>Click to expand full Maven test output</summary>

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

</details>
