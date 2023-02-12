
package level02;

import org.junit.Test;

import com.google.common.collect.Multiset;

import lib.EulerTest;

public class p039 extends EulerTest {

    final int N = 1000;

    /**
     * Find p≤N where the number of right triangles with perimeter p is maximized.
     */
    @Test
    public void test() {
        Multiset<Long> perims = mset();
        pythagoreanTriples(N).generate(tr -> perims.add(tr.perim()));
        for (long perim : rangeC(N))
            if (perims.count(perim) > perims.count(ans))
                ans = perim;
        check(840);
    }
}
