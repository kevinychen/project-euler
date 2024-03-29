
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p097 extends EulerTest {

    final int N = 10;
    final int B = 10;

    /**
     * Find the last N digits of the non-Mersenne prime 28433x2^{7830457}+1.
     */
    @Test
    public void test() {
        ans = big(28433).multiply(big(2).pow(7830457)).add(big(1)).mod(big(B).pow(N)).longValue();
        check(8739992577L);
    }
}
