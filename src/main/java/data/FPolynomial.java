
package data;

import lombok.Data;

@Data
public class FPolynomial {

    public final double[] coefficients;

    public FPolynomial(double... coefficients) {
        this.coefficients = coefficients;
    }

    public double evaluate(double n) {
        double evaluated = 0;
        for (int i = coefficients.length; --i >= 0;)
            evaluated = evaluated * n + coefficients[i];
        return evaluated;
    }

    public FPolynomial add(FPolynomial other) {
        double[] newCoefficients = new double[Math.max(coefficients.length, other.coefficients.length)];
        for (int i = 0; i < coefficients.length || i < other.coefficients.length; i++) {
            if (i < coefficients.length)
                newCoefficients[i] += coefficients[i];
            if (i < other.coefficients.length)
                newCoefficients[i] += other.coefficients[i];
        }
        return new FPolynomial(newCoefficients);
    }

    public FPolynomial subtract(FPolynomial other) {
        return add(other.multiply(-1));
    }

    public FPolynomial multiply(double other) {
        double[] newCoefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++)
            newCoefficients[i] = coefficients[i] * other;
        return new FPolynomial(newCoefficients);
    }

    public FPolynomial multiply(FPolynomial other) {
        double[] newCoefficients = new double[coefficients.length + other.coefficients.length - 1];
        for (int i = 0; i < coefficients.length; i++)
            for (int j = 0; j < other.coefficients.length; j++)
                newCoefficients[i + j] += coefficients[i] * other.coefficients[j];
        return new FPolynomial(newCoefficients);
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

    public FPolynomial shiftUp(int shift) {
        double[] newCoefficients = new double[coefficients.length + shift];
        for (int i = 0; i < coefficients.length; i++)
            newCoefficients[i + shift] = coefficients[i];
        return new FPolynomial(newCoefficients);
    }

    public FPolynomial derivative() {
        double[] newCoefficients = new double[coefficients.length - 1];
        for (int i = 0; i < newCoefficients.length; i++)
            newCoefficients[i] = coefficients[i + 1] * (i + 1);
        return new FPolynomial(newCoefficients);
    }
}
