
package level02;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p043 extends EulerTest {

    final int B = 10;
    final int K = 3;
    final List<Integer> PRIMES = getPrimes(B - K);

    /**
     * Find the number of pandigital numbers [D1][D2]...[D10] such that the K-substrings are all
     * divisible by a corresponding prime, i.e.
     * <li>[D2][D3][D4] is divisible by 2.
     * <li>[D3][D4][D5] is divisible by 3. <br>
     * <li>...
     */
    @Test
    public void test() {
        permutations(range(B)).generate(permutation -> {
            if (hasSubstringDivisibilityProperty(permutation))
                ans += Long.parseLong(join(permutation));
        });
        check(16695334890L);
    }

    boolean hasSubstringDivisibilityProperty(List<Integer> permutation) {
        for (int i = 0; i < B - K; i++) {
            int substring = 0;
            for (int j = 0; j < K; j++)
                substring += pow(B, K - 1 - j) * permutation.get(i + 1 + j);
            if (substring % PRIMES.get(i) != 0)
                return false;
        }
        return true;
    }
}
