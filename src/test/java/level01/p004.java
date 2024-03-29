
package level01;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p004 extends EulerTest {

    final int N = 3;
    final int B = 10;

    /**
     * Find the largest palindrome that is a product of two N-digit numbers.
     */
    @Test
    public void test() {
        List<Integer> nums = range(ipow(B, N - 1), ipow(B, N));
        for (int i : nums)
            for (int j : nums)
                if (i * j > ans && isPalindrome(i * j))
                    ans = i * j;
        check(906609);
    }
}
