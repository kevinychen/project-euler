
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p036 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the sum of all numbers under N that are palindromes in both base 10 and base 2.
     */
    @Test
    public void test() {
        for (int i : range(N))
            if (isPalindrome(i) && isPalindrome(Integer.toBinaryString(i)))
                ans += i;
        check(872187);
    }
}
