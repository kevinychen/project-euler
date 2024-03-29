
package level03;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p051 extends EulerTest {

    final int N = 8;
    final int B = 10;

    /**
     * Find the smallest prime in a N prime value family, which is a set of primes with a subset of
     * digits replaced by N different digits d, e.g. 13, 23, 43, 53, 73, 83 is a 6 prime value
     * family.
     * <p>
     * As an optimization, the size of the subset of digits must be divisible by 3, because
     * otherwise at least 3 numbers will not be prime.
     */
    @Test
    public void test() {
        int numDigits = 1;
        ans = Long.MAX_VALUE;
        while (ans == Long.MAX_VALUE) {
            preff(ipow(B, numDigits));
            helper("", 0, numDigits);
            numDigits++;
        }
        check(121313);
    }

    void helper(String numWithStars, int numStars, int numDigits) {
        if (numWithStars.length() == numDigits) {
            if (numStars != 3)
                return;
            List<Integer> family = list();
            for (int i : range(B)) {
                if (i == 0 && numWithStars.startsWith("*"))
                    continue;
                int num = Integer.parseInt(numWithStars.replace('*', (char) (i + '0')), B);
                if (isPrime(num))
                    family.add(num);
            }
            if (family.size() >= N && family.get(0) < ans)
                ans = family.get(0);
        } else {
            helper(numWithStars + "*", numStars + 1, numDigits);
            for (int i : range(B))
                helper(numWithStars + i, numStars, numDigits);
        }
    }
}
