
package level04;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p087 extends EulerTest {

    final int N = 50_000000;
    final List<Integer> Ks = list(2, 3, 4);
    final List<Integer> primes = primes((int) Math.pow(N, 1. / Collections.min(Ks)));

    /**
     * Find the number of positive integers below N that can be expressed as the sum of prime kth
     * powers, with one kth power for each value of k in Ks.
     */
    @Test
    public void test() {
        boolean[] expressible = new boolean[N + 1];
        helper(0, 0, expressible);
        for (boolean b : expressible)
            if (b)
                ans++;
        check(1097343);
    }

    void helper(int i, int sum, boolean[] expressible) {
        if (i == Ks.size()) {
            expressible[sum] = true;
            return;
        }
        for (int p : primes) {
            int newSum = sum + ipow(p, Ks.get(i));
            if (newSum > N)
                break;
            helper(i + 1, newSum, expressible);
        }
    }
}
