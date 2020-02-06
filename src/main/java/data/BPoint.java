
package data;

import java.math.BigInteger;

import lombok.Data;

@Data
public final class BPoint {

    public final BigInteger x, y;

    public static BPoint fromPoint(LPoint point) {
        return new BPoint(BigInteger.valueOf(point.x), BigInteger.valueOf(point.y));
    }

    public long x() {
        return x.longValue();
    }

    public long y() {
        return y.longValue();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
