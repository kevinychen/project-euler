
package data;

import java.util.Arrays;

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

    public LPolynomial[] divideAndRemainder(LPolynomial other, long mod) {
        if (degree() < other.degree())
            return new LPolynomial[] { ZERO, this };

        long[] dividend = Arrays.copyOf(coefficients, coefficients.length);
        int m = other.coefficients.length;
        long[] newCoefficients = new long[coefficients.length - m + 1];
        for (int i = dividend.length - m; i >= 0; i--) {
            long q = dividend[i + m - 1] / other.coefficients[m - 1];
            if (q == 0)
                continue;
            newCoefficients[i] = q;
            for (int j = 0; j < m; j++) {
                dividend[j + i] -= other.coefficients[j] * q;
                dividend[j + i] %= mod;
            }
        }
        return new LPolynomial[] { new LPolynomial(newCoefficients), new LPolynomial(dividend) };
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
}
