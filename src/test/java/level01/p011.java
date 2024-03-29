
package level01;

import org.junit.Test;

import data.Dir;
import lib.EulerTest;

public class p011 extends EulerTest {

    final int N = 20;
    final int K = 4;

    /**
     * Find the greatest product of K consecutive numbers in a NxN grid.
     */
    @Test
    public void test() {
        long[][] grid = readAsGrid(" ");
        for (int i : range(N))
            for (int j : range(N))
                for (Dir dir : Dir.ALL)
                    if (dir.inBounds(i, j, K - 1, N)) {
                        long prod = 1;
                        for (int k : range(K))
                            prod *= grid[i + k * dir.di][j + k * dir.dj];
                        if (prod > ans)
                            ans = prod;
                    }
        check(70600674);
    }
}
