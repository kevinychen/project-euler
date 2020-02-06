
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
        for (; k < table.length; k += k & -k) {
            table[k] += v;
            if (M != Long.MAX_VALUE)
                table[k] %= M;
        }
    }

    /**
     * Finds the sum of all added values from 1 to k, inclusive.
     */
    public long sum(int k) {
        long sum = 0;
        for (; k > 0; k -= k & -k) {
            sum += table[k];
            if (M != Long.MAX_VALUE)
                sum %= M;
        }
        return sum;
    }
}
