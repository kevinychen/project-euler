
package data;

import lib.EulerLib;
import lombok.Data;

@Data
public final class LFraction implements Comparable<LFraction> {

    public final long num, den;

    private LFraction(long num, long den) {
        this.num = num;
        this.den = den;
    }

    public static LFraction reduced(long num, long den) {
        long gcd = EulerLib.gcd(num, den);
        if (gcd == 0)
            gcd = 1;
        else if (den / gcd < 0)
            gcd *= -1;
        return new LFraction(num / gcd, den / gcd);
    }

    public static LFraction integer(long num) {
        return new LFraction(num, 1);
    }

    public LFraction add(LFraction other) {
        if (den == 0 || other.den == 0)
            return new LFraction(1, 0);
        long l = EulerLib.lcm(den, other.den);
        return LFraction.reduced(l / den * num + l / other.den * other.num, l);
    }

    public LFraction negate() {
        return new LFraction(-num, den);
    }

    public LFraction subtract(LFraction other) {
        return add(other.negate());
    }

    public LFraction multiply(LFraction other) {
        return LFraction.reduced(num * other.num, den * other.den);
    }

    public LFraction reciprocal() {
        if (den < 0)
            return new LFraction(-den, -num);
        else
            return new LFraction(den, num);
    }

    public LFraction divide(LFraction other) {
        return multiply(other.reciprocal());
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public int compareTo(LFraction other) {
        return Long.compare(num * other.den, den * other.num);
    }
}
