
package level02;

import org.junit.Test;

import data.LFraction;
import lib.EulerTest;

public class p033 extends EulerTest {

    final int B = 10;

    /**
     * Find the product of all ratios of 2-digit numbers [AB]/[CD] such that erroneously canceling
     * [AB]/[CD] = A/D (if B=C) or [AB]/[CD] = B/C (if A=D) is still correct. Return the denominator
     * of this product.
     */
    @Test
    public void test() {
        LFraction product = LFraction.integer(1);
        for (int a = B; a < sq(B); a++)
            for (int b = a + 1; b < sq(B); b++) {
                LFraction f = LFraction.reduced(a, b);
                if (a % B == b / B && LFraction.reduced(a / B, b % B).equals(f)
                        || a / B == b % B && LFraction.reduced(a % B, b / B).equals(f)) {
                    product = product.multiply(f);
                }
            }
        ans = product.den;
        check(100);
    }
}
