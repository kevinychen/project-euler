
package level04;

import java.util.Arrays;

import org.junit.Test;

import lib.EulerTest;

public class p088 extends EulerTest {

    final int N = 12000;

    /**
     * Find the sum of all minimal product numbers for 2≤k≤N. A minimal product number for k is the
     * smallest integer that can be written as the sum and the product of the same set of k
     * integers, e.g. 6 is the minimal product number for k=3 because 6=1+2+3=1*2*3.
     * <p>
     * For every factorization P=f1*f2*...*f_n, P can be written as
     * P=f1*f2*...*f_n*1*1*...+1=f1+f2+...+f_n+1+1+...+1 with (P-sum(f_i)) 1s, and is therefore a
     * product number for k=n+P-sum(f_i). Search for all such factorizations, and find for each k
     * the smallest P. For any P, k=n+P-sum(f_i)>2+P-(P/2+2)=P/2; since k≤N, we only search P≤2N.
     */
    @Test
    public void test() {
        int[] minimalProductSumNumbers = new int[N + 1];
        for (int k = 2; k <= N; k++)
            minimalProductSumNumbers[k] = Integer.MAX_VALUE;
        helper(0, 0, 1, minimalProductSumNumbers);
        ans = Arrays.stream(minimalProductSumNumbers).distinct().sum();
        check(7587457);
    }

    void helper(int count, int sum, int prod, int[] minimalProductSumNumbers) {
        int k = count + prod - sum;
        if (k <= N && prod < minimalProductSumNumbers[k])
            minimalProductSumNumbers[k] = prod;
        for (int i = 2; prod * i <= 2 * N; i++)
            helper(count + 1, sum + i, prod * i, minimalProductSumNumbers);
    }
}
