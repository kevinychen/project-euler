
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p005 extends EulerTest {

    final int N = 20;

    /**
     * Find the smallest positive integer that is a multiple of all integers from 1 to N.
     */
    @Test
    public void test() {
        ans = 1;
        for (int i : rangeC(1, N))
            ans = lcm(ans, i);
        check(232792560);
    }
}
