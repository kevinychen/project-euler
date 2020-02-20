
package lib;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Multimap;

import lombok.Data;

public final class Graph<T> extends EulerLib {

    public final Set<T> vertices = set();
    public final Multimap<T, T> outgoing = mmap();
    public final Multimap<T, T> incoming = mmap();
    public final Multimap<Edge, Long> weights = mmap();

    public static <T> Graph<T> create() {
        return new Graph<>();
    }

    public long getTotalWeight(T start, T end) {
        long totalWeight = 0;
        for (long weight : weights.get(new Edge(start, end)))
            totalWeight += weight;
        return totalWeight;
    }

    public void setTotalWeight(T start, T end, long weight) {
        weights.replaceValues(new Edge(start, end), list(weight));
    }

    public Graph<T> addDirectedEdge(T start, T end) {
        return addDirectedEdge(start, end, 1);
    }

    public Graph<T> addDirectedEdge(T start, T end, long weight) {
        vertices.add(start);
        vertices.add(end);
        outgoing.put(start, end);
        incoming.put(end, start);
        weights.put(new Edge(start, end), weight);
        return this;
    }

    public Graph<T> addUndirectedEdge(T start, T end) {
        return addUndirectedEdge(start, end, 1);
    }

    public Graph<T> addUndirectedEdge(T start, T end, long weight) {
        return addDirectedEdge(start, end, weight).addDirectedEdge(end, start, weight);
    }

    public long dijkstra(T start, T end) {
        Map<T, Long> dists = map();
        Set<T> visited = set();
        dists.put(start, 0L);
        while (true) {
            T closestV = null;
            long closestDist = Long.MAX_VALUE;
            for (T v : dists.keySet())
                if (dists.get(v) < closestDist && !visited.contains(v)) {
                    closestDist = dists.get(v);
                    closestV = v;
                }
            if (closestV == null)
                return -1;
            else if (closestV.equals(end))
                return closestDist;
            visited.add(closestV);
            for (T neighbor : outgoing.get(closestV))
                for (long weight : weights.get(new Edge(closestV, neighbor))) {
                    long dist = closestDist + weight;
                    if (!dists.containsKey(neighbor) || dist < dists.get(neighbor))
                        dists.put(neighbor, dist);
                }
        }
    }

    public long bellmanFord(T start, T end) {
        Map<T, Long> dists = map(start, 0L);
        while (true) {
            boolean hasUpdate = false;
            for (Map.Entry<Edge, Long> entry : weights.entries()) {
                T edgeStart = entry.getKey().start;
                if (dists.containsKey(edgeStart)) {
                    T edgeEnd = entry.getKey().end;
                    long dist = dists.get(edgeStart) + entry.getValue();
                    if (!dists.containsKey(edgeEnd) || dist < dists.get(edgeEnd)) {
                        dists.put(edgeEnd, dist);
                        hasUpdate = true;
                    }
                }
            }
            if (!hasUpdate)
                return dists.getOrDefault(end, -1L);
        }
    }

    public long kruskal() {
        Map<T, Integer> ordering = indexMap(vertices);
        List<Entry<Edge, Long>> edges = list((Iterable<Entry<Edge, Long>>) weights.entries());
        Collections.sort(edges, (edge1, edge2) -> Long.compare(edge1.getValue(), edge2.getValue()));
        UnionFind uf = new UnionFind(vertices.size());
        long minimumSpanningTreeLen = 0;
        for (Entry<Edge, Long> edge : edges) {
            int start = ordering.get(edge.getKey().start);
            int end = ordering.get(edge.getKey().end);
            if (uf.find(start) != uf.find(end)) {
                minimumSpanningTreeLen += edge.getValue();
                uf.union(start, end);
            }
        }
        return minimumSpanningTreeLen;
    }

    /**
     * Assuming this is a residual graph, performs the Ford Fulkerson algorithm (with the simple BFS
     * strategy for finding augmenting paths), described here:
     * https://www.topcoder.com/community/competitive-programming/tutorials/maximum-flow-section-1.
     * The flow edges can be determined by inspecting the new residual values.
     */
    public long maxFlow(T start, T end) {
        for (Edge edge : list(weights.keySet()))
            if (!weights.containsKey(new Edge(edge.end, edge.start)))
                addDirectedEdge(edge.end, edge.start, 0);

        long flow = 0;
        while (true) {
            Map<T, T> prevVertex = map();
            List<T> currentVertices = list(start);
            Set<T> visited = set();
            while (!currentVertices.isEmpty() && !visited.contains(end)) {
                List<T> nextVertices = list();
                for (T u : currentVertices)
                    for (T v : outgoing.get(u))
                        if (!visited.contains(v) && getTotalWeight(u, v) > 0) {
                            prevVertex.put(v, u);
                            visited.add(v);
                            nextVertices.add(v);
                        }
                currentVertices = nextVertices;
            }
            if (!visited.contains(end))
                return flow;

            long minWeight = Long.MAX_VALUE;
            for (T v = end; v != start; v = prevVertex.get(v)) {
                long weight = getTotalWeight(prevVertex.get(v), v);
                if (weight < minWeight)
                    minWeight = weight;
            }
            for (T v = end; v != start; v = prevVertex.get(v)) {
                T u = prevVertex.get(v);
                setTotalWeight(u, v, getTotalWeight(u, v) - minWeight);
                setTotalWeight(v, u, getTotalWeight(v, u) + minWeight);
            }
            flow += minWeight;
        }
    }

    @Data
    private class Edge {
        final T start, end;
    }
}
