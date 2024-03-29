
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p017 extends EulerTest {

    final int N = 1000;
    final String[] SMALL_NUMBERS = { "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
    final String[] MULTIPLES_OF_TEN_NUMBERS = { "", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy",
            "eighty", "ninety" };

    /**
     * Find the number of letters in all written out words from 1 to N.
     */
    @Test
    public void test() {
        for (int i : rangeC(N))
            ans += numLettersInNumberWord(i);
        check(21124);
    }

    int numLettersInNumberWord(int n) {
        if (n < 20)
            return SMALL_NUMBERS[n].length();
        else if (n < 100)
            return MULTIPLES_OF_TEN_NUMBERS[n / 10].length() + numLettersInNumberWord(n % 10);
        else if (n < 1000)
            return SMALL_NUMBERS[n / 100].length() + "hundred".length() + (n % 100 == 0 ? 0 : "and".length())
                    + numLettersInNumberWord(n % 100);
        else if (n == 1000)
            return SMALL_NUMBERS[1].length() + "thousand".length();
        else
            throw die();
    }
}
