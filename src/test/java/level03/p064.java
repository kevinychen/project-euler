
package level03;

import org.junit.Test;

import lib.EulerTest;
import lib.Pell;

public class p064 extends EulerTest {

    final int N = 10000;

    /**
     * Find the number of continued fractions of √n for n≤N with an odd period.
     * <p>
     * √n has an odd period if and only if the negative Pell equation x² - n y² = -1 has a solution.
     */
    @Test
    public void test() {
        for (int i : range(N))
            if (!isSq(i) && Pell.negative(i).get(0) != null)
                ans++;
        check(1322);
    }
}
