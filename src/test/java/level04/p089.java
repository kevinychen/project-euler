
package level04;

import org.junit.Test;

import lib.EulerTest;
import lib.RomanNumerals;

public class p089 extends EulerTest {

    /**
     * Find the number of characters saved by writing each of the specified Roman numerals in
     * minimal form.
     */
    @Test
    public void test() {
        for (String romanNumeral : read()) {
            int numericValue = RomanNumerals.toNumericValue(romanNumeral);
            String minimalRomanNumeral = RomanNumerals.toRomanNumeral(numericValue);
            ans += romanNumeral.length() - minimalRomanNumeral.length();
        }
        check(743);
    }
}
