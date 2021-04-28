
package lib;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.LFraction;
import lombok.Data;

public class FloorSums extends EulerLib {

    private static final int MAX_ORDER = 10;

    private final long M;
    private final long[][] nCrs;
    private final LFraction[][] sumPowerCoefficients;
    private final Map<Key, Long> cache;

    public FloorSums(long M) {
        this.M = M;
        this.nCrs = nCrs(MAX_ORDER, M);
        this.sumPowerCoefficients = new LFraction[MAX_ORDER][];
        for (int exp = 0; exp < MAX_ORDER; exp++)
            sumPowerCoefficients[exp] = sumPowerCoefficients(exp);
        this.cache = new HashMap<>();
    }

    /**
     * Computes Σ_{x=1}^N ⌊(a/b) x⌋ in O(log N) time, the number of lattice points where 1 ≤ x ≤ N
     * above the x-axis, and on or below the line y = (a/b) x.
     */
    public long numLatticePoints(long N, long a, long b) {
        return numLatticePoints(N, a, b, false);
    }

    /**
     * Computes Σ_{x=1}^N ⌊(a/b) x⌋ in O(log N) time, the number of lattice points where 1 ≤ x ≤ N
     * above the x-axis, and on or below the line y = (a/b) x. If strict=true, computes the number
     * of lattice points strictly below y = (a/b) x.
     */
    private long numLatticePoints(long N, long a, long b, boolean strict) {
        if (a == 0)
            return strict ? -N : 0;
        if (a >= b)
            return numLatticePoints(N, a % b, b, strict) + a / b * tr(N);
        long m = (a * N - (strict ? 1 : 0)) / b;
        return N * m - numLatticePoints(m, b, a, !strict);
    }

    /**
     * Computes Σ_{x=x0}^{x1} ⌊(c-ax)/b⌋ in logarithmic time, the number of lattice points where x0
     * ≤ x ≤ x1 above the x-axis, and on or below the line ax + by = c. If strict=true, computes the
     * number of lattice points strictly below ax + by = c. The line must be above the x-axis in
     * this region.
     */
    private long numLatticePoints(long x0, long x1, long a, long b, long c, boolean strict) {
        if (x0 > x1)
            return 0;
        long g = gcd(a, b);
        a /= g;
        b /= g;
        c /= g;
        long x = linComb(a, b, c).x;
        if (a * b < 0) {
            if (x > x0)
                x -= roundUp(x - x0, b);
            return numLatticePoints(x1 - x, Math.abs(a), Math.abs(b), strict)
                    - numLatticePoints(x0 - x - 1, Math.abs(a), Math.abs(b), strict)
                    + (c - a * x) / b * (x1 - x0 + 1);
        } else {
            if (x < x1)
                x += roundUp(x1 - x, b);
            return numLatticePoints(x - x0, Math.abs(a), Math.abs(b), strict)
                    - numLatticePoints(x - x1 - 1, Math.abs(a), Math.abs(b), strict)
                    + (c - a * x) / b * (x1 - x0 + 1);
        }
    }

    /**
     * Computes the number of lattice points where x0 ≤ x ≤ x1 that satisfy all the given
     * constraints. The runtime is O(k³) (where k is the number of constraints) but logarithmic in
     * x0, x1, and the values of the constraints.
     */
    public long numLatticePoints(long x0, long x1, List<Constraint> constraints) {
        Set<Long> xs = set(x0, x1);
        for (int i = 0; i < constraints.size(); i++)
            for (int j = i + 1; j < constraints.size(); j++) {
                Constraint c1 = constraints.get(i);
                Constraint c2 = constraints.get(j);
                long den = c1.a * c2.b - c2.a * c1.b;
                if (den != 0) {
                    long x = (c1.c * c2.b - c2.c * c1.b) / den;
                    if (x > x0 && x < x1)
                        xs.add(x);
                }
            }
        List<Long> sorted_xs = list(xs);
        Collections.sort(sorted_xs);
        long numLatticePoints = 0;
        for (long x : sorted_xs) {
            Constraint largest = null, smallest = null;
            double largest_y = Double.MAX_VALUE;
            double smallest_y = -Double.MAX_VALUE;
            for (Constraint c : constraints) {
                double y = (c.c - (double) c.a * x) / c.b;
                if (c.b > 0 && y < largest_y) {
                    largest = c;
                    largest_y = y;
                } else if (c.b < 0 && y > smallest_y) {
                    smallest = c;
                    smallest_y = y;
                }
            }
            if (largest_y >= smallest_y) {
                numLatticePoints += largest.strict ? Math.ceil(largest_y) - 1 : Math.floor(largest_y);
                numLatticePoints -= smallest.strict ? Math.floor(smallest_y) : Math.ceil(smallest_y) - 1;
            }
        }
        for (int i = 0; i < sorted_xs.size() - 1; i++) {
            long x = sorted_xs.get(i) + 1;
            long xe = sorted_xs.get(i + 1) - 1;
            Constraint largest = null, smallest = null;
            double largest_y = Double.MAX_VALUE;
            double smallest_y = -Double.MAX_VALUE;
            for (Constraint c : constraints) {
                double y = (c.c - (double) c.a * x) / c.b;
                if (c.b > 0 && y < largest_y) {
                    largest = c;
                    largest_y = y;
                } else if (c.b < 0 && y > smallest_y) {
                    smallest = c;
                    smallest_y = y;
                }
            }
            if (largest_y >= smallest_y) {
                numLatticePoints += numLatticePoints(x, xe, largest.a, largest.b, largest.c, largest.strict);
                numLatticePoints -= numLatticePoints(x, xe, smallest.a, smallest.b, smallest.c, !smallest.strict);
            }
        }
        return numLatticePoints;
    }

    /**
     * Computes sum_{x=1}^N (x^K ⌊F*x⌋^L) in O((K + L)⁴ log N) time.
     *
     * See https://discuss.codechef.com/t/window-editorial/1069.
     */
    public long sumPowers(long N, ContinuedFraction F, int K, int L) {
        cache.clear();
        return sumPowers(N, F, K, L, false);
    }

    /**
     * Computes sum_{x=1}^n (x^k ⌊f*x⌋^j) if minusEpsilon is false, or sum_{x=1}^n (x^k ⌊f*x - e⌋^j)
     * if minusEpsilon is true, where e is an infinitesimally small number.
     */
    private long sumPowers(long n, ContinuedFraction f, int k, int j, boolean minusEpsilon) {
        return memoize(cache, new Key(n, f, k, j, minusEpsilon), key -> {
            if (n == 0)
                return 0L;
            if (j == 0)
                return sumPowers(n, k, M);
            if (f.isZero())
                return minusEpsilon ? parity(j) * sumPowers(n, k, M) : 0;
            long q = f.integerPart();
            if (q >= 1) {
                long sumFloorMults = 0;
                for (int i = 0; i <= j; i++)
                    sumFloorMults += nCrs[j][i] * pow(q, i, M) % M * sumPowers(n, f.fractionalPart(), k + i, j - i, minusEpsilon);
                return mod(sumFloorMults, M);
            } else {
                long m = f.multiplyAndFloor(n, minusEpsilon);
                long res = 0;
                for (int i = 0; i < j; i++)
                    for (int h = 1; h <= k + 1; h++) {
                        res += nCrs[j][i] * parity(j - i) * sumPowers(m, f.inverse(), i, h, !minusEpsilon) % M
                                * sumPowerCoefficients[k][h].num % M * modInv(sumPowerCoefficients[k][h].den, M);
                    }
                return mod(sumPowers(n, k, M) * pow(m, j, M) + res, M);
            }
        });
    }

    /**
     * Represents a constraint ax + by ≤ c (or ax + by < c if strict = true).
     */
    @Data
    public static class Constraint {

        final long a, b, c;
        final boolean strict;
    }

    public interface ContinuedFraction {

        boolean isZero();

        long integerPart();

        ContinuedFraction fractionalPart();

        ContinuedFraction inverse();

        long multiplyAndFloor(long n, boolean minusEpsilon);
    }

    @Data
    public static class RationalContinuedFraction implements ContinuedFraction {

        final long num, den;

        @Override
        public boolean isZero() {
            return num == 0;
        }

        @Override
        public long integerPart() {
            return num / den;
        }

        @Override
        public ContinuedFraction fractionalPart() {
            return new RationalContinuedFraction(num % den, den);
        }

        @Override
        public ContinuedFraction inverse() {
            return new RationalContinuedFraction(den, num);
        }

        @Override
        public long multiplyAndFloor(long n, boolean minusEpsilon) {
            return (num * n - (minusEpsilon ? 1 : 0)) / den;
        }
    }

    /**
     * Represents (a + b√c) / d for c not a perfect square.
     */
    @Data
    public static class QuadraticRootContinuedFraction implements ContinuedFraction {

        final long a, b, c, d;

        @Override
        public boolean isZero() {
            return a == 0 & b == 0;
        }

        @Override
        public long integerPart() {
            return (long) ((a + b * Math.sqrt(c)) / d);
        }

        @Override
        public ContinuedFraction fractionalPart() {
            return new QuadraticRootContinuedFraction(a - d * integerPart(), b, c, d);
        }

        @Override
        public ContinuedFraction inverse() {
            long new_a = a * d, new_b = -b * d, new_d = sq(a) - sq(b) * c;
            long g = gcd(gcd(new_a, new_b), new_d);
            if (Math.signum(g) != Math.signum(new_d))
                g *= -1;
            new_a /= g;
            new_b /= g;
            new_d /= g;
            return new QuadraticRootContinuedFraction(new_a, new_b, c, new_d);
        }

        @Override
        public long multiplyAndFloor(long n, boolean minusEpsilon) {
            return (long) ((a + b * Math.sqrt(c)) / d * n - (minusEpsilon && b == 0 ? 1 : 0));
        }
    }

    @Data
    private static class Key {

        final long n;
        final ContinuedFraction f;
        final int k;
        final int j;
        final boolean minusEpsilon;
    }
}
