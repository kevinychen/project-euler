
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p037 extends EulerTest {

    final int B = 10;
    final int L = 1000000;

    /**
     * Find the sum of all truncatable primes with at least two digits where every prefix and suffix
     * of the prime is also prime (e.g. 3797 is a truncatable prime because 3797, 797, 97, 7, 379,
     * 37, and 3 are all prime.
     */
    @Test
    public void test() {
        for (int p : primes(L))
            if (isTruncatablePrime(p))
                ans += p;
        check(748317);
    }

    boolean isTruncatablePrime(int n) {
        if (n < B)
            return false;
        for (int powB = B; powB < n; powB *= B)
            if (!isPrime(n % powB))
                return false;
        while (n > 0) {
            if (!isPrime(n))
                return false;
            n /= B;
        }
        return true;
    }
}
