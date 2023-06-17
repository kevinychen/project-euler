
package level03;

import org.junit.Test;

import lib.EulerTest;

public class p059 extends EulerTest {

    final int N = 3;

    /**
     * Find the sum of the ASCII values of the decrypted text, encrypted by repeatedly xor-ing with
     * a N-letter key consisting of letters from a to z.
     * <p>
     * Find the key that yields the maximum number of spaces in the decrypted message.
     */
    @Test
    public void test() {
        long[] encrypted = readAsGrid(",")[0];
        int[] key = new int[N];
        for (int slice : range(N)) {
            int maxNumSpaces = 0;
            for (char keychar = 'a'; keychar <= 'z'; keychar++) {
                int numSpaces = 0;
                for (int i = slice; i < encrypted.length; i += N)
                    if ((encrypted[i] ^ keychar) == ' ')
                        numSpaces++;
                if (numSpaces > maxNumSpaces) {
                    maxNumSpaces = numSpaces;
                    key[slice] = keychar;
                }
            }
        }
        for (int i : range(encrypted.length))
            ans += encrypted[i] ^ key[i % N];
        check(129448);
    }
}
