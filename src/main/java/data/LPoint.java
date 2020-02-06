
package data;

import lombok.Data;

@Data
public final class LPoint {

    public final long x, y;

    public LPoint add(LPoint other) {
        return new LPoint(x + other.x, y + other.y);
    }

    public LPoint subtract(LPoint other) {
        return new LPoint(x - other.x, y - other.y);
    }

    public LPoint negate() {
        return new LPoint(-x, -y);
    }

    public LPoint reflectX() {
        return new LPoint(-x, y);
    }

    public LPoint reflectY() {
        return new LPoint(x, -y);
    }

    public LPoint multiply(long scale) {
        return new LPoint(x * scale, y * scale);
    }

    public LPoint complexMultiply(LPoint other) {
        return new LPoint(x * other.x - y * other.y, x * other.y + y * other.x);
    }

    public LPoint divide(long scale) {
        return new LPoint(x / scale, y / scale);
    }

    public long dot(LPoint other) {
        return x * other.x + y * other.y;
    }

    public long cross(LPoint other) {
        return x * other.y - y * other.x;
    }

    public double angleTo(LPoint other) {
        return Math.atan2(other.y - y, other.x - x);
    }

    public double distanceTo(LPoint other) {
        return Math.hypot(other.x - x, other.y - y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
