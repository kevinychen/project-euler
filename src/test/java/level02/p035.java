
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p035 extends EulerTest {

    final int B = 10;
    final int L = 1000000;

    /**
     * Find the sum of all circular primes, primes where all rotations of the prime are prime (e.g.
     * 197 is a circular prime because 197, 971, and 719 are prime.
     */
    @Test
    public void test() {
        for (int p : primes(L))
            if (isCircularPrime(p))
                ans++;
        check(55);
    }

    boolean isCircularPrime(int n) {
        int numDigits = Integer.toString(n, B).length();
        int scale = ipow(B, numDigits - 1);
        for (int i = 1; i < numDigits; i++) {
            n = (n % scale) * B + n / scale;
            if (!isPrime(n))
                return false;
        }
        return true;
    }
}
