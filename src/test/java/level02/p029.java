
package level02;

import java.math.BigInteger;
import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p029 extends EulerTest {

    final int N = 100;

    /**
     * Find the number of distinct values of a^b where 2≤a,b≤N.
     */
    @Test
    public void test() {
        Set<BigInteger> nums = set();
        for (int a : rangeC(2, N))
            for (int b : rangeC(2, N))
                nums.add(big(a).pow(b));
        ans = nums.size();
        check(9183);
    }
}
