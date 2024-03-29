
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p002 extends EulerTest {

    final int N = 4000000;

    /**
     * Find the sum of all even Fibonacci numbers up to N.
     */
    @Test
    public void test() {
        for (long a : fibonaccis())
            if (a < N && a % 2 == 0)
                ans += a;
        check(4613732);
    }
}
