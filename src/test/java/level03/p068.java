
package level03;

import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p068 extends EulerTest {

    final int N = 10;
    final int C = N / 2;

    String ans;

    /**
     * Find the maximum string when the numbers from 1 to N are arranged in a magic ring. The ring
     * consists of half of the numbers in an n-gon, and the other half jutting out the ends, such
     * that every row of 3 numbers have the same sum. For example, the string for the magic ring
     * below is "432 621 513".
     *
     * <pre>
     *   4
     *    \
     *     3
     *    / \
     *   1---2---6
     *  /
     * 5
     * </pre>
     */
    @Test
    public void test() {
        permutations(rangeC(1, N)).generate(p -> {
            for (int i = 1; i < C; i++)
                if (p.get(i + C) < p.get(C))
                    return;
            Set<Integer> sums = set();
            for (int i = 0; i < C; i++)
                sums.add(p.get(i + C) + p.get(i) + p.get((i + 1) % C));
            if (sums.size() == 1) {
                StringBuilder sb = sb();
                for (int i = 0; i < C; i++)
                    sb.append("" + p.get(i + C) + p.get(i) + p.get((i + 1) % C));
                String s = sb + "";
                if (ans == null || s.compareTo(ans) > 0)
                    ans = s;
            }
        });
        check(ans, "6531031914842725");
    }
}
