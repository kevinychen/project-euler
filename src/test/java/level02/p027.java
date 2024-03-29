
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p027 extends EulerTest {

    final int N = 1000;
    final int L = 100000;

    /**
     * Find the product of a and b, with |a|<N and |b|≤N, such that n²+an+b generates the most
     * primes for whole numbers n starting with 0.
     */
    @Test
    public void test() {
        preff(L);
        int maxNumPrimes = 0;
        for (int a : range(-N + 1, N))
            for (int b : rangeC(-N, N)) {
                int n = 0;
                while (isPrime(isq(n) + a * n + b))
                    n++;
                int numPrimes = n;
                if (numPrimes > maxNumPrimes) {
                    maxNumPrimes = numPrimes;
                    ans = a * b;
                }
            }
        check(-59231);
    }
}
