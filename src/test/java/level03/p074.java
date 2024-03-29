
package level03;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import lib.EulerTest;

public class p074 extends EulerTest {

    final int N = 1000000;
    final int K = 60;

    /**
     * Find the number of factorial chains (where each number is the sum of the factorials of the
     * digits of the previous number) that start with a number < N and contain exactly K
     * non-repeating terms.
     */
    @Test
    public void test() {
        Map<Integer, Integer> factorialChainLen = map();
        for (int i : range(N)) {
            int n = i;
            Set<Integer> chain = set();
            while (!chain.contains(n) && !factorialChainLen.containsKey(n)) {
                chain.add(n);
                int next = 0;
                for (int j : digits(n))
                    next += factorial(j);
                n = next;
            }
            int chainLen = chain.size() + factorialChainLen.getOrDefault(n, 0);
            factorialChainLen.put(i, chainLen);
            if (chainLen == K)
                ans++;
        }
        check(402);
    }
}
