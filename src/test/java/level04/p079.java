
package level04;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;

import lib.EulerTest;

public class p079 extends EulerTest {

    String ans;

    /**
     * Find the shortest possible secret pass-code that contains all the specified subsequences.
     * <p>
     * As the shortest super-sequence problem is incomplete, we make the assumption that no
     * character repeats in the pass-code. With this assumption, we try all permutations of these
     * characters until one satisfies all the subsequence restraints.
     */
    @Test
    public void test() {
        List<List<Character>> subsequences = Lists.transform(read(), Lists::charactersOf);
        Set<Character> allChars = set();
        for (List<Character> chars : subsequences)
            allChars.addAll(chars);
        permutations(list(allChars)).generateUntil(permutation -> {
            for (List<Character> subsequence : subsequences)
                if (!isSubsequence(permutation, subsequence))
                    return false;
            ans = join(permutation);
            return true;
        });
        check(ans, "73162890");
    }

    boolean isSubsequence(List<Character> s, List<Character> subsequence) {
        int index = 0;
        for (char c : s) {
            if (c == subsequence.get(index))
                index++;
            if (index == subsequence.size())
                return true;
        }
        return false;
    }
}
