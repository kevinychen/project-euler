
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p086 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the least value of M such that there are over N cuboids with size≤M where the shortest
     * distance between two opposite vertices is an integer.
     * <p>
     * Given a cuboid with longest side M and other sides a and b, the shortest distance between two
     * opposite vertices is hypot(a+b, M). For efficiency, compute all (a+b) where this distance is
     * an integer, and for each (a+b) compute the number of pairs (a, b) that have that sum.
     */
    @Test
    public void test() {
        int longestSide = 1;
        for (int numSolutions = 0; true; longestSide++) {
            for (int sumOtherSides : rangeC(2, 2 * longestSide))
                if (isSq(sq(sumOtherSides) + sq(longestSide)))
                    numSolutions += Math.min(sumOtherSides, 2 * longestSide + 2 - sumOtherSides) / 2;
            if (numSolutions > N)
                break;
        }
        ans = longestSide;
        check(1818);
    }
}
