
package lib;

import java.util.HashMap;
import java.util.Map;

public final class UnionFind<T> {

    private Map<T, T> parents = new HashMap<>();
    private Map<T, Integer> ranks = new HashMap<>();

    public static <T> UnionFind<T> create() {
        return new UnionFind<>();
    }

    public T find(T obj) {
        T rep = obj;
        while (parents.containsKey(rep))
            rep = parents.get(rep);
        while (parents.containsKey(obj))
            obj = parents.put(obj, rep);
        return rep;
    }

    public void union(T obj1, T obj2) {
        obj1 = find(obj1);
        obj2 = find(obj2);
        if (obj1.equals(obj2))
            return;
        int rank1 = ranks.getOrDefault(obj1, 0);
        int rank2 = ranks.getOrDefault(obj2, 0);
        if (rank1 < rank2)
            parents.put(obj1, obj2);
        else if (rank2 < rank1)
            parents.put(obj2, obj1);
        else {
            parents.put(obj1, obj2);
            ranks.put(obj2, rank2 + 1);
        }
    }
}
