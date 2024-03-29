
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p008 extends EulerTest {

    final int N = 13;

    /**
     * Find the N adjacent digits with the greatest product.
     */
    @Test
    public void test() {
        String s = join(read());
        for (int i : range(s.length() - N)) {
            long prod = 1;
            for (int d : digits(s.substring(i, i + N)))
                prod *= d;
            if (prod > ans)
                ans = prod;
        }
        check(23514624000L);
    }
}
