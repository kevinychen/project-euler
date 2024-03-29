
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p010 extends EulerTest {

    final int N = 2000000;

    /**
     * Find the sum of all primes below N.
     */
    @Test
    public void test() {
        for (int p : primes(N))
            ans += p;
        check(142913828922L);
    }
}
