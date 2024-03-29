
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p077 extends EulerTest {

    final int N = 5000;
    final int L = 100;

    /**
     * Find the smallest number that can be written as a sum of primes in over N ways.
     * <p>
     * <li>dp(i, j) is the number of ways to write i as the sum of primes≤j.
     * <li>dp(i, j) = sum_{p≤j} dp(i-p, p)
     * <li>This solution is very similar to that of {@link p076}.
     */
    @Test
    public void test() {
        preff(L);
        int[][] dp = new int[L + 1][L];
        for (int j : range(1, L))
            dp[0][j] = 1;
        for (int i : rangeC(1, L))
            for (int j : range(1, L))
                for (int p : rangeC(Math.min(i, j)))
                    if (isPrime(p))
                        dp[i][j] += dp[i - p][p];
        int ans = 1;
        while (dp[ans][ans - 1] <= N)
            ans++;
        check(ans, 71);
    }
}
