
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p001 extends EulerTest {

    final int N = 1000;
    final int D1 = 3, D2 = 5;

    /**
     * Find the sum of all natural numbers below N that are divisible by D1 or D2.
     */
    @Test
    public void test() {
        for (int i : range(1, N))
            if (i % D1 == 0 || i % D2 == 0)
                ans += i;
        check(233168);
    }
}
