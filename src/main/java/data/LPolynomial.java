
package data;

import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LPolynomial {
    public final long[] coefficients;

    public long evaluate(long n, long mod) {
        long evaluated = 0;
        for (int i = coefficients.length; --i >= 0;)
            evaluated = (evaluated * n + coefficients[i]) % mod;
        return evaluated;
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
        if (this.coefficients.length * other.coefficients.length <= 10000)
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
        long[] newCoefficients = new long[coefficients.length + other.coefficients.length - 1];
        for (int i = 0; i < coefficients.length; i++)
            for (int j = 0; j < other.coefficients.length; j++) {
                newCoefficients[i + j] += coefficients[i] * other.coefficients[j];
                newCoefficients[i + j] %= mod;
            }
        return new LPolynomial(newCoefficients);
    }

    private LPolynomial shiftUp(int shift) {
        long[] newCoefficients = new long[coefficients.length + shift];
        for (int i = 0; i < coefficients.length; i++)
            newCoefficients[i + shift] = coefficients[i];
        return new LPolynomial(newCoefficients);
    }

    private LPolynomial shiftDown(int shift) {
        long[] newCoefficients = new long[Math.max(0, coefficients.length - shift)];
        for (int i = 0; i < newCoefficients.length; i++)
            newCoefficients[i] = coefficients[i + shift];
        return new LPolynomial(newCoefficients);
    }
}
