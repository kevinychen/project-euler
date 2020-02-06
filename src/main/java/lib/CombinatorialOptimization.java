
package lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinatorialOptimization {

    /**
     * Finds the minimum possible sum by selecting either one number in every row or one number in
     * every column, with no two numbers in the same row or column, using the O(n⁴) Hungarian
     * algorithm described in
     * https://www.topcoder.com/community/competitive-programming/tutorials/assignment-problem-and-hungarian-algorithm.
     */
    public static long minimumMatching(long[][] originalCosts) {
        int H = originalCosts.length;
        int W = originalCosts[0].length;
        long[][] costs = new long[H][W];
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                costs[i][j] = originalCosts[i][j];

        // Step 0: optimize by subtracting as much as possible along entire rows and entire columns.
        if (H <= W)
            for (int i = 0; i < H; i++) {
                long minCost = Long.MAX_VALUE;
                for (int j = 0; j < W; j++)
                    if (costs[i][j] < minCost)
                        minCost = costs[i][j];
                for (int j = 0; j < W; j++)
                    costs[i][j] -= minCost;
            }
        if (W <= H)
            for (int j = 0; j < W; j++) {
                long minCost = Long.MAX_VALUE;
                for (int i = 0; i < H; i++)
                    if (costs[i][j] < minCost)
                        minCost = costs[i][j];
                for (int i = 0; i < H; i++)
                    costs[i][j] -= minCost;
            }

        // Construct a residual graph for incrementally computing max flow.
        Graph<String> graph = Graph.create();
        for (int i = 0; i < H; i++)
            graph.addDirectedEdge("start", "row" + i, 1);
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                if (costs[i][j] == 0)
                    graph.addDirectedEdge("row" + i, "col" + j);
        for (int j = 0; j < W; j++)
            graph.addDirectedEdge("col" + j, "end", 1);

        long totalFlow = 0;
        while (true) {
            // Step 1A: Find a maximum matching using only 0-weight edges.
            totalFlow += graph.maxFlow("start", "end");

            // Check if a perfect matching is found. If so, compute the sum based on the original costs.
            if (totalFlow == Math.min(H, W)) {
                int totalCost = 0;
                for (int i = 0; i < H; i++)
                    for (int j = 0; j < W; j++)
                        if (graph.getTotalWeight("col" + j, "row" + i) > 0)
                            totalCost += originalCosts[i][j];
                return totalCost;
            }

            // Step 1B: Find the maximum vertex cover V from the maximum matching using the construction in the proof of
            // Konig's Theorem: https://en.wikipedia.org/wiki/K%C5%91nig%27s_theorem_(graph_theory)#Proof.
            Set<String> Z = new HashSet<>();
            for (int i = 0; i < H; i++)
                if (graph.getTotalWeight("row" + i, "start") == 0)
                    Z.add("row" + i);
            List<String> vertices = new ArrayList<>(Z);
            for (int level = 0; !vertices.isEmpty(); level++) {
                List<String> nextVertices = new ArrayList<>();
                for (String u : vertices)
                    for (String v : graph.outgoing.get(u))
                        if (!Z.contains(v))
                            if (level % 2 == 0) {
                                if (v.startsWith("col") && graph.getTotalWeight(v, u) == 0)
                                    nextVertices.add(v);
                            } else {
                                if (v.startsWith("row") && graph.getTotalWeight(u, v) > 0)
                                    nextVertices.add(v);
                            }
                Z.addAll(nextVertices);
                vertices = nextVertices;
            }

            Set<String> minVertexCover = new HashSet<>();
            for (int i = 0; i < H; i++)
                if (!Z.contains("row" + i))
                    minVertexCover.add("row" + i);
            for (int j = 0; j < W; j++)
                if (Z.contains("col" + j))
                    minVertexCover.add("col" + j);

            // Step 2: Adjust costs and add new 0-weight edges to the graph. Then repeat.
            long minCost = Long.MAX_VALUE;
            for (int i = 0; i < H; i++)
                for (int j = 0; j < W; j++)
                    if (costs[i][j] < minCost && !minVertexCover.contains("row" + i) && !minVertexCover.contains("col" + j))
                        minCost = costs[i][j];
            for (int i = 0; i < H; i++)
                for (int j = 0; j < W; j++) {
                    if (minVertexCover.contains("row" + i) && minVertexCover.contains("col" + j))
                        costs[i][j] += minCost;
                    else if (!minVertexCover.contains("row" + i) && !minVertexCover.contains("col" + j))
                        costs[i][j] -= minCost;
                    if (costs[i][j] == 0)
                        graph.addDirectedEdge("row" + i, "col" + j);
                }
        }
    }

    /**
     * Finds the maximum possible sum by selecting numbers in the given matrix, with no two numbers
     * in the same row or column.
     */
    public static long maximumMatching(long[][] originalCosts) {
        int H = originalCosts.length;
        int W = originalCosts[0].length;
        long[][] costs = new long[H][W];
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                costs[i][j] = -originalCosts[i][j];
        return -minimumMatching(costs);
    }
}
