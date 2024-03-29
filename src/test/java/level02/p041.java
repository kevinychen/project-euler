
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p041 extends EulerTest {

    final int L = ipow(10, 7);

    /**
     * Find the largest pandigital prime.
     * <p>
     * No primes with 8 or 9 digits can exist, because the sum of its digits would be 36 or 45
     * respectively.
     */
    @Test
    public void test() {
        for (int p : primes(L)) {
            String s = p + "";
            if (isPandigital(s, 1, s.length()))
                ans = p;
        }
        check(7652413);
    }
}
