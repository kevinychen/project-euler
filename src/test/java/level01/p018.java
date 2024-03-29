
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p018 extends EulerTest {

    /**
     * Find the maximum total of numbers from the top to bottom of the triangular grid.
     * <p>
     * <li>dp(i, j) is the maximum total of numbers from the top of row i, col j.
     * <li>dp(i, j) = max(dp(i-1, j-1), dp(i-1, j)) + grid(i-1, j-1).
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
        check(1074);
    }
}
