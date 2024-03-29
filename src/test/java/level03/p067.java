
package level03;

import org.junit.Test;

import level01.p018;
import lib.EulerTest;

public class p067 extends EulerTest {

    /**
     * Find the maximum total of numbers from the top to bottom of the specified triangular grid.
     * <p>
     * This is the same as {@link p018}.
     */
    @Test
    public void test() {
        long[][] grid = readAsGrid(" ");
        long[][] dp = new long[grid.length + 1][];
        for (int i : rangeC(grid.length)) {
            dp[i] = new long[i + 2];
            for (int j : rangeC(1, i))
                dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + grid[i - 1][j - 1];
        }
        for (long n : dp[grid.length])
            if (n > ans)
                ans = n;
        check(7273);
    }
}
