
package level04;

import org.junit.Test;

import data.IPoint;
import lib.EulerTest;
import lib.Graph;

public class p082 extends EulerTest {

    final int N = 80;
    final Object START = "start", END = "end";

    /**
     * Find the minimum sum of numbers on a path from the left column to the right column in the
     * specified NxN grid, moving only up, down, and right.
     */
    @Test
    public void test() {
        long[][] grid = readAsGrid(",");
        Graph<Object> graph = Graph.create();
        graph.addDirectedEdge(START, new IPoint(0, 0), grid[0][0]);
        for (int i : range(N)) {
            graph.addDirectedEdge(START, new IPoint(i, 0), grid[i][0]);
            for (int j : range(1, N)) {
                graph.addDirectedEdge(new IPoint(i, j - 1), new IPoint(i, j), grid[i][j]);
                graph.addDirectedEdge(new IPoint(j - 1, i), new IPoint(j, i), grid[j][i]);
                graph.addDirectedEdge(new IPoint(j, i), new IPoint(j - 1, i), grid[j - 1][i]);
            }
            graph.addDirectedEdge(new IPoint(i, N - 1), END, 0);
        }
        ans = graph.bellmanFord(START, END);
        check(260324);
    }
}
