
package data;

import lombok.Data;

@Data
public final class IPoint {

    public final int x, y;

    public long norm2() {
        return x * x + y * y;
    }
}
