
package level02;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p050 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the number less than N that is the sum of the most consecutive primes.
     */
    @Test
    public void test() {
        List<Integer> primes = primes(N);
        int maxNumPrimes = 0;
        for (int i : range(primes.size())) {
            int numPrimes = 0, sum = 0;
            for (int j = i; j < primes.size() && sum < N; j++) {
                if (numPrimes > maxNumPrimes && isPrime(sum)) {
                    maxNumPrimes = numPrimes;
                    ans = sum;
                }
                numPrimes++;
                sum += primes.get(j);
            }
        }
        check(997651);
    }
}
