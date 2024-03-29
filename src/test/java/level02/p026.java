
package level02;

import org.junit.Test;

import lib.EulerTest;
import lib.NumberTheory;

public class p026 extends EulerTest {

    final int N = 1000;
    final int B = 10;

    /**
     * Find the positive integer under N with the longest recurring cycle in its decimal part.
     */
    @Test
    public void test() {
        long[] decimalCycleLengths = NumberTheory.decimalCycleLengths(N, B);
        long maxCycleLength = 0;
        for (int i : range(1, N))
            if (decimalCycleLengths[i] > maxCycleLength) {
                maxCycleLength = decimalCycleLengths[i];
                ans = i;
            }
        check(983);
    }
}
