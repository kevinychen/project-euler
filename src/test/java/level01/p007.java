
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p007 extends EulerTest {

    final int N = 10001;

    /**
     * Find the Nth prime number.
     */
    @Test
    public void test() {
        ans = getPrimes(N).get(N - 1);
        check(104743);
    }
}
