
package level03;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p052 extends EulerTest {

    final int N = 6;

    /**
     * Find the smallest positive integer x such that x, 2x, ... N*x all contain the same digits.
     */
    @Test
    public void test() {
        int x = 1;
        while (!good(x))
            x++;
        ans = x;
        check(142857);
    }

    boolean good(int x) {
        List<Integer> digits = digits(x);
        for (int i : rangeC(2, N))
            if (!isPermutation(digits, digits(i * x)))
                return false;
        return true;
    }
}
