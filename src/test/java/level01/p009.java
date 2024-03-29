
package level01;

import org.junit.Test;

import lib.EulerTest;

public class p009 extends EulerTest {

    final int N = 1000;

    /**
     * Find the unique Pythagorean triple with perimeter N.
     */
    @Test
    public void test() {
        pythagoreanTriples(N).generateUntil(tr -> {
            ans = tr.a * tr.b * tr.c;
            return tr.perim() == N;
        });
        check(31875000);
    }
}
