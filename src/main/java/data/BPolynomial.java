package data;

import java.math.BigInteger;
import java.util.Arrays;

import lombok.Data;

@Data
public class BPolynomial {

    public final BigInteger[] coefficients;

    public BPolynomial(BigInteger... coefficients) {
        this.coefficients = coefficients;
    }

    public BigInteger evaluate(BigInteger n) {
        BigInteger evaluated = BigInteger.ZERO;
        for (int i = coefficients.length; --i >= 0;)
            evaluated = evaluated.multiply(n).add(coefficients[i]);
        return evaluated;
    }

    public BPolynomial add(BPolynomial other) {
        BigInteger[] newCoefficients = new BigInteger[Math.max(coefficients.length, other.coefficients.length)];
        Arrays.fill(newCoefficients, BigInteger.ZERO);
        for (int i = 0; i < coefficients.length || i < other.coefficients.length; i++) {
            if (i < coefficients.length)
                newCoefficients[i] = newCoefficients[i].add(coefficients[i]);
            if (i < other.coefficients.length)
                newCoefficients[i] = newCoefficients[i].add(other.coefficients[i]);
        }
        return new BPolynomial(newCoefficients);
    }

    public BPolynomial multiply(BPolynomial other) {
        BigInteger[] newCoefficients = new BigInteger[coefficients.length + other.coefficients.length - 1];
        Arrays.fill(newCoefficients, BigInteger.ZERO);
        for (int i = 0; i < coefficients.length; i++)
            for (int j = 0; j < other.coefficients.length; j++)
                newCoefficients[i + j] = newCoefficients[i + j].add(coefficients[i].multiply(other.coefficients[j]));
        return new BPolynomial(newCoefficients);
    }
}
