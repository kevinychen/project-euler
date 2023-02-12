
package level01;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import lib.EulerTest;

public class p019 extends EulerTest {

    final int YEAR_START = 1901, YEAR_END = 2000;

    /**
     * Find the number of Sundays on the first of the month from YEAR_START to YEAR_END.
     */
    @Test
    public void test() {
        for (int year : rangeC(YEAR_START, YEAR_END))
            for (int month : rangeC(Calendar.JANUARY, Calendar.DECEMBER))
                if (new GregorianCalendar(year, month, 1).get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                    ans++;
        check(171);
    }
}
