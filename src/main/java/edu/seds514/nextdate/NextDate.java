package edu.seds514.nextdate;

/**
 * NextDate Program - Calculates the next date given a valid date input.
 * SEDS 514 - Project 1
 */
public class NextDate {

    /**
     * Calculates the next date for a given date.
     *
     * @param month the month (1-12)
     * @param day the day (1-31 depending on month)
     * @param year the year (1812-2100)
     * @return a string representing the next date in format "MM/DD/YYYY"
     * @throws IllegalArgumentException if the input date is invalid
     */
    public String getNextDate(int month, int day, int year) {
        // Validate input
        validateDate(month, day, year);

        // Start by incrementing the day by 1
        int nextDay = day + 1;
        int nextMonth = month;
        int nextYear = year;

        // Check if we've exceeded the days in the current month
        if (nextDay > getDaysInMonth(month, year)) {
            // Move to the first day of the next month
            nextDay = 1;
            nextMonth = month + 1;

            // Check if we've exceeded 12 months
            if (nextMonth > 12) {
                // Move to January of the next year
                nextMonth = 1;
                nextYear = year + 1;
            }
        }

        // Return the next date formatted as MM/DD/YYYY
        return String.format("%02d/%02d/%04d", nextMonth, nextDay, nextYear);
    }

    /**
     * Validates the input date.
     *
     * @param month the month (1-12)
     * @param day the day (1-31 depending on month)
     * @param year the year (1812-2100)
     * @throws IllegalArgumentException if the input date is invalid
     */
    private void validateDate(int month, int day, int year) {
        if (year < 1812 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1812 and 2100");
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31");
        }

        // Validate day is appropriate for the specific month
        int maxDaysInMonth = getDaysInMonth(month, year);
        if (day > maxDaysInMonth) {
            throw new IllegalArgumentException(
                "Day " + day + " is invalid for month " + month + " in year " + year +
                " (max " + maxDaysInMonth + " days)");
        }
    }

    /**
     * Checks if a year is a leap year.
     * Rules (in priority order):
     * 1. Divisible by 400 -> leap year (e.g., 2000)
     * 2. Divisible by 100 -> NOT a leap year (e.g., 1900, 2100)
     * 3. Divisible by 4 -> leap year (e.g., 2024)
     * 4. Otherwise -> NOT a leap year (e.g., 2023)
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    private boolean isLeapYear(int year) {
        // Rule 1: Check divisible by 400 first (most specific)
        if (year % 400 == 0) {
            return true;
        }
        // Rule 2: Divisible by 100 but not 400 -> NOT a leap year
        if (year % 100 == 0) {
            return false;
        }
        // Rule 3: Divisible by 4 but not 100 -> leap year
        if (year % 4 == 0) {
            return true;
        }
        // Rule 4: Not divisible by 4 -> NOT a leap year
        return false;
    }

    /**
     * Gets the number of days in a given month.
     * 31 days: Jan(1), Mar(3), May(5), Jul(7), Aug(8), Oct(10), Dec(12)
     * 30 days: Apr(4), Jun(6), Sep(9), Nov(11)
     * 28/29 days: Feb(2) - depends on leap year
     *
     * @param month the month (1-12)
     * @param year the year
     * @return the number of days in the month
     */
    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1:  // January
            case 3:  // March
            case 5:  // May
            case 7:  // July
            case 8:  // August
            case 10: // October
            case 12: // December
                return 31;

            case 4:  // April
            case 6:  // June
            case 9:  // September
            case 11: // November
                return 30;

            case 2:  // February
                // Check if it's a leap year
                if (isLeapYear(year)) {
                    return 29; // Leap year February
                } else {
                    return 28; // Regular February
                }

            default:
                // This should never happen due to validation
                throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    /**
     * Main method for testing the NextDate program.
     */
    public static void main(String[] args) {
        NextDate nextDate = new NextDate();

        System.out.println("=== Testing NextDate Program ===\n");

        // Test case 1: Normal day in the middle of a month
        System.out.println("Test 1: 06/15/2024");
        System.out.println("Next date: " + nextDate.getNextDate(6, 15, 2024));
        System.out.println();

        // Test case 2: Last day of a 30-day month
        System.out.println("Test 2: 06/30/2024");
        System.out.println("Next date: " + nextDate.getNextDate(6, 30, 2024));
        System.out.println();

        // Test case 3: Last day of a 31-day month
        System.out.println("Test 3: 01/31/2024");
        System.out.println("Next date: " + nextDate.getNextDate(1, 31, 2024));
        System.out.println();

        // Test case 4: Last day of December (year change)
        System.out.println("Test 4: 12/31/2024");
        System.out.println("Next date: " + nextDate.getNextDate(12, 31, 2024));
        System.out.println();

        // Test case 5: February in a leap year
        System.out.println("Test 5: 02/28/2024");
        System.out.println("Next date: " + nextDate.getNextDate(2, 28, 2024));
        System.out.println();

        // Test case 6: February in a non-leap year
        System.out.println("Test 6: 02/28/2023");
        System.out.println("Next date: " + nextDate.getNextDate(2, 28, 2023));
        System.out.println();

        // Test case 7: Upper boundary - last day of year 2100
        System.out.println("Test 7: 12/31/2100 (upper boundary)");
        System.out.println("Next date: " + nextDate.getNextDate(12, 31, 2100));
        System.out.println();

        // Test case 8: Lower boundary - last day of year 1811 (before valid range)
        System.out.println("Test 8: 12/31/1811 (before lower boundary)");
        try {
            System.out.println("Next date: " + nextDate.getNextDate(12, 31, 1811));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
