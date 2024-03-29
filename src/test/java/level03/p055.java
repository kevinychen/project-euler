
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p055 extends EulerTest {

    final int N = 10000;
    final int K = 50;

    /**
     * Find the number of Lychrel numbers below N, defined as a number that does not form a
     * palindrome after adding tself to its reverse K times.
     */
    @Test
    public void test() {
        for (int i : range(N))
            if (isLychrel(i + ""))
                ans++;
        check(249);
    }

    boolean isLychrel(String s) {
        for (int i = 0; i < K; i++) {
            s = big(s).add(big(sb(s).reverse())) + "";
            if (isPalindrome(s))
                return false;
        }
        return true;
    }
}
