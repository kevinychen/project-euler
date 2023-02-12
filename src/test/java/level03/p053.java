
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p053 extends EulerTest {

    final int N = 1000000;
    final int K = 100;

    /**
     * Find the number of pairs (n, r) with n≤K such that nCr(n, r) is at least N.
     */
    @Test
    public void test() {
        for (int n : rangeC(K))
            for (int r : rangeC(n))
                if (fnCr(n, r) > N)
                    ans++;
        check(4075);
    }
}
