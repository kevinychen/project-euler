
package level02;

import org.junit.Test;

import lib.EulerTest;

public class p038 extends EulerTest {

    final int D = 9;
    final int L = 10000;

    /**
     * Find the largest D-pandigital number formed by concatenating n, 2n, ... kn for some n and
     * k>1.
     */
    @Test
    public void test() {
        String ans = "";
        for (int i : range(L)) {
            StringBuilder sb = sb();
            for (int k = 1; sb.length() < D; k++)
                sb.append(i * k);
            String s = sb + "";
            if (s.compareTo(ans) > 0 && isPandigital(s, 1, D))
                ans = s;
        }
        check(ans, "932718654");
    }
}
