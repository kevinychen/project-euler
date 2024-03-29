
package level03;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import data.BFraction;
import lib.EulerTest;

public class p065 extends EulerTest {

    final int N = 100;

    /**
     * Find the sum of digits in the numerator of the Nth convergent of the continued fraction for
     * e.
     * <p>
     * The continued fraction for e is [2; 1,2,1, 1,4,1, 1,6,1, ..., 1,2k,1, ...].
     */
    @Test
    public void test() {
        List<Integer> as = list(2);
        for (int i : range(N - 1))
            as.add(i % 3 == 1 ? 2 * (i / 3 + 1) : 1);
        Collections.reverse(as);

        BFraction val = BFraction.reduced(1, 0);
        for (int a : as)
            val = BFraction.integer(a).add(val.reciprocal());
        ans = sumDigits(val.num);
        check(272);
    }
}
