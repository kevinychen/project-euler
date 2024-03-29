
package level03;

import java.math.BigInteger;

import org.junit.Test;

import lib.EulerTest;

public class p063 extends EulerTest {

    final int L = 9;

    /**
     * Find the number of n-digit positive integers that are nth powers, for any n.
     * <p>
     * If b≥10, then b^n will have at least n+1 digits, so only check base≤9. Increment n and stop
     * once b^n has fewer than n digits, because if b≤9, then incrementing n will not cause b^n to
     * grow by more than a single digit.
     */
    @Test
    public void test() {
        for (int i : rangeC(1, L)) {
            BigInteger base = big(i);
            int len;
            for (int n = 1; (len = (base.pow(n) + "").length()) >= n; n++)
                if (len == n)
                    ans++;
        }
        check(49);
    }
}
