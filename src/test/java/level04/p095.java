
package level04;

import java.util.Map;

import org.junit.Test;

import lib.EulerTest;

public class p095 extends EulerTest {

    final int N = 1000000;

    /**
     * Find the smallest number in the longest amicable chain with no numbers exceeding N.
     */
    @Test
    public void test() {
        preSumDivisors(N);
        int maxChainLen = 0, maxChainMin = 0;
        Map<Integer, Integer> seen = map();
        for (int i : rangeC(N)) {
            int n = i;
            while (n <= N && !seen.containsKey(n)) {
                seen.put(n, i);
                n = sumProperDivisors(n);
            }
            if (n > N || seen.get(n) != i)
                continue;
            int chainStart = n, chainLen = 0, chainMin = Integer.MAX_VALUE;
            do {
                chainLen++;
                if (n < chainMin)
                    chainMin = n;
                n = sumProperDivisors(n);
            } while (n != chainStart);
            if (chainLen > maxChainLen || chainLen == maxChainLen && chainMin < maxChainMin) {
                maxChainLen = chainLen;
                maxChainMin = chainMin;
            }
        }
        ans = maxChainMin;
        check(14316);
    }
}
