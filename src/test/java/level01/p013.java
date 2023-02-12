
package level01;

import java.math.BigInteger;

import org.junit.Test;

import lib.EulerTest;

public class p013 extends EulerTest {

    final int N = 10;

    /**
     * Find the first N digits of the sum of the given numbers.
     */
    @Test
    public void test() {
        BigInteger n = big(0);
        for (String s : read())
            n = n.add(big(s));
        String ans = (n + "").substring(0, N);
        check(ans, "5537376230");
    }
}
