
package level03;

import org.junit.Test;

import lib.EulerTest;
import lib.NumberTheory;

public class p072 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the number of reduced proper fractions with denominator≤N.
     */
    @Test
    public void test() {
        ans = NumberTheory.sumPhis(N).div(1) - 1;
        check(303963552391L);
    }
}
