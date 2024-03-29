
package level03;

import java.math.BigInteger;

import org.junit.Test;

import lib.EulerTest;

public class p057 extends EulerTest {

    final int N = 1000;
    final int NUM = 3, DEN = 2;

    /**
     * Find the number of the first N continued fraction approximations A/B to √2, starting with
     * NUM/DEN, such that A has more digits than B.
     * <p>
     * Given the approximation A/B, the next approximation is 1+1/(1+A/B) = (A+2B)/(A+B).
     */
    @Test
    public void test() {
        BigInteger num = big(NUM), den = big(DEN);
        for (int i = 0; i < N; i++) {
            if (num.toString().length() > den.toString().length())
                ans++;
            num = num.add(den).add(den);
            den = num.subtract(den);
        }
        check(153);
    }
}
