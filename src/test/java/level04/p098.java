
package level04;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import lib.EulerTest;

public class p098 extends EulerTest {

    final int L = 10000;

    /**
     * Find the largest square number that is part of a square anagram word pair (e.g. CARE and RACE
     * can be mapped to 1296=36² and 9216=96² respectively.)
     */
    @Test
    public void test() {
        List<Long> squares = list();
        for (int i : range(L))
            squares.add(sq(i));
        Multimap<Integer, Long> squaresByLength = Multimaps.index(squares, n -> (n + "").length());

        String[] words = readAsCommaSeparatedStrings();
        for (String word1 : words)
            for (String word2 : words) {
                int len = word1.length();
                if (len == word2.length() && !word1.equals(word2) && isPermutation(Lists.charactersOf(word1), Lists.charactersOf(word2)))
                    for (long square : squaresByLength.get(len))
                        if (good(word1, word2, square + "") && square > ans)
                            ans = square;
            }
        check(18769);
    }

    boolean good(String word1, String word2, String square) {
        BiMap<Character, Character> replacements = bmap();
        for (int i : range(square.length())) {
            char c = word1.charAt(i), replacement = square.charAt(i);
            if (replacements.containsKey(c) || replacements.containsValue(replacement))
                return false;
            replacements.put(c, replacement);
        }
        StringBuilder sb = sb();
        for (char c : word2.toCharArray())
            sb.append(replacements.get(c));
        return sb.charAt(0) != '0' && isSq(Long.parseLong(sb + ""));
    }
}
