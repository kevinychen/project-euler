
package lib;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

public final class RomanNumerals extends EulerLib {

    private static final Map<String, Integer> ROMAN_NUMERALS = lmap("M", 1000, "CM", 900, "D", 500, "CD", 400, "C", 100,
        "XC", 90, "L", 50, "XL", 40, "X", 10, "IX", 9, "V", 5, "IV", 4, "I", 1);

    public static final Set<String> getRomanLetters() {
        return Sets.filter(ROMAN_NUMERALS.keySet(), s -> s.length() == 1);
    }

    public static final int toNumericValue(String romanNumeral) {
        int numericValue = 0;
        while (!romanNumeral.isEmpty())
            for (Map.Entry<String, Integer> entry : ROMAN_NUMERALS.entrySet()) {
                if (romanNumeral.startsWith(entry.getKey())) {
                    romanNumeral = romanNumeral.substring(entry.getKey().length());
                    numericValue += entry.getValue();
                    break;
                }
            }
        return numericValue;
    }

    public static final String toRomanNumeral(int numericValue) {
        StringBuilder minimalRomanNumeral = sb();
        while (numericValue > 0)
            for (Map.Entry<String, Integer> entry : ROMAN_NUMERALS.entrySet()) {
                if (numericValue >= entry.getValue()) {
                    numericValue -= entry.getValue();
                    minimalRomanNumeral.append(entry.getKey());
                    break;
                }
            }
        return minimalRomanNumeral + "";
    }

    private RomanNumerals() {
    }
}
