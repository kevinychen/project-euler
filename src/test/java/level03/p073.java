
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p073 extends EulerTest {

    final int N = 12000;
    final int MIN_NUM = 1, MIN_DEN = 3, MAX_NUM = 1, MAX_DEN = 2;

    /**
     * Find the number of reduced proper fractions with denominator≤N between MIN_NUM/MIN_DEN and
     * MAX_NUM/MAX_DEN.
     */
    @Test
    public void test() {
        preff(N);
        for (int i : rangeC(1, N))
            ans += numRelativelyPrime(i, ceil(MAX_NUM * i, MAX_DEN) - 1) - numRelativelyPrime(i, i * MIN_NUM / MIN_DEN);
        check(7295372);
    }
}
