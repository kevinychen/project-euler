
package level02;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p040 extends EulerTest {

    final List<Integer> INDICES = list(1, 10, 100, 1000, 10000, 100000, 1000000);

    /**
     * Find prod(d_{INDICES[i]}) where d_i is the ith digit in:
     * <p>
     * 0.123456789101112131415161718192021...
     */
    @Test
    public void test() {
        StringBuilder sb = sb();
        for (int index = 1; sb.length() < last(INDICES); index++)
            sb.append(index);
        ans = 1;
        for (int index : INDICES)
            ans *= sb.charAt(index - 1) - '0';
        check(210);
    }
}
