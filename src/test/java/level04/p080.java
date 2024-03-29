
package level04;

import java.math.BigInteger;

import org.junit.Test;

import lib.EulerTest;

public class p080 extends EulerTest {

    final int N = 100;
    final int K = 100;
    final int B = 10;

    /**
     * Find the sum of the first K decimal digits of all irrational √n for n≤N.
     */
    @Test
    public void test() {
        BigInteger shift = big(B).pow(2 * K);
        for (int n : rangeC(1, N))
            if (!isSq(n))
                for (int d : digits(((isqrt(big(n).multiply(shift)) + "").substring(0, K))))
                    ans += d;
        check(40886);
    }
}
