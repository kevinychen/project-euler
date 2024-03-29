
package level04;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p090 extends EulerTest {

    final int N = 6;
    final int B = 10;

    /**
     * Find the number of arrangements of two N-sided cubes with the numbers from 0 to B-1 such that
     * all two-digit perfect squares can be displayed, where 6 and 9 are interchangeable.
     */
    @Test
    public void test() {
        combinations(range(B), N).generate(cube1 -> {
            combinations(range(B), N).generateUntil(cube2 -> {
                if (good(cube1, cube2))
                    ans++;
                return cube1.equals(cube2);
            });
        });
        check(1217);
    }

    boolean good(List<Integer> cube1, List<Integer> cube2) {
        Set<Integer> possibleDigits1 = getPossibleDigits(cube1);
        Set<Integer> possibleDigits2 = getPossibleDigits(cube2);
        for (int i : range(1, B)) {
            int digit1 = isq(i) / B, digit2 = isq(i) % B;
            if (possibleDigits1.contains(digit1) && possibleDigits2.contains(digit2))
                continue;
            if (possibleDigits1.contains(digit2) && possibleDigits2.contains(digit1))
                continue;
            return false;
        }
        return true;
    }

    Set<Integer> getPossibleDigits(List<Integer> cube) {
        Set<Integer> possibleDigits = set(cube);
        if (possibleDigits.contains(6))
            possibleDigits.add(9);
        else if (possibleDigits.contains(9))
            possibleDigits.add(6);
        return possibleDigits;
    }
}
