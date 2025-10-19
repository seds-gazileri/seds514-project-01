package edu.seds514.nextdate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Equivalence Class Testing for NextDate Program
 *
 * Tests representative values from different equivalence classes.
 * Each class represents a category of inputs that should behave similarly.
 *
 * Valid Equivalence Classes:
 * - Month types: 31-day, 30-day, Feb (leap), Feb (non-leap)
 * - Year types: divisible by 400, by 4 not 100, by 100 not 400, regular
 * - Day transitions: normal, end of month, end of year
 *
 * Invalid Equivalence Classes:
 * - Invalid months, days, years
 */
public class NextDateEquivalenceClassTest {

    private NextDate nextDate;

    @Before
    public void setUp() {
        nextDate = new NextDate();
    }

    // ========================================
    // VALID EQUIVALENCE CLASSES - MONTH TYPES
    // ========================================

    /**
     * Test: 31-day month (January)
     * Representative: January 15, 2000
     * Expected: 01/16/2000
     */
    @Test
    public void testMonth_31Days() {
        String result = nextDate.getNextDate(1, 15, 2000);
        assertEquals("01/16/2000", result);
    }

    /**
     * Test: 30-day month (April)
     * Representative: April 15, 2000
     * Expected: 04/16/2000
     */
    @Test
    public void testMonth_30Days() {
        String result = nextDate.getNextDate(4, 15, 2000);
        assertEquals("04/16/2000", result);
    }

    /**
     * Test: February in leap year (29 days)
     * Representative: February 15, 2000
     * Expected: 02/16/2000
     */
    @Test
    public void testMonth_FebruaryLeapYear() {
        String result = nextDate.getNextDate(2, 15, 2000);
        assertEquals("02/16/2000", result);
    }

    /**
     * Test: February in non-leap year (28 days)
     * Representative: February 15, 2023
     * Expected: 02/16/2023
     */
    @Test
    public void testMonth_FebruaryNonLeapYear() {
        String result = nextDate.getNextDate(2, 15, 2023);
        assertEquals("02/16/2023", result);
    }

    // =============================================
    // VALID EQUIVALENCE CLASSES - LEAP YEAR TYPES
    // =============================================

    /**
     * Test: Leap year divisible by 400
     * Representative: February 28, 2000
     * Expected: 02/29/2000 (leap year, so Feb has 29 days)
     */
    @Test
    public void testLeapYear_DivisibleBy400() {
        String result = nextDate.getNextDate(2, 28, 2000);
        assertEquals("02/29/2000", result);
    }

    /**
     * Test: Leap year divisible by 4 but not 100
     * Representative: February 28, 2024
     * Expected: 02/29/2024 (leap year, so Feb has 29 days)
     */
    @Test
    public void testLeapYear_DivisibleBy4Not100() {
        String result = nextDate.getNextDate(2, 28, 2024);
        assertEquals("02/29/2024", result);
    }

    /**
     * Test: Non-leap year divisible by 100 but not 400
     * Representative: February 28, 2100
     * Expected: 03/01/2100 (NOT leap year, Feb has only 28 days)
     */
    @Test
    public void testNonLeapYear_DivisibleBy100Not400() {
        String result = nextDate.getNextDate(2, 28, 2100);
        assertEquals("03/01/2100", result);
    }

    /**
     * Test: Non-leap year (regular year not divisible by 4)
     * Representative: February 28, 2023
     * Expected: 03/01/2023 (NOT leap year, Feb has only 28 days)
     */
    @Test
    public void testNonLeapYear_NotDivisibleBy4() {
        String result = nextDate.getNextDate(2, 28, 2023);
        assertEquals("03/01/2023", result);
    }

    // ===============================================
    // VALID EQUIVALENCE CLASSES - DAY TRANSITIONS
    // ===============================================

    /**
     * Test: Normal day transition (middle of month)
     * Representative: June 15, 2000
     * Expected: 06/16/2000
     */
    @Test
    public void testDayTransition_MiddleOfMonth() {
        String result = nextDate.getNextDate(6, 15, 2000);
        assertEquals("06/16/2000", result);
    }

    /**
     * Test: Last day of 31-day month
     * Representative: January 31, 2000
     * Expected: 02/01/2000 (rolls to next month)
     */
    @Test
    public void testDayTransition_EndOf31DayMonth() {
        String result = nextDate.getNextDate(1, 31, 2000);
        assertEquals("02/01/2000", result);
    }

    /**
     * Test: Last day of 30-day month
     * Representative: April 30, 2000
     * Expected: 05/01/2000 (rolls to next month)
     */
    @Test
    public void testDayTransition_EndOf30DayMonth() {
        String result = nextDate.getNextDate(4, 30, 2000);
        assertEquals("05/01/2000", result);
    }

    /**
     * Test: Last day of February in leap year
     * Representative: February 29, 2000
     * Expected: 03/01/2000 (rolls to March)
     */
    @Test
    public void testDayTransition_EndOfFebruaryLeapYear() {
        String result = nextDate.getNextDate(2, 29, 2000);
        assertEquals("03/01/2000", result);
    }

    /**
     * Test: Last day of February in non-leap year
     * Representative: February 28, 2019
     * Expected: 03/01/2019 (rolls to March)
     */
    @Test
    public void testDayTransition_EndOfFebruaryNonLeapYear() {
        String result = nextDate.getNextDate(2, 28, 2019);
        assertEquals("03/01/2019", result);
    }

    /**
     * Test: Last day of year
     * Representative: December 31, 2000
     * Expected: 01/01/2001 (rolls to next year)
     */
    @Test
    public void testDayTransition_EndOfYear() {
        String result = nextDate.getNextDate(12, 31, 2000);
        assertEquals("01/01/2001", result);
    }

    // ===========================================
    // INVALID EQUIVALENCE CLASSES
    // ===========================================

    /**
     * Test: Invalid month (too small)
     * Representative: month = 0
     * Expected: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalid_MonthTooSmall() {
        nextDate.getNextDate(0, 15, 2000);
    }

    /**
     * Test: Invalid month (too large)
     * Representative: month = 13
     * Expected: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalid_MonthTooLarge() {
        nextDate.getNextDate(13, 15, 2000);
    }

    /**
     * Test: Invalid day (too small)
     * Representative: day = 0
     * Expected: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalid_DayTooSmall() {
        nextDate.getNextDate(1, 0, 2000);
    }

    /**
     * Test: Invalid day (too large for month)
     * Representative: February 30, 2000 (Feb only has 29 days in leap year)
     * Expected: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalid_DayTooLargeForMonth() {
        nextDate.getNextDate(2, 30, 2000);
    }

    /**
     * Test: Invalid year (too small)
     * Representative: year = 1811
     * Expected: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalid_YearTooSmall() {
        nextDate.getNextDate(6, 15, 1811);
    }

    /**
     * Test: Invalid year (too large)
     * Representative: year = 2101
     * Expected: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalid_YearTooLarge() {
        nextDate.getNextDate(6, 15, 2101);
    }
}
