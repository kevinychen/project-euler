
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p016 extends EulerTest {

    final int N = 1000;

    /**
     * Find the sum of the digits in 2^N.
     */
    @Test
    public void test() {
        ans = sumDigits(big(2).pow(N));
        check(1366);
    }
}
