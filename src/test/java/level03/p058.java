
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p058 extends EulerTest {

    final double R = 0.1;
    final int K = 4;

    /**
     * Find the side length of a K-side spiral such that less than R of the numbers on the corners
     * are prime.
     */
    @Test
    public void test() {
        int sideLen = 1, numPrimes = 0;
        do {
            sideLen += 2;
            for (int i : range(1, K))
                if (isProbablePrime(sq(sideLen) - i * (sideLen - 1)))
                    numPrimes++;
        } while (!(numPrimes < R * (2 * sideLen - 1)));
        ans = sideLen;
        check(26241);
    }
}
