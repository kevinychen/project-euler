
package level04;

import org.junit.Test;

import data.IPoint;
import lib.EulerTest;
import lib.Graph;

public class p083 extends EulerTest {

    final int N = 80;
    final Object START = "start";

    /**
     * Find the minimum sum of numbers on a path from the top left to the bottom right through the
     * specified NxN grid.
     */
    @Test
    public void test() {
        long[][] grid = readAsGrid(",");
        Graph<Object> graph = Graph.create();
        graph.addDirectedEdge(START, new IPoint(0, 0), grid[0][0]);
        for (int i : range(N))
            for (int j : range(1, N)) {
                graph.addDirectedEdge(new IPoint(i, j - 1), new IPoint(i, j), grid[i][j]);
                graph.addDirectedEdge(new IPoint(i, j), new IPoint(i, j - 1), grid[i][j - 1]);
                graph.addDirectedEdge(new IPoint(j - 1, i), new IPoint(j, i), grid[j][i]);
                graph.addDirectedEdge(new IPoint(j, i), new IPoint(j - 1, i), grid[j - 1][i]);
            }
        ans = graph.bellmanFord(START, new IPoint(N - 1, N - 1));
        check(425185);
    }
}
