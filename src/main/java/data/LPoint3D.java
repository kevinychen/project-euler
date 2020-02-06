
package data;

import lombok.Data;

@Data
public final class LPoint3D {

    public final long x, y, z;

    public LPoint3D reflectX() {
        return new LPoint3D(-x, y, z);
    }

    public LPoint3D reflectY() {
        return new LPoint3D(x, -y, z);
    }

    public LPoint3D reflectZ() {
        return new LPoint3D(x, y, -z);
    }

    public long dot(LPoint3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public long cross(LPoint3D other1, LPoint3D other2) {
        return (x * other1.y * other2.z + y * other1.z * other2.x + z * other1.x * other2.y)
                - (x * other1.z * other2.y + y * other1.x * other2.z + z * other1.y * other2.x);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
