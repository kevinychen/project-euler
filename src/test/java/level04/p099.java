
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p099 extends EulerTest {

    /**
     * Find the pair (a, b) from the specified pairs such that a^b is largest.
     * <p>
     * Since a^b > c^d if and only if b log(a) > d log(c), we compute the line number with the
     * largest b log(a).
     */
    @Test
    public void test() {
        long[][] readAsGrid = readAsGrid(",");
        double maxLogValue = 0.0;
        for (int i : range(readAsGrid.length)) {
            double logValue = readAsGrid[i][1] * Math.log(readAsGrid[i][0]);
            if (logValue > maxLogValue) {
                maxLogValue = logValue;
                ans = i + 1;
            }
        }
        check(709);
    }
}
