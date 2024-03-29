
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p071 extends EulerTest {

    final int N = 1000000;
    final int NUM = 3, DEN = 7;

    /**
     * Find the numerator of the largest fraction with denominator≤N smaller than NUM/DEN.
     * <p>
     * Two fractions A/B and C/D in the Farey sequence can only be adjacent if BC-AD=1. Since their
     * difference is then 1/(BD), the smallest difference when C/D is fixed corresponds to the
     * largest value of B.
     */
    @Test
    public void test() {
        for (int b : rangeC(N))
            if ((NUM * b - 1) % DEN == 0)
                ans = (NUM * b - 1) / DEN;
        check(428570);
    }
}
