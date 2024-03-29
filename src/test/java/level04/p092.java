
package level04;

import org.junit.Test;

import lib.EulerTest;

public class p092 extends EulerTest {

    final int N = ipow(10, 7);
    final int L = 1000;
    final int END = 89;

    /**
     * Find the number of integers below N such that repeatedly forming a new number from the sum of
     * the squares of the digits of the previous number eventually results in END.
     * <p>
     * Let f(n) be the sum of the squares of the digits of n and call n "good" if the chain n, f(n),
     * f(f(n))... eventually contains END. For n<10^k, f(n) is at most 81k, which for the case of
     * k=7 is at most 567 < L=1000. First we compute which numbers below L are good using brute
     * force. Then, any n is good if and only if f(n) is. We optimize further by noticing that f(L*i
     * + j)=f(i)+f(j), which gives us a quick way of computing f(n) for n≥N/L.
     */
    @Test
    public void test() {
        int[] next = new int[Math.max(L, N / L)];
        for (int i : range(next.length))
            next[i] = next(i);
        boolean[] good = new boolean[L];
        for (int i : range(1, L)) {
            int n = i;
            while (n != 1) {
                if (n == END) {
                    good[i] = true;
                    break;
                }
                n = next[n];
            }
        }
        for (int i : range(N / L))
            for (int j : range(L))
                if (good[next[i] + next[j]])
                    ans++;
        check(8581146);
    }

    int next(int n) {
        int next = 0;
        for (int i : digits(n))
            next += sq(i);
        return next;
    }
}
