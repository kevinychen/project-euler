package lib;

import java.util.function.Function;

import data.QuotientValues;

/**
 * A Dirichlet series is an infinite series of the form f(s) = Σ_n (a_n) / n^s.
 * <p>
 * It is represented internally by a {@link QuotientValues} where f(k) is the cumulative sum of
 * the numerators, f(k) = Σ_{n=1}^k a_n (for some cap N), because this allows efficient
 * multiplication/division of Dirichlet series in O(N^{2/3}) time.
 */
public class DirichletSeriesContext {

    private final long N;
    private final long M;
    private final int L, L2;

    private final long[] numDivisors;
    private final int[] startIndices;
    private final int[] divisors;

    public DirichletSeriesContext(long N) {
        this(N, Long.MAX_VALUE);
    }

    public DirichletSeriesContext(long N, long M) {
        this.N = N;
        this.M = M;
        this.L = (int) Math.cbrt(N * Math.log(N)) + 1;
        this.L2 = (int) (N / L);

        numDivisors = EulerLib.numDivisors(L2);

        startIndices = new int[L2 + 2];
        for (int i = 1; i <= L2; i++)
            startIndices[i + 1] = (int) (startIndices[i] + numDivisors[i]);

        int[] currIndices = new int[L2 + 1];
        for (int i = 0; i <= L2; i++)
            currIndices[i] = startIndices[i];

        divisors = new int[startIndices[L2 + 1]];
        for (int d = 1; d <= L2; d++)
            for (int mult = 1; d * mult <= L2; mult++)
                divisors[currIndices[d * mult]++] = d;
    }

    /**
     * Returns (a_1)/1^s + (a_2)/2^s + (a_3)/3^s + ... (a_1 is the first value)
     */
    public QuotientValues create(int... a_n) {
        long[] cumSums = new long[a_n.length + 1];
        for (int i = 0; i < a_n.length; i++)
            cumSums[i + 1] = cumSums[i] + a_n[i];
        return create(n -> cumSums[(int) Math.min(n, a_n.length)]);
    }

    /**
     * Returns Σ_n (a_n) / n^s where cumSum(k) = Σ_{n=1}^k a_n.
     */
    public QuotientValues create(Function<Long, Number> cumSum) {
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        for (int i = 1; i <= L2; i++)
            small[i] = cumSum.apply((long) i).longValue();
        for (int i = L; --i >= 1;)
            big[i] = cumSum.apply(N / i).longValue();
        return new QuotientValues(N, big, small);
    }

    public QuotientValues add(QuotientValues ds1, QuotientValues ds2) {
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        for (int i = 1; i <= L2; i++)
            small[i] = ds1.get(i) + ds2.get(i);
        for (int i = L; --i >= 1;)
            big[i] = ds1.div(i) + ds2.div(i);
        return new QuotientValues(N, big, small);
    }

    public QuotientValues add(QuotientValues... ds) {
        QuotientValues res = ds[0];
        for (int i = 1; i < ds.length; i++)
            res = add(res, ds[i]);
        return res;
    }

    public QuotientValues subtract(QuotientValues ds1, QuotientValues ds2) {
        return add(ds1, multiply(ds2, -1));
    }

    public QuotientValues multiply(QuotientValues ds, long scale) {
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        for (int i = 1; i <= L2; i++)
            small[i] = ds.get(i) * scale;
        for (int i = L; --i >= 1;)
            big[i] = ds.div(i) * scale;
        return new QuotientValues(N, big, small);
    }

    public QuotientValues multiply(QuotientValues ds1, QuotientValues ds2) {
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        for (int i = 1; i <= L2; i++) {
            small[i] = small[i - 1];
            for (int j = 0; j < numDivisors[i]; j++) {
                int d = divisors[startIndices[i] + j];
                small[i] += (ds1.get(d) - ds1.get(d - 1)) * (ds2.get(i / d) - ds2.get(i / d - 1));
            }
        }
        for (int i = L; --i >= 1;) {
            int limit = EulerLib.isqrt(N / i);
            for (int x = 1; x <= limit; x++)
                big[i] += (ds1.get(x) - ds1.get(x - 1)) * ds2.div(i * x);
            for (int y = 1; y <= limit; y++)
                big[i] += (ds2.get(y) - ds2.get(y - 1)) * ds1.div(i * y);
            big[i] -= ds1.get(limit) * ds2.get(limit);
        }
        return new QuotientValues(N, big, small);
    }

    public QuotientValues multiply(QuotientValues... ds) {
        QuotientValues res = ds[0];
        for (int i = 1; i < ds.length; i++)
            res = multiply(res, ds[i]);
        return res;
    }

    public QuotientValues reciprocal(QuotientValues ds) {
        if (Math.abs(ds.get(1)) != 1)
            throw new IllegalArgumentException("Dirichlet series is not invertible.");
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        small[1] = ds.get(1);
        for (int i = 2; i <= L2; i++) {
            small[i] = small[i - 1];
            for (int j = 1; j < numDivisors[i]; j++) {
                int d = divisors[startIndices[i] + j];
                small[i] += -ds.get(1) * (ds.get(d) - ds.get(d - 1)) * (small[i / d] - small[i / d - 1]);
            }
            small[i] %= M;
        }
        for (int i = L; --i >= 1;) {
            int limit = EulerLib.isqrt(N / i);
            for (int x = 2; x <= limit; x++)
                big[i] += (ds.get(x) - ds.get(x - 1)) * (i * x < L ? big[i * x] : small[(int) (N / (i * x))]);
            for (int y = 1; y <= limit; y++)
                big[i] += (small[y] - small[y - 1]) * (ds.div(i * y) - ds.get(1));
            big[i] -= (ds.get(limit) - ds.get(1)) * small[limit];
            big[i] = ds.get(1) * (1 - big[i]);
            big[i] %= M;
        }
        return new QuotientValues(N, big, small);
    }

    public QuotientValues divide(QuotientValues ds1, QuotientValues ds2) {
        return multiply(ds1, reciprocal(ds2));
    }
}