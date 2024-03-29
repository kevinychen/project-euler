
package level03;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p060 extends EulerTest {

    final int N = 5;
    final int L = 10000;
    final int B = 10;
    final List<Integer> primes = primes(L);

    boolean[][] concatenatable = new boolean[L][L];

    /**
     * Find the minimum sum of N primes such that for any different two primes p and q, the
     * concatenated p.q and q.p are both prime.
     */
    @Test
    public void test() {
        preff(isq(L));
        for (int p1 : primes)
            for (int p2 : primes)
                if (isPrime(p1 * ipow(B, (p2 + "").length()) + p2) && isPrime(p2 * ipow(B, (p1 + "").length()) + p1))
                    concatenatable[p1][p2] = concatenatable[p2][p1] = true;
        helper(list(), 0);
        check(26033);
    }

    void helper(List<Integer> nums, int sum) {
        if (nums.size() == N) {
            if (sum > ans)
                ans = sum;
            return;
        }
        for (int p : primes)
            if ((nums.isEmpty() || p > last(nums)) && concatenatable(nums, p)) {
                nums.add(p);
                helper(nums, sum + p);
                removeLast(nums);
            }
    }

    boolean concatenatable(List<Integer> nums, int p) {
        for (int n : nums)
            if (!concatenatable[n][p])
                return false;
        return true;
    }
}
