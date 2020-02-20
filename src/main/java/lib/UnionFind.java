
package lib;

public class UnionFind {

    private int[] parents, ranks, sizes;

    public UnionFind(int maxNumObjs) {
        parents = new int[maxNumObjs];
        for (int i = 0; i < maxNumObjs; i++)
            parents[i] = i;
        ranks = new int[maxNumObjs];
        sizes = new int[maxNumObjs];
        for (int i = 0; i < maxNumObjs; i++)
            sizes[i] = 1;
    }

    public int find(int n) {
        int rep = n;
        while (parents[rep] != rep)
            rep = parents[rep];
        while (parents[n] != n) {
            int temp = parents[n];
            parents[n] = rep;
            n = temp;
        }
        return rep;
    }

    public void union(int n1, int n2) {
        int rep1 = find(n1), rep2 = find(n2);
        if (rep1 == rep2)
            return;
        if (ranks[rep1] < ranks[rep2]) {
            parents[rep1] = rep2;
            sizes[rep2] += sizes[rep1];
        } else if (ranks[rep2] < ranks[rep1]) {
            parents[rep2] = rep1;
            sizes[rep1] += sizes[rep2];
        } else {
            parents[rep1] = rep2;
            ranks[rep2]++;
            sizes[rep2] += sizes[rep1];
        }
    }

    public int size(int n) {
        return sizes[find(n)];
    }
}
