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
| 15 | 351 to 375 | 32.6 seconds |
| 16 | 376 to 400 | 30.7 seconds |
| 17 | 401 to 425 | 100.1 seconds |
| 18 | 426 to 450 | 80.2 seconds |
| 19 | 451 to 475 | 73.1 seconds |
| 20 | 476 to 500 | 54.9 seconds |
| 21 | 501 to 525 | 64.8 seconds |
| 22 | 526 to 550 | 64.9 seconds |
| 23 | 551 to 575 | 98.5 seconds |
| 24 | 576 to 600 | 82.6 seconds |
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

