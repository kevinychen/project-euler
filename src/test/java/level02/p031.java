
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p031 extends EulerTest {

    final int N = 200;
    final int[] DENOMINATIONS = { 200, 100, 50, 20, 10, 5, 2, 1 };

    /**
     * Find the number of ways to make N with the given DENOMINATIONS.
     * <p>
     * <li>dp(i, j) is the number of ways to make j using only denominations of at least
     * DENOMINATIONS[i].
     * <li>dp(i, j) = dp(i, j - DENOMINATIONS[i]) + dp(i-1, j).
     */
    @Test
    public void test() {
        int[][] dp = new int[DENOMINATIONS.length][N + 1];
        for (int i : range(DENOMINATIONS.length)) {
            dp[i][0] = 1;
            for (int j : rangeC(DENOMINATIONS[i], N))
                dp[i][j] = dp[i][j - DENOMINATIONS[i]] + (i > 0 ? dp[i - 1][j] : 0);
        }
        ans = dp[DENOMINATIONS.length - 1][N];
        check(73682);
    }
}
