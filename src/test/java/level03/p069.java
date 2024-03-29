
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p069 extends EulerTest {

    final int N = 1000000;

    /**
     * Find n≤N such that n/ϕ(n) is maximized.
     */
    @Test
    public void test() {
        prePhi(N);
        double maxDiv = 0;
        for (int n : rangeC(1, N)) {
            double div = 1. * n / phi[n];
            if (div > maxDiv) {
                maxDiv = div;
                ans = n;
            }
        }
        check(510510);
    }
}
