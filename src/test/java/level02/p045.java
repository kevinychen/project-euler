
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p045 extends EulerTest {

    final long N = 40755;

    /**
     * Find the smallest triangular number>N that is also pentagonal and hexagonal.
     * <p>
     * Since every hexagonal number is triangular, find the smallest hexagonal number that is
     * pentagonal.
     */
    @Test
    public void test() {
        for (int i = 1; true; i++) {
            long hexagonal = figurate(i, 6);
            if (hexagonal > N && isPentagonal(hexagonal)) {
                ans = hexagonal;
                break;
            }
        }
        check(1533776805);
    }

    boolean isPentagonal(long n) {
        return isSq(1 + 24 * n) && isqrt(1 + 24 * n) % 6 == 5;
    }
}
