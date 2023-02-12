
package level01;

import java.util.Map;

import org.junit.Test;

import lib.EulerTest;

public class p014 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the natural number under N that generates the longest Collatz chain.
     */
    @Test
    public void test() {
        int maxChainLen = 0;
        Map<Long, Integer> cache = map(1L, 1);
        for (long i : range(1, N)) {
            long n = i;
            int chainLen = 0;
            while (!cache.containsKey(n)) {
                chainLen++;
                n = n % 2 == 0 ? n / 2 : 3 * n + 1;
            }
            chainLen += cache.get(n);
            cache.put(i, chainLen);
            if (chainLen > maxChainLen) {
                maxChainLen = chainLen;
                ans = i;
            }
        }
        check(837799);
    }
}
