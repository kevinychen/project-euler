
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p020 extends EulerTest {

    final int N = 100;

    /**
     * Find the sum of the digits in N!.
     */
    @Test
    public void test() {
        ans = sumDigits(bfactorial(N));
        check(648);
    }
}
