
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p028 extends EulerTest {

    final int N = 1001;
    final int K = 4;

    /**
     * Find the sum of the numbers on the diagonals of a NxN K-side spiral.
     *
     * <pre>
     * (21)  22   23   24  (25)
     *  20   (7)   8   (9)  10
     *  19    6   (1)   2   11
     *  18   (5)   4   (3)  12
     * (17)  16   15   14  (13)
     * </pre>
     *
     * Layer 0 contains a single number, 1. For i≥1, layer i contains 4 numbers, the largest of
     * which is (2i+1)² and the remaining are smaller in steps of (2i).
     */
    @Test
    public void test() {
        ans = 1;
        for (int i : rangeC(1, N / 2))
            for (int j : range(K))
                ans += sq(2 * i + 1) - 2 * i * j;
        check(669171001);
    }
}
