
package level02;

import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p032 extends EulerTest {

    final int L1 = 100, L = 10000;
    final int D = 9;

    /**
     * Find the sum of all integers that can be written as a product A x B = C such that the string
     * A.B.C is D-pandigital.
     * <p>
     * C cannot have at least 5 digits, because then A and B must together have at least 5 digits.
     * So C must have at most 4 digits, and consequently the smaller of A and B must have at most 2
     * digits.
     */
    @Test
    public void test() {
        Set<Integer> products = set();
        for (int i : range(1, L1))
            for (int j = i; i * j < L; j++)
                if (isPandigital("" + i + j + i * j, 1, D))
                    products.add(i * j);
        for (int n : products)
            ans += n;
        check(45228);
    }
}
