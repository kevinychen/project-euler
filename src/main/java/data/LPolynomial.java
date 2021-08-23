
package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.EulerLib;
import lombok.Data;

@Data
public class LPolynomial {

    public static final LPolynomial ZERO = new LPolynomial();

    public final long[] coefficients;

    public LPolynomial(long... coefficients) {
        int newLength = coefficients.length;
        while (newLength > 0 && coefficients[newLength - 1] == 0)
            newLength--;
        if (coefficients.length != newLength)
            coefficients = Arrays.copyOf(coefficients, newLength);
        this.coefficients = coefficients;
    }

    public long evaluate(long n, long mod) {
        long evaluated = 0;
        for (int i = coefficients.length; --i >= 0;)
            evaluated = (evaluated * n + coefficients[i]) % mod;
        return evaluated;
    }

    public int degree() {
        return coefficients.length - 1;
    }

    public LPolynomial add(LPolynomial other, long mod) {
        long[] newCoefficients = new long[Math.max(coefficients.length, other.coefficients.length)];
        for (int i = 0; i < coefficients.length || i < other.coefficients.length; i++) {
            if (i < coefficients.length)
                newCoefficients[i] += coefficients[i];
            if (i < other.coefficients.length)
                newCoefficients[i] += other.coefficients[i];
            newCoefficients[i] %= mod;
        }
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial subtract(LPolynomial other, long mod) {
        return add(other.multiply(-1, mod), mod);
    }

    public LPolynomial multiply(long other, long mod) {
        long[] newCoefficients = new long[coefficients.length];
        for (int i = 0; i < coefficients.length; i++)
            newCoefficients[i] = coefficients[i] * other % mod;
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial multiply(LPolynomial other, long mod) {
        if (this.coefficients.length * other.coefficients.length <= 1000)
            return multiplyHelper(other, mod);

        if (Long.lowestOneBit(mod - 1) >= coefficients.length + other.coefficients.length && EulerLib.isProbablePrime(mod))
            return new LPolynomial(multiply(coefficients, other.coefficients, mod));

        int k = Math.max(this.coefficients.length, other.coefficients.length) / 2;

        LPolynomial a0 = new LPolynomial(Arrays.copyOf(this.coefficients, k));
        LPolynomial a1 = this.shiftDown(k);
        LPolynomial b0 = new LPolynomial(Arrays.copyOf(other.coefficients, k));
        LPolynomial b1 = other.shiftDown(k);

        LPolynomial bottom = a0.multiply(b0, mod);
        LPolynomial top = a1.multiply(b1, mod);
        LPolynomial middle = (a0.add(a1, mod)).multiply(b0.add(b1, mod), mod).subtract(top.add(bottom, mod), mod);

        return bottom.add(middle.shiftUp(k), mod).add(top.shiftUp(2 * k), mod);
    }

    private LPolynomial multiplyHelper(LPolynomial other, long mod) {
        if (other.equals(ZERO))
            return ZERO;

        long[] newCoefficients = new long[coefficients.length + other.coefficients.length - 1];
        for (int i = 0; i < coefficients.length; i++)
            for (int j = 0; j < other.coefficients.length; j++) {
                newCoefficients[i + j] += coefficients[i] * other.coefficients[j];
                newCoefficients[i + j] %= mod;
            }
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial reciprocal(long mod) {
        if (degree() == 0)
            return new LPolynomial(EulerLib.modInv(coefficients[0], mod));
        LPolynomial x = capTo(degree() / 2).reciprocal(mod);
        return x.multiply(2, mod).subtract(multiply(x, mod).multiply(x, mod), mod).capTo(degree());
    }

    public LPolynomial divide(LPolynomial other, long mod) {
        return multiply(other.reciprocal(mod), mod);
    }

    public LPolynomial[] divideAndRemainder(LPolynomial other, long mod) {
        if (degree() < other.degree())
            return new LPolynomial[] { ZERO, this };

        long[] dividend = Arrays.copyOf(coefficients, coefficients.length);
        int m = other.coefficients.length;

        List<Integer> nonzeroIndices = new ArrayList<>();
        for (int i = 0; i < m; i++)
            if (other.coefficients[i] != 0)
                nonzeroIndices.add(i);

        long[] newCoefficients = new long[coefficients.length - m + 1];
        for (int i = dividend.length - m; i >= 0; i--) {
            long q = dividend[i + m - 1] / other.coefficients[m - 1];
            if (q == 0)
                continue;
            newCoefficients[i] = q;
            if (nonzeroIndices.size() < m / 2)
                for (int j : nonzeroIndices) {
                    dividend[j + i] -= other.coefficients[j] * q;
                    dividend[j + i] %= mod;
                }
            else
                for (int j = 0; j < m; j++) {
                    dividend[j + i] -= other.coefficients[j] * q;
                    dividend[j + i] %= mod;
                }
        }
        return new LPolynomial[] { new LPolynomial(newCoefficients), new LPolynomial(dividend) };
    }

    public LPolynomial pow(long e, LPolynomial mod1, long mod2) {
        if (e == 0)
            return new LPolynomial(1);
        LPolynomial res = pow(e / 2, mod1, mod2);
        res = res.multiply(res, mod2).divideAndRemainder(mod1, mod2)[1];
        if (e % 2 != 0)
            res = res.multiply(this, mod2).divideAndRemainder(mod1, mod2)[1];
        return res;
    }

    public LPolynomial gcd(LPolynomial other, long mod) {
        if (other.equals(LPolynomial.ZERO))
            return this;
        return other.gcd(divideAndRemainder(other, mod)[1], mod);
    }

    public LPolynomial shiftUp(int shift) {
        long[] newCoefficients = new long[coefficients.length + shift];
        for (int i = 0; i < coefficients.length; i++)
            newCoefficients[i + shift] = coefficients[i];
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial shiftDown(int shift) {
        long[] newCoefficients = new long[Math.max(0, coefficients.length - shift)];
        for (int i = 0; i < newCoefficients.length; i++)
            newCoefficients[i] = coefficients[i + shift];
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial derivative(long mod) {
        long[] newCoefficients = new long[coefficients.length - 1];
        for (int i = 0; i < newCoefficients.length; i++)
            newCoefficients[i] = coefficients[i + 1] * (i + 1) % mod;
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial integral(long pmod) {
        long[] newCoefficients = new long[coefficients.length + 1];
        long[] modInvs = EulerLib.modInvs(newCoefficients.length, pmod);
        for (int i = 1; i < newCoefficients.length; i++)
            newCoefficients[i] = coefficients[i - 1] * modInvs[i] % pmod;
        return new LPolynomial(newCoefficients);
    }

    public LPolynomial capTo(int maxDegree) {
        return new LPolynomial(Arrays.copyOf(coefficients, maxDegree + 1));
    }

    /**
     * Multiply the coefficient arrays using discrete FFT in O(n log n) time. Based on
     * https://cp-algorithms.com/algebra/fft.html.
     */
    private static long[] multiply(long[] a, long[] b, long pmod) {
        int n = 1;
        while (n < a.length + b.length)
            n <<= 1;
        long[] fa = new long[n];
        long[] fb = new long[n];
        for (int i = 0; i < a.length; i++)
            fa[i] = a[i];
        for (int i = 0; i < b.length; i++)
            fb[i] = b[i];

        fft(fa, false, pmod);
        fft(fb, false, pmod);
        for (int i = 0; i < n; i++) {
            fa[i] *= fb[i];
            fa[i] %= pmod;
        }
        fft(fa, true, pmod);
        return fa;
    }

    private static void fft(long[] a, boolean invert, long pmod) {
        int e = Long.numberOfTrailingZeros(pmod - 1);
        long root = EulerLib.pow(EulerLib.generator(pmod), pmod >> e, pmod);
        long root_1 = EulerLib.modInv(root, pmod);
        long root_pw = 1 << e;

        int n = a.length;

        for (int i = 1, j = 0; i < n; i++) {
            int bit = n >> 1;
            for (; (j & bit) > 0; bit >>= 1)
                j ^= bit;
            j ^= bit;

            if (i < j) {
                long temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        for (int len = 2; len <= n; len <<= 1) {
            long wlen = invert ? root_1 : root;
            for (int i = len; i < root_pw; i <<= 1)
                wlen = wlen * wlen % pmod;

            for (int i = 0; i < n; i += len) {
                long w = 1;
                for (int j = 0; j < len / 2; j++) {
                    long u = a[i + j], v = a[i + j + len / 2] * w % pmod;
                    a[i + j] = u + v < pmod ? u + v : u + v - pmod;
                    a[i + j + len / 2] = u - v >= 0 ? u - v : u - v + pmod;
                    w = w * wlen % pmod;
                }
            }
        }

        if (invert) {
            long n_1 = EulerLib.modInv(n, pmod);
            for (int i = 0; i < n; i++)
                a[i] = a[i] * n_1 % pmod;
        }
    }
}
