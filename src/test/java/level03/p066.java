
package level03;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import lib.EulerTest;
import lib.Pell;
import lib.Pell.PQaStep;

public class p066 extends EulerTest {

    final int N = 1000;

    /**
     * Find D≤N for which the minimum solution of x² - D y² = 1 has the largest x.
     * <p>
     * Similar to {@link Pell#standard}, but recomputes the solutions using BigIntegers.
     */
    @Test
    public void test() {
        BigInteger maxX = big(0);
        for (int D : rangeC(N))
            if (!isSq(D)) {
                List<PQaStep> steps = list();
                Pell.PQa(0, D, 1).generateUntil(step -> {
                    steps.add(step);
                    return step.a == 2 * steps.get(0).a && steps.size() % 2 == 1;
                });
                removeLast(steps);
                List<BigInteger> xs = list(big(0), big(1));
                for (PQaStep step : steps)
                    xs.add(big(step.a).multiply(last(xs)).add(penult(xs)));
                if (last(xs).compareTo(maxX) > 0) {
                    maxX = last(xs);
                    ans = D;
                }
            }
        check(661);
    }
}
