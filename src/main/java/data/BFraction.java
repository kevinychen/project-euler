
package data;

import java.math.BigInteger;

import lombok.Data;

@Data
public final class BFraction implements Comparable<BFraction> {

    public final BigInteger num, den;

    private BFraction(BigInteger num, BigInteger den) {
        this.num = num;
        this.den = den;
    }

    public static BFraction reduced(long num, long den) {
        return reduced(BigInteger.valueOf(num), BigInteger.valueOf(den));
    }

    public static BFraction reduced(BigInteger num, BigInteger den) {
        BigInteger gcd = num.gcd(den);
        if (gcd.signum() == 0)
            gcd = BigInteger.ONE;
        else if (den.divide(gcd).signum() < 0)
            gcd = gcd.negate();
        return new BFraction(num.divide(gcd), den.divide(gcd));
    }

    public static BFraction integer(long num) {
        return integer(BigInteger.valueOf(num));
    }

    public static BFraction integer(BigInteger num) {
        return new BFraction(num, BigInteger.ONE);
    }

    public BFraction add(BFraction other) {
        return BFraction.reduced(num.multiply(other.den).add(den.multiply(other.num)), den.multiply(other.den));
    }

    public BFraction negate() {
        return new BFraction(num.negate(), den);
    }

    public BFraction subtract(BFraction other) {
        return add(other.negate());
    }

    public BFraction multiply(BFraction other) {
        return BFraction.reduced(num.multiply(other.num), den.multiply(other.den));
    }

    public BFraction reciprocal() {
        return new BFraction(den, num);
    }

    public BFraction divide(BFraction other) {
        return multiply(other.reciprocal());
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public int compareTo(BFraction other) {
        return num.multiply(other.den).compareTo(den.multiply(other.num));
    }
}
