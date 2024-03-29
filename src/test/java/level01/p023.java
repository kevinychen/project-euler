
package level01;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p023 extends EulerTest {

    final int L = 28123;

    /**
     * Find the sum of all positive integers that cannot be written as the sum of two abundant
     * numbers.
     */
    @Test
    public void test() {
        preSumDivisors(L);
        List<Integer> abundants = list();
        for (int i : range(L))
            if (sumProperDivisors(i) > i)
                abundants.add(i);
        boolean[] sumOfAbundants = new boolean[2 * L];
        for (int abundant1 : abundants)
            for (int abundant2 : abundants)
                sumOfAbundants[abundant1 + abundant2] = true;
        for (int i : range(L))
            if (!sumOfAbundants[i])
                ans += i;
        check(4179871);
    }
}
