
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p024 extends EulerTest {

    final int N = 1000000;
    final int K = 9;

    /**
     * Find the Nth permutation when all permutations of 0 to K are sorted.
     */
    @Test
    public void test() {
        String ans = join(permutations(rangeC(K)).get(N - 1));
        check(ans, "2783915460");
    }
}
