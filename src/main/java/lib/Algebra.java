package lib;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import data.LPolynomial;

public class Algebra extends EulerLib {

    /**
     * Assuming that S(n) is a polynomial (mod m) with the given degree k, returns a function that
     * can evaluate S(n) in O(k²). Runs in time O(k²) and requires fewer terms than
     * {@link #extrapolation}.
     */
    public static Function<Long, Long> polynomialExtrapolation(Function<Integer, Long> S, int k, long m) {
        long[][] diffs = new long[k + 1][k + 1];
        for (int j = 0; j <= k; j++)
            diffs[0][j] = S.apply(j);
        for (int i = 1; i <= k; i++)
            for (int j = 0; j <= k - i; j++)
                diffs[i][j] = diffs[i - 1][j + 1] - diffs[i - 1][j];
        return index -> {
            long res = 0;
            for (int i = 0; i <= k; i++) {
                long nCr = 1;
                long den = factorial(i);
                for (int j = 0; j < i; j++) {
                    long num = index - j;
                    long g = gcd(num, den);
                    nCr *= num / g;
                    nCr %= m;
                    den /= g;
                }
                res += nCr * diffs[i][0];
                res %= m;
            }
            return res;
        };
    }

    /**
     * Assuming that S(n) satisfies a linear recurrence (mod m) with order k ≥ minOrder, returns a
     * function that can evaluate S(n) in O(k² log(n)). Runs in time O(k²).
     */
    public static Function<Long, Long> extrapolation(Function<Integer, Long> S, int minOrder, long m) {
        Map<Long, Integer> primeFactors = lprimeFactor(m);
        Map<Long, LPolynomial> recurrences = map();
        Map<Integer, Long> cache = map();
        primeFactors.forEach((p, e) -> recurrences.put(p, recurrence(n -> cache.computeIfAbsent(n, S), minOrder, p, e)));
        int k = recurrences.values().stream().mapToInt(LPolynomial::degree).max().getAsInt();
        long[] A = new long[k + 1];
        A[k] = 1;
        for (int i : range(k)) {
            List<Long> as = list();
            List<Long> ms = list();
            primeFactors.forEach((p, e) -> {
                LPolynomial recurrence = recurrences.get(p);
                as.add(k - i <= recurrence.degree() ? -recurrence.coefficients[k - i] : 0);
                ms.add(pow(p, e));
            });
            A[i] = -crt(as, ms);
        }
        return index -> {
            if (index < cache.size() - k)
                return S.apply(index.intValue());
            long[] coeffs = new LPolynomial(0, 1).pow(index - (cache.size() - k), new LPolynomial(A), m).coefficients;
            long res = 0;
            for (int i = 0; i < coeffs.length; i++) {
                res += coeffs[i] * cache.get(i + cache.size() - k);
                res %= m;
            }
            return res;
        };
    }

    /**
     * Returns a polynomial a(x) such that S(x) a(x) (mod p^e) is a polynomial with degree smaller
     * than that of a(x), and a_0 = 1. In other words, S satisfies the linear recurrence
     *
     * <pre>
     * S_n ≡ a_1 S_{n-1} + a_2 S_{n-2} + ... + a_k S_{n-k} (mod p^e).
     * </pre>
     *
     * This is implemented using the Reeds Sloane algorithm and runs in time O(k²). See the paper
     * "Shift-Register Synthesis (Modulo m)" by Reeds and Sloane, 1985
     * http://citeseerx.ist.psu.edu/viewdoc/download;jsessionid=E93C0581CA1D6F845B51BA8CD47433F2?doi=10.1.1.48.4652&rep=rep1&type=pdf.
     */
    private static LPolynomial recurrence(Function<Integer, Long> S, int minOrder, long p, int e) {
        long pe = pow(p, e);
        LPolynomial[] a = new LPolynomial[e];
        LPolynomial[] b = new LPolynomial[e];
        LPolynomial[] a_new = new LPolynomial[e];
        LPolynomial[] b_new = new LPolynomial[e];
        long[] theta = new long[e];
        int[] u = new int[e];
        for (int eta = 0; eta < e; eta++) {
            a[eta] = new LPolynomial(pow(p, eta));
            b[eta] = LPolynomial.ZERO;
            a_new[eta] = new LPolynomial(pow(p, eta));
            b_new[eta] = new LPolynomial(pow(p, eta) * S.apply(0) % pe);
            theta[eta] = S.apply(0) * pow(p, eta);
            while (u[eta] < e && theta[eta] % p == 0) {
                theta[eta] /= p;
                u[eta]++;
            }
        }
        LPolynomial[] a_old = new LPolynomial[e];
        LPolynomial[] b_old = new LPolynomial[e];
        for (int eta = 0; eta < e; eta++) {
            a_old[eta] = LPolynomial.ZERO;
            b_old[eta] = LPolynomial.ZERO;
        }
        long[] theta_old = new long[e];
        int[] u_old = new int[e];
        int[] r = new int[e];
        for (int k = 1; k <= 2 * minOrder || k <= 2 * a_new[0].degree(); k++) {
            for (int g = 0; g < e; g++)
                if (L(a_new[g], b_new[g]) > L(a[g], b[g])) {
                    int h = e - 1 - u[g];
                    if (h == -1)
                        continue;
                    a_old[g] = a[h];
                    b_old[g] = b[h];
                    theta_old[g] = theta[h];
                    u_old[g] = u[h];
                    r[g] = k - 1;
                }
            for (int eta = 0; eta < e; eta++) {
                a[eta] = a_new[eta];
                b[eta] = b_new[eta];
            }
            for (int eta = 0; eta < e; eta++) {
                theta[eta] = 0;
                u[eta] = 0;
                for (int i = 0; i <= k && i <= a[eta].degree(); i++) {
                    theta[eta] += S.apply(k - i) * a[eta].coefficients[i];
                    theta[eta] %= pe;
                }
                if (k <= b[eta].degree())
                    theta[eta] -= b[eta].coefficients[k];
                while (u[eta] < e && theta[eta] % p == 0) {
                    theta[eta] /= p;
                    u[eta]++;
                }
                int g = e - 1 - u[eta];
                if (u[eta] == e) {
                    a_new[eta] = a[eta];
                    b_new[eta] = b[eta];
                } else if (L(a[g], b[g]) == 0) {
                    a_new[eta] = a[eta];
                    b_new[eta] = b[eta].add(new LPolynomial(theta[eta] * pow(p, u[eta] % pe)).shiftUp(k), pe);
                } else {
                    a_new[eta] = a[eta].subtract(
                        a_old[g].multiply(theta[eta] * modInv(theta_old[g], pe) % pe * pow(p, u[eta] - u_old[g]) % pe, pe).shiftUp(k - r[g]), pe);
                    b_new[eta] = b[eta].subtract(
                        b_old[g].multiply(theta[eta] * modInv(theta_old[g], pe) % pe * pow(p, u[eta] - u_old[g]) % pe, pe).shiftUp(k - r[g]), pe);
                }
            }
        }
        return a_new[0];
    }

    private static int L(LPolynomial a, LPolynomial b) {
        return Math.max(a.degree(), 1 + b.degree());
    }
}
