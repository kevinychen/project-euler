
package data;

import lombok.Data;

@Data
public final class LTriangle {

    public final long a, b, c;

    public long perim() {
        return a + b + c;
    }
}
