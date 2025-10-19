package edu.seds514.nextdate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Boundary Value Testing for NextDate Program
 *
 * Tests boundary values for month, day, and year parameters.
 * For each parameter with range [min, max], we test:
 * - min - 1 (just below minimum - should be invalid)
 * - min (minimum valid value)
 * - min + 1 (just above minimum)
 * - max - 1 (just below maximum)
 * - max (maximum valid value)
 * - max + 1 (just above maximum - should be invalid)
 *
 * Input Ranges:
 * - Year: 1812-2100
 * - Month: 1-12
 * - Day: 1-31 (depending on month)
 */
public class NextDateBoundaryValueTest {

    private NextDate nextDate;

    /**
     * Setup method that runs before each test.
     * Creates a new NextDate instance for testing.
     */
    @Before
    public void setUp() {
        nextDate = new NextDate();
    }

    // ========================================
    // BOUNDARY VALUE TESTS FOR YEAR PARAMETER
    // ========================================
    // Year range: 1812-2100
    // Nominal values used: month=6 (June), day=15

    /**
     * Test: Year = 1811 (min - 1)
     * Expected: Should throw IllegalArgumentException (below valid range)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testYear_BelowMinimum() {
        nextDate.getNextDate(6, 15, 1811);
    }

    /**
     * Test: Year = 1812 (min)
     * Expected: Should return 06/16/1812
     */
    @Test
    public void testYear_Minimum() {
        String result = nextDate.getNextDate(6, 15, 1812);
        assertEquals("06/16/1812", result);
    }

    /**
     * Test: Year = 1813 (min + 1)
     * Expected: Should return 06/16/1813
     */
    @Test
    public void testYear_JustAboveMinimum() {
        String result = nextDate.getNextDate(6, 15, 1813);
        assertEquals("06/16/1813", result);
    }

    /**
     * Test: Year = 2099 (max - 1)
     * Expected: Should return 06/16/2099
     */
    @Test
    public void testYear_JustBelowMaximum() {
        String result = nextDate.getNextDate(6, 15, 2099);
        assertEquals("06/16/2099", result);
    }

    /**
     * Test: Year = 2100 (max)
     * Expected: Should return 06/16/2100
     */
    @Test
    public void testYear_Maximum() {
        String result = nextDate.getNextDate(6, 15, 2100);
        assertEquals("06/16/2100", result);
    }

    /**
     * Test: Year = 2101 (max + 1)
     * Expected: Should throw IllegalArgumentException (above valid range)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testYear_AboveMaximum() {
        nextDate.getNextDate(6, 15, 2101);
    }

    // =========================================
    // BOUNDARY VALUE TESTS FOR MONTH PARAMETER
    // =========================================
    // Month range: 1-12
    // Nominal values used: year=2000, day=15

    /**
     * Test: Month = 0 (min - 1)
     * Expected: Should throw IllegalArgumentException (below valid range)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMonth_BelowMinimum() {
        nextDate.getNextDate(0, 15, 2000);
    }

    /**
     * Test: Month = 1 (min - January)
     * Expected: Should return 01/16/2000
     */
    @Test
    public void testMonth_Minimum() {
        String result = nextDate.getNextDate(1, 15, 2000);
        assertEquals("01/16/2000", result);
    }

    /**
     * Test: Month = 2 (min + 1 - February)
     * Expected: Should return 02/16/2000
     */
    @Test
    public void testMonth_JustAboveMinimum() {
        String result = nextDate.getNextDate(2, 15, 2000);
        assertEquals("02/16/2000", result);
    }

    /**
     * Test: Month = 11 (max - 1 - November)
     * Expected: Should return 11/16/2000
     */
    @Test
    public void testMonth_JustBelowMaximum() {
        String result = nextDate.getNextDate(11, 15, 2000);
        assertEquals("11/16/2000", result);
    }

    /**
     * Test: Month = 12 (max - December)
     * Expected: Should return 12/16/2000
     */
    @Test
    public void testMonth_Maximum() {
        String result = nextDate.getNextDate(12, 15, 2000);
        assertEquals("12/16/2000", result);
    }

    /**
     * Test: Month = 13 (max + 1)
     * Expected: Should throw IllegalArgumentException (above valid range)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMonth_AboveMaximum() {
        nextDate.getNextDate(13, 15, 2000);
    }

    // =======================================
    // BOUNDARY VALUE TESTS FOR DAY PARAMETER
    // =======================================
    // Day range: 1-31 (varies by month)
    // Nominal values used: year=2000, month=1 (January has 31 days)

    /**
     * Test: Day = 0 (min - 1)
     * Expected: Should throw IllegalArgumentException (below valid range)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDay_BelowMinimum() {
        nextDate.getNextDate(1, 0, 2000);
    }

    /**
     * Test: Day = 1 (min)
     * Expected: Should return 01/02/2000
     */
    @Test
    public void testDay_Minimum() {
        String result = nextDate.getNextDate(1, 1, 2000);
        assertEquals("01/02/2000", result);
    }

    /**
     * Test: Day = 2 (min + 1)
     * Expected: Should return 01/03/2000
     */
    @Test
    public void testDay_JustAboveMinimum() {
        String result = nextDate.getNextDate(1, 2, 2000);
        assertEquals("01/03/2000", result);
    }

    /**
     * Test: Day = 30 (max - 1 for 31-day month)
     * Expected: Should return 01/31/2000
     */
    @Test
    public void testDay_JustBelowMaximum() {
        String result = nextDate.getNextDate(1, 30, 2000);
        assertEquals("01/31/2000", result);
    }

    /**
     * Test: Day = 31 (max for 31-day month)
     * Expected: Should return 02/01/2000 (rolls to next month)
     */
    @Test
    public void testDay_Maximum() {
        String result = nextDate.getNextDate(1, 31, 2000);
        assertEquals("02/01/2000", result);
    }

    /**
     * Test: Day = 32 (max + 1)
     * Expected: Should throw IllegalArgumentException (above valid range)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDay_AboveMaximum() {
        nextDate.getNextDate(1, 32, 2000);
    }
}
