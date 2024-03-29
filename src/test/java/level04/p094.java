
package level04;

import org.junit.Test;

import data.LTriangle;
import lib.EulerTest;
import lib.Pell;

public class p094 extends EulerTest {

    final int N = ipow(10, 9);

    /**
     * Find the sum of the perimeters of all almost equilateral triangles (two sides are equal and
     * the third differs by no more than 1) with integral side lengths, integral area, and perimeter
     * not exceeding N.
     * <p>
     * Suppose BC is the different side of almost equilateral triangle ABC. Since ABC has integral
     * area, the height AM to side BC must be integral, and consequently BM must be integral as
     * well. Let a=BM and b=AM. We have
     * <li>a² + b² = (2a±1)².
     * <li>=> (3a±2)² - 3b² = 1.
     */
    @Test
    public void test() {
        Pell.standard(3).generateUntil(sol -> {
            long x = sol.x.longValue();
            long a = x % 3 == 2 ? (x - 2) / 3 : (x + 2) / 3;
            LTriangle tr = x % 3 == 2
                ? new LTriangle(2 * a, 2 * a - 1, 2 * a - 1)
                : new LTriangle(2 * a, 2 * a + 1, 2 * a + 1);
            if (tr.perim() > N)
                return true;
            if (tr.a > 0 && tr.b > 0 && tr.c > 0)
                ans += tr.perim();
            return false;
        });
        check(518408346);
    }
}
