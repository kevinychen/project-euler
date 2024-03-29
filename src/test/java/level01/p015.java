
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p015 extends EulerTest {

    final int N = 20;

    /**
     * Find the number of routes, only moving right and down, in a NxN grid.
     */
    @Test
    public void test() {
        ans = nCr(2 * N, N);
        check(137846528820L);
    }
}
