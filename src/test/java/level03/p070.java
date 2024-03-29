
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p070 extends EulerTest {

    final int N = ipow(10, 7);

    /**
     * Find 1<n<N such that ϕ(n) is a permutation of n and n/ϕ(n) is minimized.
     */
    @Test
    public void test() {
        prePhi(N);
        double minDiv = Double.MAX_VALUE;
        for (int n = 2; n < N; n++) {
            double div = 1. * n / phi[n];
            if (div < minDiv && n % 9 == phi[n] % 9 && isPermutation(digits(n), digits(phi[n]))) {
                minDiv = div;
                ans = n;
            }
        }
        check(8319823);
    }
}
