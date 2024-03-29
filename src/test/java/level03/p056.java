
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p056 extends EulerTest {

    final int N = 100;

    /**
     * Find the maximum digit sum of a^b for a,b < N.
     */
    @Test
    public void test() {
        for (int a : range(N))
            for (int b : range(N)) {
                long sumDigits = sumDigits(big(a).pow(b));
                if (sumDigits > ans)
                    ans = sumDigits;
            }
        check(972);
    }
}
