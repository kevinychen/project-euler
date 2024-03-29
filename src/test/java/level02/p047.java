
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p047 extends EulerTest {

    final int N = 4;
    final int K = 4;
    final int L = 1000000;

    /**
     * Find the smallest positive integer n such that the K consecutive numbers starting with n all
     * have at least N distinct prime factors.
     */
    @Test
    public void test() {
        preff(L);
        int ans = 0;
        while (!good(ans))
            ans++;
        check(ans, 134043);
    }

    boolean good(int n) {
        for (int i : range(K))
            if (primeFactor(n + i).size() < N)
                return false;
        return true;
    }
}
