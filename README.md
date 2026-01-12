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
