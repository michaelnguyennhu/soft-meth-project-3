package sample;

import java.util.Calendar;

/**
 * Stores a date which includes day, month, year.
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Date implements Comparable< Date >
{
    private int year;
    private int month;
    private int day;

    /**
     * Reads date and parses it from mm/dd/yyyy format.
     *
     * @param date date in mm/dd/yyyy format.
     */
    public Date(String date)
    {

        int firstDash;
        int secondDash;

        firstDash = date.indexOf("/");
        if ( firstDash == -1 )
        {
            year = -1;
            month = -1;
            day = -1;
            return;
        }

        secondDash = date.indexOf("/", firstDash + 1);
        if ( secondDash == -1 )
        {
            year = -1;
            month = -1;
            day = -1;
            return;
        }


        try
        {
            month = Integer.parseInt(date.substring(0, firstDash));
            day = Integer.parseInt(date.substring(firstDash + 1, secondDash));
            year = Integer.parseInt(date.substring(secondDash + 1));
        } catch ( NumberFormatException e )
        {
            year = -1;
            month = -1;
            day = -1;
            return;
        }

    }

	/**
     * Compares two date classes
     *
     * @param date date object that is being compared
	 * @return there are 3 return values:
	 * 		   0 - when the two dates are the same
	 * 		   1 - when the date in the parameter is less than the other date
	 * 		  -1 - when the date in the parameter is greater than the other date
     */
    @Override
    public int compareTo(Date date)
    {
        if ( this.day == date.day && this.month == date.month && this.year == date.year )
        {
            return 0;
        } else if ( this.year > date.year || (this.year == date.year && this.month > date.month) || (this.year == date.year && this.month == date.month && this.day > date.day) )
        {
            return 1;
        } else
        {
            return -1;
        }


    }

    /**
     * Checks if the current date is valid.
     * Validity depends on multiple factors.
     * - Leap year
     * - If the day falls within that month's day range
     * - If the month is correct
     * - If the year is between today's year and 1900
     *
     * @return If this date is within the parameter range
     */
    public boolean isValid()
    {
        Calendar today = Calendar.getInstance(); //get today's date
        int thisYear = today.get(Calendar.YEAR);
        int thisMonth = today.get(Calendar.MONTH) + 1;
        int thisDay = today.get(Calendar.DATE);


        //check if the date is within the correct boundaries
        if ( this.year < 1900 || this.year > thisYear || (this.year == thisYear && this.month > thisMonth) || (this.year == thisYear && this.month == thisMonth && this.day > thisDay) )
        {
            return false;
        }

        boolean leapYear = false;

        if ( this.year % 4 == 0 )
        { //check if the year is a leap year
            if ( this.year % 100 == 0 )
            {
                if ( this.year % 400 == 0 )
                {
                    leapYear = true;
                }
            } else
            {
                leapYear = true;
            }
        }

        //check if the day is valid for the month
        if ( this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10 || this.month == 12 )
        {
            return this.day > 0 && this.day <= 31;
        } else if ( this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11 )
        {
            return this.day > 0 && this.day <= 30;
        } else if ( this.month == 2 )
        {
            if ( leapYear == true )
            {
                return this.day > 0 && this.day <= 29;
            } else
            {
                return this.day > 0 && this.day <= 28;
            }

        } else
        {
            return false;
        }
    }

    /**
     * Converts this date into mm/dd/yyyy
     *
     * @return String format of the date
     */
    @Override
    public String toString()
    {
        return this.month + "/" + this.day + "/" + this.year;
    }
}
