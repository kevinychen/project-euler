
package data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class FPolynomial {

    public final double[] coefficients;

    public FPolynomial(double... coefficients) {
        this.coefficients = coefficients;
    }

    public double evaluate(long n) {
        double evaluated = 0;
        for (int i = coefficients.length; --i >= 0;)
            evaluated = evaluated * n + coefficients[i];
        return evaluated;
    }

    /**
     * Returns this polynomial divided by x-n.
     */
    public FPolynomial divideBinomial(double n) {
        double[] newCoefficients = new double[coefficients.length - 1];
        double carry = 0;
        for (int i = newCoefficients.length - 1; i >= 0; i--) {
            newCoefficients[i] = coefficients[i + 1] + carry;
            carry = newCoefficients[i] * n;
        }
        return new FPolynomial(newCoefficients);
    }
}
