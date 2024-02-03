
package level04;

import org.junit.Test;

import lib.EulerTest;
import lib.Pell;

public class p100 extends EulerTest {

    final long N = pow(10, 12);

    /**
     * Find the number of blue discs in the smallest arrangement of over N blue and red discs such
     * that the probability of taking two blue discs is exactly 1/2. For example, if there are 15
     * blue discs and 6 red discs, then the probability of taking two blue discs is (15/21)(14/20) =
     * 1/2.
     * <p>
     * Let the number of discs be n and the number of blue discs be b. We have
     * <li>(b/n)*((b-1)/(n-1))=1/2
     * <li>=> (2n-1)² - 2(2b-1)² = -1.
     * <p>
     * This base solution for this negative Pell equation can be solved by convergent continued
     * fractions to √2, and other solutions can be found by multiplying by (x+y√2)^k, where (x, y)
     * are the solutions to x²-2y²=1.
     */
    @Test
    public void test() {
        Pell.negative(2).generateUntil(sol -> {
            ans = (sol.y.longValue() + 1) / 2;
            return (sol.x.longValue() + 1) / 2 > N;
        });
        check(756872327473L);
    }
}
