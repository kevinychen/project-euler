# Project Euler Java library and solutions

Java programs for Project Euler. Every solution satisfies the one-minute rule. Most solutions complete in less than a few seconds. Each file has a detailed explanation of the solution, and each program is written to be as readable and clean as possible. To keep with the spirit of Project Euler, each solution is encrypted with its corresponding answer.

| Problems | Total time to run 100 solutions |
| --- | --- |
| 1 to 100 | 6 seconds |
| 101 to 200 | 13 seconds |
| 201 to 300 | 96 seconds |
| 301 to 400 | 119 seconds |
| 401+ | In progress! |

### Setup

Each solution is encrypted with the answer to that problem. In order to decrypt them, create a file called `solutions.txt` in the root directory in the following format (you do not need to include all answers):

    1. [answer to Problem 1]
    2. [answer to Problem 2]
    ...

Then run the following (requires JDK 8):

    $ ./gradlew decrypt

This will decrypt all the files that you included an answer for. Note: if you included a wrong answer, the decrypted file will be garbage!

### Running tests

Each solution is a JUnit test that can be run in Eclipse. A test can also be run directly with Gradle:

    $ ./gradlew test --tests *p001 // runs the program for Problem 1

To run all tests (takes a few minutes):

    $ ./gradlew test

### Development

To setup the Eclipse project, run the following:

    $ ./gradlew eclipse

This project uses Lombok to easily generate Java data objects. To install Lombok in Eclipse, download and execute [lombok.jar](https://search.maven.org/remotecontent?filepath=org/projectlombok/lombok/1.16.12/lombok-1.16.12.jar).

To import this project, open Eclipse and click "Import" -> "Existing Projects into Workspace". Select this directory.

To maintain consist style in Eclipse, import the `formatter.xml` file in the root directory of this repository. Under project-specific preferences, select "Java Code Style" -> "Formatter", and import the `formatter.xml` profile.

Encrypt all solution files before committing by adding the corresponding answers to `solutions.txt` and then running:

    $ ./gradlew encrypt

