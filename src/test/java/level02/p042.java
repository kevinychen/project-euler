
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p042 extends EulerTest {

    /**
     * Find the number of triangle words, words whose letters add up to a triangle number (A=1, B=2,
     * ... Z=26).
     */
    @Test
    public void test() {
        for (String s : readAsCommaSeparatedStrings())
            if (isTr(wordValue(s)))
                ans++;
        check(162);
    }

    boolean isTr(long n) {
        return isSq(1 + 8 * n);
    }
}
