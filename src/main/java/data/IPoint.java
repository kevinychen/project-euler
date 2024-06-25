
package data;

import lombok.Data;

@Data
public final class IPoint {

    public final int x, y;

    public IPoint add(IPoint other) {
        return new IPoint(x + other.x, y + other.y);
    }

    public IPoint subtract(IPoint other) {
        return new IPoint(x - other.x, y - other.y);
    }

    public long norm2() {
        return x * x + y * y;
    }
}
