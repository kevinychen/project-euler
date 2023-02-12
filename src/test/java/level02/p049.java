
package level02;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p049 extends EulerTest {

    final int N = 4;
    final int B = 10;

    String ans;

    /**
     * Find the concatenation of the arithmetic sequence of N-digit primes A, B, C which are all
     * permutations of each other.
     */
    @Test
    public void test() {
        for (int p1 : primes(ipow(B, N))) {
            List<Integer> digits = digits(p1);
            permutations(digits).generateUntil(permutation -> {
                int p2 = Integer.parseInt(join(permutation));
                if (p2 > p1 && isPrime(p2)) {
                    int p3 = 2 * p2 - p1;
                    if (p3 < pow(B, N) && isPrime(p3) && isPermutation(digits, digits(p3))) {
                        ans = "" + p1 + p2 + p3;
                        return true;
                    }
                }
                return false;
            });
        }
        check(ans, "296962999629");
    }
}
