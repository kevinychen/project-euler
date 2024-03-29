
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p076 extends EulerTest {

    final int N = 100;

    /**
     * Find the number of ways to write N as the sum of at least two positive integers.
     */
    @Test
    public void test() {
        ans = numPartitions().get(N) - 1;
        check(190569291);
    }
}
