
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p003 extends EulerTest {

    final long N = 600851475143L;

    /**
     * Find the largest prime factor of N.
     */
    @Test
    public void test() {
        for (long p : lprimeFactor(N).keySet())
            if (p > ans)
                ans = p;
        check(6857);
    }
}
