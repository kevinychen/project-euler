
package level03;

import org.junit.Test;

import com.google.common.collect.Multiset;

import lib.EulerTest;

public class p075 extends EulerTest {

    final int N = 1500000;

    /**
     * Find the number of lengths L≤N such that exactly one integral right triangle can be formed
     * from a wire of length L.
     */
    @Test
    public void test() {
        Multiset<Long> perims = mset();
        pythagoreanTriples(N).generate(tr -> perims.add(tr.perim()));
        for (long perim : rangeC(N))
            if (perims.count(perim) == 1)
                ans++;
        check(161667);
    }
}
