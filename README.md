



# Jokenpo


This project contains the jokenpo game where players choose actions between ROCK,  PAPER,  SCISSORS, SPOCK and LIZARD, and when run finish endpoint the system show who is the winner and the defeat history.
 
#### Postman
- [Publish postman]([https://documenter.getpostman.com/view/1662721/SzmiWw6f?version=latest](https://documenter.getpostman.com/view/1662721/SzmiWw6f?version=latest))

### Requirements

For building and running the application you need:

- [Eclipse](https://www.eclipse.org/) ou [Intellij](https://www.jetbrains.com/idea/)
- [Lombok](https://www.baeldung.com/lombok-ide)
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle]([https://gradle.org/](https://gradle.org/))

## Running the application locally

It is necessary import the application 

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.brq.jokenpo` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/) like so:

 ```
     ./gradlew  bootRun
  ```

### Step-by-step to run in Intellij

After open the file:
- Install Lombok
   - Go to File > Settings > Plugins
   - Click on Browse repositories...
   - Search for Lombok Plugin
   - Click on Install plugin
   - Restart IntelliJ IDEA

- JDK8
    - Go to "File"
    - "Project Structure "
    - Then select "Project" in "Project Settings"
    - In "Project SDK" select 1.8 or add

- Now is ready, run ` ./gradlew  bootRun` or:
    - Go to `JokenpoApplication` in package  `com.brq.jokenpo` and with right button select `Rum JokenpoApplication.main()`