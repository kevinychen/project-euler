
package level03;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Multimap;

import lib.EulerTest;

public class p062 extends EulerTest {

    final int N = 5;

    /**
     * Find the smallest cube such that exactly N permutations of its digits are also cube.
     */
    @Test
    public void test() {
        Multimap<List<?>, Long> cubes = mmap();
        for (int i = 1; true; i++) {
            long cube = cb(i);
            List<Integer> digits = digits(cube);
            Collections.sort(digits);
            cubes.put(digits, cube);
            Collection<Long> permutations = cubes.get(digits);
            if (permutations.size() == N) {
                ans = Collections.min(permutations);
                break;
            }
        }
        check(127035954683L);
    }
}
