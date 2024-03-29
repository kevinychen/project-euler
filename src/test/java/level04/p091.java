
package level04;

import java.util.List;

import org.junit.Test;

import data.LPoint;
import lib.EulerTest;

public class p091 extends EulerTest {

    final int N = 50;

    /**
     * Find the number of right triangles with one vertex at the origin and integer coordinates
     * between 0 and N inclusive.
     */
    @Test
    public void test() {
        List<LPoint> points = list();
        for (int x1 : rangeC(N))
            for (int y1 : rangeC(N))
                points.add(new LPoint(x1, y1));
        for (int i : range(points.size())) {
            LPoint P = points.get(i);
            for (int j : range(i + 1, points.size())) {
                LPoint Q = points.get(j);
                if (P.cross(Q) != 0 && (P.dot(Q) == 0 || P.dot(P.subtract(Q)) == 0 || Q.dot(Q.subtract(P)) == 0))
                    ans++;
            }
        }
        check(14234);
    }
}
