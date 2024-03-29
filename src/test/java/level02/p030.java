
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p030 extends EulerTest {

    final int N = 5;
    final int L = 1000000;

    /**
     * Find the sum of all numbers > 1 that equal the sum of Nth powers of their digits.
     * <p>
     * Since 9^5 < 60000, it is impossible for the sum of the 5th powers of a 7+ digit number to be
     * larger than 7x9^5 < 999999. Therefore, only numbers with up to 6 digits need to be checked.
     */
    @Test
    public void test() {
        for (int i : range(2, L)) {
            int sumNthPowers = 0;
            for (int d : digits(i))
                sumNthPowers += pow(d, N);
            if (sumNthPowers == i)
                ans += i;
        }
        check(443839);
    }
}
