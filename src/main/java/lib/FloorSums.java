
package lib;

import java.util.HashMap;
import java.util.Map;

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
        return cache.computeIfAbsent(new Key(n, f, k, j, minusEpsilon), key -> {
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
