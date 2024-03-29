
package level02;

import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p044 extends EulerTest {

    final int L = 3000;

    /**
     * Find the minimal difference between two pentagonal numbers A and B such that both A+B and A-B
     * are also pentagonal.
     */
    @Test
    public void test() {
        Set<Long> pentagonals = set();
        for (int i : range(1, L))
            pentagonals.add(figurate(i, 5));

        ans = Long.MAX_VALUE;
        for (long a : pentagonals)
            for (long b : pentagonals) {
                long d = Math.abs(a - b);
                if (Math.abs(d) < ans && pentagonals.contains(a + b) && pentagonals.contains(d))
                    ans = d;
            }
        check(ans, 5482660);
    }
}
