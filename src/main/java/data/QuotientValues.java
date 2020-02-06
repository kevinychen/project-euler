
package data;

import lombok.Data;

/**
 * Given an integer n, this class stores values of f(⌊n/k⌋) for all integer k. This is stored in two
 * separate arrays: the values are stored in big[k] directly for small k, and for large k they are
 * stored implicitly as small[i] = f(i).
 */
@Data
public class QuotientValues {

    private final long n;
    private final long[] big;
    private final long[] small;

    public long get(int i) {
        return small[i];
    }

    /**
     * Return f(⌊n/k⌋).
     */
    public long div(long k) {
        return k < big.length ? big[(int) k] : small[(int) (n / k)];
    }
}
