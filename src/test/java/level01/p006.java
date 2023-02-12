
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p006 extends EulerTest {

    final int N = 100;

    /**
     * Find the difference between the sum of the squares of the first N natural integers and the
     * square of the sum.
     */
    @Test
    public void test() {
        ans = sq(tr(N));
        for (int i : rangeC(N))
            ans -= sq(i);
        check(25164150);
    }
}
