# Project Euler Java library and solutions

Java programs for [Project Euler](https://projecteuler.net). Every solution satisfies the one-minute rule. Most solutions complete in less than a few seconds.

To keep with the spirit of Project Euler, each solution after the first 100 problems is encrypted with its corresponding answer. They can be decrypted with the [setup steps below](#one-time-setup).

| Level | Problems                            | Total time to run 25 solutions |
|-------|-------------------------------------|--------------------------------|
| 1     | [1 to 25](src/test/java/level01)    | 0.3 seconds                    |
| 2     | [26 to 50](src/test/java/level02)   | 0.8 seconds                    |
| 3     | [51 to 75](src/test/java/level03)   | 1.8 seconds                    |
| 4     | [76 to 100](src/test/java/level04)  | 1.0 seconds                    |
| 5     | [101 to 125](src/test/java/level05) | 0.7 seconds                    |
| 6     | [126 to 150](src/test/java/level06) | 1.1 seconds                    |
| 7     | [151 to 175](src/test/java/level07) | 2.6 seconds                    |
| 8     | [176 to 200](src/test/java/level08) | 2.1 seconds                    |
| 9     | [201 to 225](src/test/java/level09) | 4.4 seconds                    |
| 10    | [226 to 250](src/test/java/level10) | 5.5 seconds                    |
| 11    | [251 to 275](src/test/java/level11) | 25.4 seconds                   |
| 12    | [276 to 300](src/test/java/level12) | 8.9 seconds                    |
| 13    | [301 to 325](src/test/java/level13) | 8.1 seconds                    |
| 14    | [326 to 350](src/test/java/level14) | 10.8 seconds                   |
| 15    | [351 to 375](src/test/java/level15) | 14.0 seconds                   |
| 16    | [376 to 400](src/test/java/level16) | 13.2 seconds                   |
| 17    | [401 to 425](src/test/java/level17) | 31.0 seconds                   |
| 18    | [426 to 450](src/test/java/level18) | 18.6 seconds                   |
| 19    | [451 to 475](src/test/java/level19) | 30.4 seconds                   |
| 20    | [476 to 500](src/test/java/level20) | 25.1 seconds                   |
| 21    | [501 to 525](src/test/java/level21) | 18.5 seconds                   |
| 22    | [526 to 550](src/test/java/level22) | 24.5 seconds                   |
| 23    | [551 to 575](src/test/java/level23) | 55.7 seconds                   |
| 24    | [576 to 600](src/test/java/level24) | 35.3 seconds                   |
| 25    | [601 to 625](src/test/java/level25) | 29.2 seconds                   |
| 26    | [626 to 650](src/test/java/level26) | 37.2 seconds                   |
| 27    | [651 to 675](src/test/java/level27) | 16.4 seconds                   |
| 28    | [676 to 700](src/test/java/level28) | 37.7 seconds                   |
| 29+   | 701+                                | In progress!                   |

(Run on Apple M2 Max.)

## One-time setup

The solution files are encrypted. To decrypt them, you need a file named `answers.txt` in the root directory with the following format:

    1. answer to Problem 1
    2. answer to Problem 2
    ...

This file does not need to include all the answers, but only solution files with an included answer will be decrypted.

To generate this file automatically for all problems that you've solved on the Project Euler website, run this in the root directory:

    $ ./gradlew fetchAnswers

You will be prompted to enter your Project Euler credentials. The task will generate an `answers.txt` file with answers for all solved problems on your account. It doesn't make any changes to your account.

Now run the following:

    $ ./scripts/setup.sh

Files in Git will now be automatically decrypted, and files added to Git will be automatically encrypted.

The `setup.sh` script must be re-run if any changes are manually made to the `answers.txt` file.

## Running tests

Each solution is a JUnit test that can be run in your IDE, or directly in Gradle:

    $ ./gradlew test --tests *p001 // runs the program for Problem 1

To run all tests (takes a few minutes):

    $ ./gradlew test

## Development

This repository requires Java 8+.

For IntelliJ, run `./gradlew idea` and open the project, or use the native Gradle integration.

For Eclipse, run `./gradlew eclipse` and then import "Existing Projects into Workspace". You will also need to setup [Lombok](https://projectlombok.org/setup/eclipse).

