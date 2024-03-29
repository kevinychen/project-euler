
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p078 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the smallest number n such that the number of ways to separate n coins into piles is
     * divisible by N.
     */
    @Test
    public void test() {
        ans = -1;
        numPartitions(N).generateUntil(numPartitions -> {
            ans++;
            return numPartitions % N == 0;
        });
        check(55374);
    }
}
