
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p021 extends EulerTest {

    final int N = 10000;

    /**
     * Find the sum of all amicable numbers under N.
     */
    @Test
    public void test() {
        preSumDivisors(N);
        for (int i : range(N)) {
            int friend = sumProperDivisors(i);
            if (i != friend && friend < N && sumProperDivisors(friend) == i)
                ans += i;
        }
        check(31626);
    }
}
