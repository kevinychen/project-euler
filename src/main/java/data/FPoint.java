
package data;

import lombok.Data;

@Data
public final class FPoint {

    public final double x, y;

    public FPoint add(FPoint other) {
        return new FPoint(x + other.x, y + other.y);
    }

    public FPoint subtract(FPoint other) {
        return new FPoint(x - other.x, y - other.y);
    }

    public FPoint multiply(double scalar) {
        return new FPoint(scalar * x, scalar * y);
    }

    public FPoint complexMultiply(FPoint other) {
        return new FPoint(x * other.x - y * other.y, x * other.y + y * other.x);
    }

    public double dot(FPoint other) {
        return x * other.x + y * other.y;
    }

    public double cross(FPoint other) {
        return x * other.y - y * other.x;
    }

    public double normSquared() {
        return x * x + y * y;
    }

    public double dist(FPoint other) {
        return Math.sqrt(subtract(other).normSquared());
    }
}
