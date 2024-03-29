
package level03;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Test;

import com.google.common.collect.Multimap;

import lib.EulerTest;

public class p061 extends EulerTest {

    final int K = 2;
    final int B = 10;
    final int[] SIDES = { 3, 4, 5, 6, 7, 8 };
    final int C = ipow(B, K);

    Multimap<List<?>, Long> allFigurates = mmap();

    /**
     * Find cyclic figurate 2K-digit numbers, one triangular, one square, etc., as specified by
     * SIDES,, such that each number shares its first K digits with the last K digits of its
     * predecessor; e.g. 8128, 2882, 8281 is a set of 3 cyclic figurate numbers.
     */
    @Test
    public void test() {
        for (int numSides : SIDES) {
            long figurate;
            for (int j = 1; (figurate = figurate(j, numSides)) < sq(C); j++) {
                long startDigits = figurate / C;
                if (startDigits >= C / B)
                    allFigurates.put(list(numSides, startDigits), figurate);
            }
        }
        helper(list(), set(), 0);
        check(28684);
    }

    void helper(List<Long> figurates, Set<Integer> usedSides, long sum) {
        if (figurates.size() == SIDES.length) {
            if (last(figurates) % C == figurates.get(0) / C)
                ans = sum;
            return;
        }
        for (int numSides : SIDES)
            if (!usedSides.contains(numSides)) {
                usedSides.add(numSides);
                Consumer<Long> recurse = startDigits -> {
                    for (long figurate : allFigurates.get(list(numSides, startDigits))) {
                        figurates.add(figurate);
                        helper(figurates, usedSides, sum + figurate);
                        removeLast(figurates);
                    }
                };
                if (figurates.isEmpty())
                    for (long startDigits : range(C))
                        recurse.accept(startDigits);
                else
                    recurse.accept(last(figurates) % C);
                usedSides.remove(numSides);
            }
    }
}
