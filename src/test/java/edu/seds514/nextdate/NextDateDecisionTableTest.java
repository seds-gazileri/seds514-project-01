package edu.seds514.nextdate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Decision Table Testing for NextDate Program
 *
 * Tests based on decision table that covers different combinations of conditions
 * that affect the behavior of the nextDate calculation.
 *
 * DECISION TABLE:
 * ===============================================================================
 * | Rule | 1       | 2         | 3         | 4          | 5         | 6            | 7     |
 * |------|---------|-----------|-----------|------------|-----------|--------------|-------|
 * | Month| Any     | 31-day    | 30-day    | Feb(leap)  | Feb(leap) | Feb(non-leap)| Dec   |
 * | Day  | 15      | 31        | 30        | 28         | 29        | 28           | 31    |
 * | Last?| No      | Yes       | Yes       | No         | Yes       | Yes          | Yes   |
 * |------|---------|-----------|-----------|------------|-----------|--------------|-------|
 * |ACTION|         |           |           |            |           |              |       |
 * | day++| X       |           |           | X          |           |              |       |
 * | new  |         | X         | X         |            | X         | X            |       |
 * | month|         |           |           |            |           |              |       |
 * | new  |         |           |           |            |           |              | X     |
 * | year |         |           |           |            |           |              |       |
 * ===============================================================================
 *
 * Rules Explained:
 * Rule 1: Normal day in middle of month → increment day only
 * Rule 2: Last day of 31-day month → new month
 * Rule 3: Last day of 30-day month → new month
 * Rule 4: Feb 28 in leap year → NOT last day, increment to Feb 29
 * Rule 5: Feb 29 in leap year → last day, new month (March)
 * Rule 6: Feb 28 in non-leap year → last day, new month (March)
 * Rule 7: Dec 31 → new year
 */
public class NextDateDecisionTableTest {

    private NextDate nextDate;

    @Before
    public void setUp() {
        nextDate = new NextDate();
    }

    // ========================================
    // RULE 1: Normal day in middle of month
    // ========================================
    // Condition: Any month, day in middle (not last)
    // Action: Increment day only

    /**
     * Rule 1: Normal day transition
     * Input: June 15, 2000 (30-day month, middle day)
     * Expected: 06/16/2000 (day increments)
     */
    @Test
    public void testRule1_NormalDay() {
        String result = nextDate.getNextDate(6, 15, 2000);
        assertEquals("06/16/2000", result);
    }

    // ========================================
    // RULE 2: Last day of 31-day month
    // ========================================
    // Condition: 31-day month (Jan,Mar,May,Jul,Aug,Oct,Dec), day=31
    // Action: Reset day=1, increment month

    /**
     * Rule 2: Last day of 31-day month
     * Input: January 31, 2000
     * Expected: 02/01/2000 (new month)
     */
    @Test
    public void testRule2_LastDayOf31DayMonth() {
        String result = nextDate.getNextDate(1, 31, 2000);
        assertEquals("02/01/2000", result);
    }

    /**
     * Rule 2: Another 31-day month example
     * Input: March 31, 2000
     * Expected: 04/01/2000 (new month)
     */
    @Test
    public void testRule2_LastDayOf31DayMonth_March() {
        String result = nextDate.getNextDate(3, 31, 2000);
        assertEquals("04/01/2000", result);
    }

    // ========================================
    // RULE 3: Last day of 30-day month
    // ========================================
    // Condition: 30-day month (Apr,Jun,Sep,Nov), day=30
    // Action: Reset day=1, increment month

    /**
     * Rule 3: Last day of 30-day month
     * Input: April 30, 2000
     * Expected: 05/01/2000 (new month)
     */
    @Test
    public void testRule3_LastDayOf30DayMonth() {
        String result = nextDate.getNextDate(4, 30, 2000);
        assertEquals("05/01/2000", result);
    }

    /**
     * Rule 3: Another 30-day month example
     * Input: September 30, 2000
     * Expected: 10/01/2000 (new month)
     */
    @Test
    public void testRule3_LastDayOf30DayMonth_September() {
        String result = nextDate.getNextDate(9, 30, 2000);
        assertEquals("10/01/2000", result);
    }

    // ========================================
    // RULE 4: Feb 28 in LEAP year
    // ========================================
    // Condition: February in leap year, day=28
    // Action: Increment day to 29 (NOT last day of month!)

    /**
     * Rule 4: Feb 28 in leap year (NOT last day)
     * Input: February 28, 2000 (leap year)
     * Expected: 02/29/2000 (day increments, stays in February)
     */
    @Test
    public void testRule4_Feb28LeapYear() {
        String result = nextDate.getNextDate(2, 28, 2000);
        assertEquals("02/29/2000", result);
    }

    /**
     * Rule 4: Feb 28 in leap year (divisible by 4, not 100)
     * Input: February 28, 2024 (leap year)
     * Expected: 02/29/2024 (day increments, stays in February)
     */
    @Test
    public void testRule4_Feb28LeapYear_2024() {
        String result = nextDate.getNextDate(2, 28, 2024);
        assertEquals("02/29/2024", result);
    }

    // ========================================
    // RULE 5: Feb 29 in LEAP year
    // ========================================
    // Condition: February 29 in leap year (last day)
    // Action: Reset day=1, increment month to March

    /**
     * Rule 5: Feb 29 in leap year (last day)
     * Input: February 29, 2000 (leap year)
     * Expected: 03/01/2000 (new month)
     */
    @Test
    public void testRule5_Feb29LeapYear() {
        String result = nextDate.getNextDate(2, 29, 2000);
        assertEquals("03/01/2000", result);
    }

    /**
     * Rule 5: Feb 29 in another leap year
     * Input: February 29, 2024 (leap year)
     * Expected: 03/01/2024 (new month)
     */
    @Test
    public void testRule5_Feb29LeapYear_2024() {
        String result = nextDate.getNextDate(2, 29, 2024);
        assertEquals("03/01/2024", result);
    }

    // ========================================
    // RULE 6: Feb 28 in NON-LEAP year
    // ========================================
    // Condition: February in non-leap year, day=28 (last day)
    // Action: Reset day=1, increment month to March

    /**
     * Rule 6: Feb 28 in non-leap year (last day)
     * Input: February 28, 2023 (non-leap year)
     * Expected: 03/01/2023 (new month)
     */
    @Test
    public void testRule6_Feb28NonLeapYear() {
        String result = nextDate.getNextDate(2, 28, 2023);
        assertEquals("03/01/2023", result);
    }

    /**
     * Rule 6: Feb 28 in non-leap year (divisible by 100 but not 400)
     * Input: February 28, 2100 (NOT a leap year)
     * Expected: 03/01/2100 (new month)
     */
    @Test
    public void testRule6_Feb28NonLeapYear_2100() {
        String result = nextDate.getNextDate(2, 28, 2100);
        assertEquals("03/01/2100", result);
    }

    /**
     * Rule 6: Feb 28 in regular non-leap year
     * Input: February 28, 2019 (not divisible by 4)
     * Expected: 03/01/2019 (new month)
     */
    @Test
    public void testRule6_Feb28NonLeapYear_2019() {
        String result = nextDate.getNextDate(2, 28, 2019);
        assertEquals("03/01/2019", result);
    }

    // ========================================
    // RULE 7: December 31 (end of year)
    // ========================================
    // Condition: December 31 (last day of year)
    // Action: Reset day=1, reset month=1, increment year

    /**
     * Rule 7: Last day of year
     * Input: December 31, 2000
     * Expected: 01/01/2001 (new year)
     */
    @Test
    public void testRule7_EndOfYear() {
        String result = nextDate.getNextDate(12, 31, 2000);
        assertEquals("01/01/2001", result);
    }

    /**
     * Rule 7: End of year, another example
     * Input: December 31, 2024
     * Expected: 01/01/2025 (new year)
     */
    @Test
    public void testRule7_EndOfYear_2024() {
        String result = nextDate.getNextDate(12, 31, 2024);
        assertEquals("01/01/2025", result);
    }
}
