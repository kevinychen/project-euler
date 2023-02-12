
package level02;

import java.math.BigInteger;

import org.junit.Test;

import lib.EulerTest;

public class p048 extends EulerTest {

    final int N = 1000;
    final long M = pow(10, 10);

    /**
     * Find the last K digits of sum{i=1}^N i^i.
     */
    @Test
    public void test() {
        BigInteger b = big(0);
        for (int i : range(1, N))
            b = b.add(big(i).pow(i));
        ans = b.mod(big(M)).longValue();
        check(9110846700L);
    }
}
