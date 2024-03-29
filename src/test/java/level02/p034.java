
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p034 extends EulerTest {

    final int L = ipow(10, 7);
    final int B = 10;
    final int C = iceilPow(isqrt(L), B);

    /**
     * Find the sum of all numbers > 2 that are equal to the sum of the factorials of their digits.
     * <p>
     * Let f(n) be the sum of the factorials of the digits of n. If n has 8+ digits, then f(n) is at
     * most 8x9! < 10^7, so we only need to check numbers with at most 7 digits.
     * <p>
     * As a further optimization, we note that if C is a power of 10 and b<C, then f(Ca+b) = f(a) +
     * f(C+b) - 1. (Note this may be different from f(a) + f(b) because b may have leading zeros;
     * therefore we sum the factorials of the digits of f(C+b), then subtract 1 for the factorial of
     * the leading 1).
     */
    @Test
    public void test() {
        int[] sumFactorials = new int[2 * C];
        for (int i : range(2 * C))
            for (int d : digits(i))
                sumFactorials[i] += factorial(d);
        for (int b : range(C)) {
            if (b > 2 && sumFactorials[b] == b)
                ans += b;
            int num;
            for (int a = 1; (num = C * a + b) < L; a++)
                if (sumFactorials[a] + sumFactorials[C + b] - 1 == num)
                    ans += num;
        }
        check(40730);
    }
}
