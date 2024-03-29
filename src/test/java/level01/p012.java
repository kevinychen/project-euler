
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p012 extends EulerTest {

    final int N = 500;
    final int L = 100000;

    /**
     * Find the smallest triangular number with over N divisors.
     * <p>
     * Since i and i+1 are relatively prime, numDivisors(tr(i)) = numDivisors(i/2) *
     * numDivisors(i+1) if i is even (and similarly if i is odd).
     */
    @Test
    public void test() {
        long[] numDivisors = numDivisors(L);
        for (int i : range(L))
            if (i % 2 == 0 && numDivisors[i / 2] * numDivisors[i + 1] > N
                    || i % 2 == 1 && numDivisors[i] * numDivisors[(i + 1) / 2] > N) {
                ans = tr(i);
                break;
            }
        check(76576500);
    }
}
