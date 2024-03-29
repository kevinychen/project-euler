
package level01;

import java.util.Arrays;

import org.junit.Test;

import lib.EulerTest;

public class p022 extends EulerTest {

    /**
     * Compute rank(name) * value(name) for the specified list of names, where:
     * <li>rank(name) is the index of the name when the list is alphabetized, starting with 1
     * <li>value(name) is the sum of all letters in the name (A=1, B=2, ... Z=26).
     */
    @Test
    public void test() {
        String[] names = readAsCommaSeparatedStrings();
        Arrays.sort(names);
        for (int rank : range(names.length))
            ans += (rank + 1) * wordValue(names[rank]);
        check(871198282);
    }
}
