
package lib;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import data.LPoint;
import data.LPoint3D;
import data.QuotientValues;

public class NumberTheory extends EulerLib {

    /**
     * Returns an array such that A[k] is the length of the repeating part of 1/k, when expressed in
     * base b. For example, in base 10, A[7] = 6 because 1/7 = .(142857).
     * <p>
     * If GCD(n, b) = 1, then L(n) is the order of b modulo n. For n = x*y where x and y are
     * relatively prime, this is L(n) = LCM(L(x), L(y)). For n a prime power p^e, we can use the
     * relation L(n) = L(p) or p L(p), and simply check whether b^L(p) ≡ 1. Finally, if n is a
     * prime, then the order must divide n - 1. We start with n - 1 and repeatedly try dividing out
     * factors of n - 1. The final number must be the order L(n).
     */
    public static long[] decimalCycleLengths(int limit, int b) {
        preff(limit);
        int[] reduced = new int[limit + 1];
        for (int p : primeFactor(b).keySet())
            for (int i = 1; i * p <= limit; i++)
                reduced[i * p] = i;

        long[] decimalCycleLengths = new long[limit + 1];
        for (int n = 3; n <= limit; n++) {
            if (reduced[n] > 0)
                decimalCycleLengths[n] = decimalCycleLengths[reduced[n]];
            else {
                int nCopy = n, d = ff[nCopy], e = 0;
                while (nCopy % d == 0) {
                    nCopy /= d;
                    e++;
                }
                if (nCopy > 1) {
                    decimalCycleLengths[n] = lcm(decimalCycleLengths[nCopy], decimalCycleLengths[n / nCopy]);
                } else if (e > 1) {
                    decimalCycleLengths[n] = pow(b, decimalCycleLengths[n / d], n) == 1
                            ? decimalCycleLengths[n / d]
                            : d * decimalCycleLengths[n / d];
                } else {
                    int phi = n - 1;
                    decimalCycleLengths[n] = phi;
                    while (phi > 1) {
                        int dd = ff[phi];
                        while (phi % dd == 0 && pow(b, decimalCycleLengths[n] / dd, n) == 1) {
                            decimalCycleLengths[n] /= dd;
                            phi /= dd;
                        }
                        while (phi % dd == 0)
                            phi /= dd;
                    }
                }
            }
        }
        return decimalCycleLengths;
    }

    /**
     * Return the number of positive integer triplets (x, y, z) such that x*y*z ≤ n.
     * <p>
     * The number of triplets can be computed by:
     *
     * <ul>
     * <li># of triplets with x &lt; y &lt; z, each of which can be permuted in 6 different ways: 6
     * sum_{x=1}{∛n} sum_{y=x+1}^{√(n/x)} (⌊n/x²⌋ - y)</li>
     * <li># of triplets with x = y &lt; z, each with 3 permutations: 3 sum_{x=1}^{∛n} (⌊n/x²⌋ -
     * x)</li>
     * <li># of triplets with x &lt; y = z, 3 permutations each: 3 sum_{x=1}^{∛n} (⌊√(n/x)⌋ -
     * x)</li>
     * <li># of triplets with x = y = z: ⌊∛n⌋.</li>
     * </ul>
     *
     * This runs in time O(n^{2/3}).
     */
    public static long numTripletsWithProductAtMost(long n) {
        long numTriplets = 0;
        for (long x = 1; cb(x) <= n; x++)
            for (long y = x + 1; sq(y) <= n / x; y++)
                numTriplets += (n / (x * y) - y) * 6;
        for (int x = 1; cb(x) <= n; x++)
            numTriplets += (n / sq(x) - x) * 3;
        for (int x = 1; cb(x) <= n; x++)
            numTriplets += (isqrt(n / x) - x) * 3;
        numTriplets += Math.cbrt(n);
        return numTriplets;
    }

    /**
     * Computes sum_{k=1}^limit ⌊n/k⌋ in O(√n) time.
     */
    public static long sumFloorQuotients(long n, long limit) {
        if (limit <= 0)
            return 0;
        int L = isqrt(n);
        long ans = 0;
        for (int k = 1; k <= limit && k <= L; k++)
            ans += n / k;
        for (long q = n / limit; q < n / L; q++)
            ans += (Math.min(n / q, limit) - n / (q + 1)) * q;
        return ans;
    }

    /**
     * Returns sum_{p=1}^l p^exp, for all l = ⌊n/k⌋. Runs in time O(n^(3/4)).
     */
    public static QuotientValues sumPrimePowers(long n, int exp, long mod) {
        int L = isqrt(n);
        preff(L);
        long[] big = new long[L + 1];
        long[] small = new long[(int) (n / L)];
        for (int i = 1; i <= L; i++)
            big[i] = sumPowers(n / i, exp, mod) - 1;
        for (int i = 1; i < n / L; i++)
            small[i] = sumPowers(i, exp, mod) - 1;
        for (int p : primes(L)) {
            long pp = pow(p, exp, mod);
            for (int i = 1; i <= L && n / i >= sq(p); i++) {
                long ip = (long) i * p;
                big[i] -= pp * ((ip <= L ? big[(int) ip] : small[(int) (n / ip)]) - small[p - 1]);
                big[i] %= mod;
            }
            for (int i = (int) (n / L); --i >= sq(p);) {
                small[i] -= pp * (small[i / p] - small[p - 1]);
                small[i] %= mod;
            }
        }
        return new QuotientValues(n, big, small);
    }

    public static QuotientValues sumPhis(long n) {
        return NumberTheory.sumPhis(n, Long.MAX_VALUE);
    }

    public static QuotientValues sumPhis(long n, long mod) {
        return sumPhis(n, 0, mod);
    }

    /**
     * Returns P_e(l) = sum_{k=1}^l k^e ϕ(k) for all l = ⌊n/k⌋. Runs in time O(n^(2/3)). See
     * https://math.stackexchange.com/questions/316376/how-to-calculate-these-totient-summation-sums-efficiently
     * for e = 0. For e > 0, we use the identity sum_{k=1}^n P_e(⌊n/k⌋) = sum_{k=1}^n k^n.
     */
    public static QuotientValues sumPhis(long n, int e, long mod) {
        int L = (int) Math.pow(n, 1. / 3) + 1;
        int L2 = (int) (n / L);
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        prePhi(L2);
        for (int i = 1; i <= L2; i++) {
            small[i] = small[i - 1] + pow(i, e, mod) * phi[i];
            small[i] %= mod;
        }
        for (int i = L; --i >= 1;) {
            big[i] = sumPowers(n / i, e + 1, mod);
            for (int k = 2; k < isqrt(n / i); k++) {
                big[i] -= pow(k, e, mod) * (1L * i * k < L ? big[i * k] : small[(int) (n / i / k)]);
                big[i] %= mod;
            }
            for (int t = 1; t <= n / i / isqrt(n / i); t++) {
                big[i] -= (sumPowers(n / i / t, e, mod) - sumPowers(n / i / (t + 1), e, mod)) * small[t];
                big[i] %= mod;
            }
        }
        return new QuotientValues(n, big, small);
    }

    /**
     * Returns M(l) for all l = ⌊n/k⌋, where M(n) = sum_{k=1}^n μ(k) is the summatory Mobius
     * function. The algorithm runs in time O(n^{2/3}) by using the identity sum_{k=1}^n M(⌊n/k⌋) =
     * 1.
     */
    public static QuotientValues mertens(long n) {
        int L = (int) Math.pow(n, 1. / 3) + 1;
        int L2 = (int) (n / L);
        long[] big = new long[L];
        long[] small = new long[L2 + 1];
        preMobius(L2);
        for (int i = 1; i <= L2; i++)
            small[i] = small[i - 1] + mobius[i];
        for (int i = L; --i >= 1;) {
            big[i] = 1;
            for (int k = 2; k < isqrt(n / i); k++)
                big[i] -= 1L * i * k < L ? big[i * k] : small[(int) (n / i / k)];
            for (int t = 1; t <= n / i / isqrt(n / i); t++)
                big[i] -= (n / i / t - n / i / (t + 1)) * small[t];
        }
        return new QuotientValues(n, big, small);
    }

    /**
     * Given the prime factors of n (with multiplicity), returns all points x,y ≥ 0 such that x²+y²
     * = n.
     */
    public static Set<LPoint> sumsOfTwoSquares(List<Integer> unsortedPrimeFactors) {
        List<Integer> primeFactors = list(unsortedPrimeFactors);
        Collections.sort(primeFactors);
        Set<LPoint> sums = set(new LPoint(0, 1), new LPoint(1, 0));
        for (int i = 0; i < primeFactors.size(); i++) {
            int p = primeFactors.get(i);
            LPoint oneSum;
            if (p % 4 == 3) {
                if (i + 1 == primeFactors.size() || primeFactors.get(i + 1) != p)
                    return set();
                oneSum = new LPoint(0, p);
                i++;
            } else if (p == 2) {
                oneSum = new LPoint(1, 1);
            } else {
                long y = p;
                long x = sqrt(p - 1, p);
                while (sq(x) + sq(y) > p) {
                    long temp = y % x;
                    y = x;
                    x = temp;
                }
                oneSum = new LPoint(x, y);
            }

            Set<LPoint> newSums = set();
            for (LPoint sum : sums) {
                long x = sum.x * oneSum.x + sum.y * oneSum.y;
                long y = Math.abs(sum.x * oneSum.y - sum.y * oneSum.x);
                newSums.add(new LPoint(x, y));
                newSums.add(new LPoint(y, x));
            }
            sums = newSums;
        }
        return sums;
    }

    /**
     * Returns all points x,y,z ≥ 0 such that x²+y²+z² = n.
     */
    public static List<LPoint3D> sumsOfThreeSquares(int n) {
        preff(2 * n);
        List<LPoint3D> sums = list(new LPoint3D(n, 0, 0));
        for (int x = 0; x < n; x++) {
            List<Integer> primeFactors = list();
            for (int factor : list(n - x, n + x))
                primeFactor(factor).forEach((p, e) -> {
                    for (int i = 0; i < e; i++)
                        primeFactors.add(p);
                });
            for (LPoint sum : NumberTheory.sumsOfTwoSquares(primeFactors))
                sums.add(new LPoint3D(x, sum.x, sum.y));
        }
        return sums;
    }

    private NumberTheory() {
    }
}
