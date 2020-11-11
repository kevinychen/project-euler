# Project Euler Java library and solutions

Java programs for [Project Euler](https://projecteuler.net). Every solution satisfies the one-minute rule. Most solutions complete in less than a few seconds. To keep with the spirit of Project Euler, each solution is encrypted with its corresponding answer.

| Level | Problems | Total time to run 25 solutions |
| --- | --- | --- |
| 1 | 1 to 25 | 0.9 seconds |
| 2 | 26 to 50 | 1.0 seconds |
| 3 | 51 to 75 | 2.2 seconds |
| 4 | 76 to 100 | 1.7 seconds |
| 5 | 101 to 125 | 1.0 seconds |
| 6 | 126 to 150 | 2.2 seconds |
| 7 | 151 to 175 | 4.3 seconds |
| 8 | 176 to 200 | 5.7 seconds |
| 9 | 201 to 225 | 19.6 seconds |
| 10 | 226 to 250 | 11.3 seconds |
| 11 | 251 to 275 | 36.3 seconds |
| 12 | 276 to 300 | 28.8 seconds |
| 13 | 301 to 325 | 21.9 seconds |
| 14 | 326 to 350 | 25.2 seconds |
| 15 | 351 to 375 | 41.6 seconds |
| 16 | 376 to 400 | 30.7 seconds |
| 17 | 401 to 425 | 100.1 seconds |
| 18 | 426 to 450 | 80.2 seconds |
| 19 | 451 to 475 | 73.1 seconds |
| 20 | 476 to 500 | 54.9 seconds |
| 21+ | 501+ | In progress! |

(Run on Intel Core i9 @ 2.3GHz.)

## Setup

This repository requires Java 8. In order to decrypt the files, you need an `answers.txt` file in the root directory in the following format (you do not need to include all answers):

    1. answer to Problem 1
    2. answer to Problem 2

To generate this `answers.txt` file with answers to all of your solved problems, run the following command:

    $ ./gradlew fetchAnswers

The task will prompt you to enter your Project Euler credentials. This is only for fetching the answers to your solved problems, in order to decrypt the files; the task does not save any data or make any changes to your account. If you do not wish to enter your credentials or are offline, you may create the `answers.txt` file manually.

Now run the following:

    $ ./scripts/setup.sh

Files in Git will now be automatically decrypted, and files added to Git will be automatically encrypted.

## Running tests

Each solution is a JUnit test that can be run in Eclipse. A test can also be run directly with Gradle:

    $ ./gradlew test --tests *p001 // runs the program for Problem 1

To run all tests (takes a few minutes):

    $ ./gradlew test

## Development

To setup the Eclipse project, run the following:

    $ ./gradlew eclipse

This project uses Lombok to easily generate Java data objects. To install Lombok in Eclipse, download and execute [lombok.jar](https://search.maven.org/remotecontent?filepath=org/projectlombok/lombok/1.16.12/lombok-1.16.12.jar).

To import this project, open Eclipse and click "Import" -> "Existing Projects into Workspace". Select this directory.

To maintain consist style in Eclipse, import the `formatter.xml` file in the root directory of this repository. Under project-specific preferences, select "Java Code Style" -> "Formatter", and import the `formatter.xml` profile.

