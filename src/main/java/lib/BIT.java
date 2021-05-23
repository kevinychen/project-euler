
package lib;

/**
 * Implementation of Fenwick Tree.
 */
public class BIT {

    private final long[] table;
    private final long M;

    public BIT(int size, long M) {
        this.table = new long[size + 1];
        this.M = M;
    }

    public void add(int k, long v) {
        if (v < 0 || v >= M) {
            v %= M;
            if (v < 0)
                v += M;
        }
        for (; k < table.length; k += k & -k) {
            table[k] += v;
            if (table[k] >= M)
                table[k] -= M;
        }
    }

    /**
     * Finds the sum of all added values from 1 to k, inclusive.
     */
    public long sum(int k) {
        long sum = 0;
        for (; k > 0; k -= k & -k) {
            sum += table[k];
            if (sum >= M)
                sum -= M;
        }
        return sum;
    }
}
