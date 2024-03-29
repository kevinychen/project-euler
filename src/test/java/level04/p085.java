
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p085 extends EulerTest {

    final int N = 2000000;

    /**
     * Find the area of the grid that contains closest to N rectangles of any size.
     * <p>
     * In a n x 1 grid, there are n rectangles of size 1, n-1 rectangles of size 2, etc., for a
     * total of tr(n) rectangles. In a n x m grid, every rectangle corresponds to the cross product
     * of a rectangle in the n x 1 grid and a rectangle in the 1 x m grid, for a total of tr(n) x
     * tr(m) rectangles.
     */
    @Test
    public void test() {
        long bestNumRectangles = 0;
        for (int i = 1; tr(i) < 2 * N; i++)
            for (int j = 1; true; j++) {
                long numRectangles = tr(i) * tr(j);
                if (numRectangles >= 2 * N)
                    break;
                if (Math.abs(numRectangles - N) < Math.abs(bestNumRectangles - N)) {
                    bestNumRectangles = numRectangles;
                    ans = i * j;
                }
            }
        check(2772);
    }
}
