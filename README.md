# Project Euler Java library and solutions

Java programs for [Project Euler](https://projecteuler.net). Every solution satisfies the one-minute rule. Most solutions complete in less than a few seconds. To keep with the spirit of Project Euler, each solution is encrypted with its corresponding answer.

| Level | Problems | Total time to run 25 solutions |
| --- | --- | --- |
| 1 | [1 to 25](src/test/java/level01) | 0.9 seconds |
| 2 | [26 to 50](src/test/java/level02) | 1.0 seconds |
| 3 | [51 to 75](src/test/java/level03) | 2.2 seconds |
| 4 | [76 to 100](src/test/java/level04) | 1.7 seconds |
| 5 | [101 to 125](src/test/java/level05) | 1.0 seconds |
| 6 | [126 to 150](src/test/java/level06) | 2.2 seconds |
| 7 | [151 to 175](src/test/java/level07) | 4.3 seconds |
| 8 | [176 to 200](src/test/java/level08) | 5.7 seconds |
| 9 | [201 to 225](src/test/java/level09) | 19.6 seconds |
| 10 | [226 to 250](src/test/java/level10) | 11.3 seconds |
| 11 | [251 to 275](src/test/java/level11) | 36.3 seconds |
| 12 | [276 to 300](src/test/java/level12) | 28.8 seconds |
| 13 | [301 to 325](src/test/java/level13) | 21.9 seconds |
| 14 | [326 to 350](src/test/java/level14) | 25.2 seconds |
| 15 | [351 to 375](src/test/java/level15) | 32.6 seconds |
| 16 | [376 to 400](src/test/java/level16) | 30.7 seconds |
| 17 | [401 to 425](src/test/java/level17) | 100.1 seconds |
| 18 | [426 to 450](src/test/java/level18) | 80.2 seconds |
| 19 | [451 to 475](src/test/java/level19) | 73.1 seconds |
| 20 | [476 to 500](src/test/java/level20) | 54.9 seconds |
| 21 | [501 to 525](src/test/java/level21) | 64.8 seconds |
| 22 | [526 to 550](src/test/java/level22) | 64.9 seconds |
| 23 | [551 to 575](src/test/java/level23) | 98.5 seconds |
| 24 | [576 to 600](src/test/java/level24) | 82.6 seconds |
| 25+ | 601+ | In progress! |

(Run on Intel Core i9 @ 2.3GHz.)

## One-time setup

The solution files are encrypted. To decrypt them, first create an `answers.txt` file in the root directory with the following contents:

    1. answer to Problem 1
    2. answer to Problem 2
    ...

You do not need to include all the answers, but only the problems numbers listed in `answers.txt` will be decrypted.

This file can be created manually, but an alternative is to run `./gradlew fetchAnswers`. This will prompt you to enter your Project Euler credentials. (Note: this is only for fetching the answers to your solved problems, in order to decrypt the files; the task does not save any data or make any changes to your account.) It then automatically generates a `answers.txt` file with answers to all of your solved problems.

Now run the following:

    $ ./scripts/setup.sh

Files in Git will now be automatically decrypted, and files added to Git will be automatically encrypted.

## Running tests

Each solution is a JUnit test that can be run in your IDE, or directly in Gradle:

    $ ./gradlew test --tests *p001 // runs the program for Problem 1

To run all tests (takes a few minutes):

    $ ./gradlew test

## Development

This repository requires Java 8+.

For IntelliJ, run `./gradlew idea` and open the project.

For Eclipse, run `./gradlew eclipse` and then import "Existing Projects into Workspace". You will also need to setup [Lombok](https://projectlombok.org/setup/eclipse).

