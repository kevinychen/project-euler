
package level04;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

import org.junit.Test;

import data.LFraction;
import lib.EulerTest;

public class p093 extends EulerTest {

    final int N = 4;
    final int B = 10;
    final List<BiFunction<LFraction, LFraction, LFraction>> OPERATIONS = list((f1, f2) -> f1.add(f2),
        (f1, f2) -> f1.subtract(f2), (f1, f2) -> f2.subtract(f1), (f1, f2) -> f1.multiply(f2),
        (f1, f2) -> f1.divide(f2), (f1, f2) -> f2.divide(f1));

    String ans;

    /**
     * Find the N digits a, b, c, d such that the maximum number of consecutive positive integers 1,
     * 2, ... n can be obtained by adding, subtracting, multiplying, and dividing the digits.
     */
    @Test
    public void test() {
        AtomicLong maxNumObtainable = new AtomicLong();
        combinations(range(B), N).generate(digits -> {
            List<LFraction> values = list();
            for (int digit : digits)
                values.add(LFraction.integer(digit));
            Set<Long> obtainable = set();
            helper(values, obtainable);
            long numObtainable = 0;
            while (obtainable.contains(numObtainable + 1))
                numObtainable++;
            if (numObtainable > maxNumObtainable.get()) {
                maxNumObtainable.set(numObtainable);
                ans = join(digits);
            }
        });
        check(ans, "1258");
    }

    void helper(List<LFraction> values, Set<Long> obtainable) {
        if (values.size() == 1) {
            if (values.get(0).den == 1)
                obtainable.add(values.get(0).num);
            return;
        }
        for (int i : range(values.size()))
            for (int j : range(i + 1, values.size())) {
                LFraction temp2 = values.remove(j);
                LFraction temp1 = values.remove(i);
                for (BiFunction<LFraction, LFraction, LFraction> f : OPERATIONS) {
                    values.add(f.apply(temp1, temp2));
                    helper(values, obtainable);
                    removeLast(values);
                }
                values.add(i, temp1);
                values.add(j, temp2);
            }
    }
}
