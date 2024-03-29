
package level02;

import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p046 extends EulerTest {

    final int L = 10000;

    /**
     * Find the smallest odd composite number that is not the sum of a prime and twice a square.
     */
    @Test
    public void test() {
        Set<Integer> good = set();
        for (int p : primes(L))
            for (int i = 1; sq(i) < L; i++)
                good.add(p + 2 * isq(i));
        int ans = 3;
        while (isPrime(ans) || good.contains(ans))
            ans += 2;
        check(ans, 5777);
    }
}
