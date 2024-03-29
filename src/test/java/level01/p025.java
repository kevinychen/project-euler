
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p025 extends EulerTest {

    final int N = 1000;
    final int B = 10;

    /**
     * Find the index of the first Fibonacci number with N digits.
     */
    @Test
    public void test() {
        int numDigits = 1;
        for (double a = 1, b = 1; numDigits < N; b += a, a = b - a) {
            ans++;
            if (a >= B) {
                numDigits++;
                a /= B;
                b /= B;
            }
        }
        check(4782);
    }
}
