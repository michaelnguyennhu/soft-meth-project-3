package main;

/**
 * Based on employee class. Specifies parttime employee
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Parttime extends Employee
{
    private float hourlyRate;
    private int hours;

    private final float OVERTIME_FACTOR = 1.5f;
    private final int MAX_HOURS = 100;
    private final int OVERTIME_THRESHOLD = 80;

    /**
     * Initiates parttime employee class with profile and the hourly rate for the part time.
     *
     * @param employeeProfile Employee profile
     * @param hourlyRate Hourly rate this employee gets paid
     */
    public Parttime(Profile employeeProfile, float hourlyRate)
    {
        super(employeeProfile);

        if( hourlyRate < 0){
            throw new RuntimeException("Parttime hourly rate can not be negative");
        }

        this.hourlyRate = hourlyRate;
    }

    /**
     * Get hourly rate of this parttime employee
     * @return Hourly rate in float value
     */
    public float getHourlyRate(){
        return hourlyRate;
    }


	/**
     * Calculates the payment for part time employees and saves it as 
	 * the paymentAmount for the employee.
     *
     * Uses hours to determine pay amount.
     * For every hour until 80 hours payment is hours * hourlyRate
     * For every hour above 80 hours payment is hours * hourlyRate * 1.5f
     */
    @Override
    public void calculatePayment()
    {
        if( hours < 0 || hours > MAX_HOURS){
            throw new RuntimeException("Parttime hours must be within 0 and 100");
        }

        if ( hours > OVERTIME_THRESHOLD )
        {
            this.paymentAmount = OVERTIME_THRESHOLD * hourlyRate;

            int extraHours = hours - OVERTIME_THRESHOLD;
            this.paymentAmount += extraHours * hourlyRate * OVERTIME_FACTOR;
        } else
        {
            this.paymentAmount = ( float ) (hours) * hourlyRate;
        }

    }

	/**
     * Gets the hours from the Parttime Employee
     *
     * @return  the hours the parttime employee worked
     */
    public int getHours()
    {
        return hours;
    }

	/**
     * Sets the hours the part time employee worked
	 * An employee cannot work over 100 hours or less than 0 hours
     *
     * @param amountOfHours Hours that this employee has worked this pay period
     */
    public void setHours(int amountOfHours)
    {

        if ( amountOfHours > 100 )
        {
            throw new RuntimeException("Amount of hours can NOT exceed 100 per pay period");
        }

        if ( amountOfHours < 0 )
        {
            throw new RuntimeException("Hours can NOT be negative");
        }

        this.hours = amountOfHours;
    }

	/**
     * Converts a part time employee to a formatted one line string.
     * Format - profile::Payment Dollars::PART TIME::Hourly Rate Dollars::Hours worked this period: hours
     *
     * @return String of part time Employee's data
     */
    @Override
    public String toString()
    {
        return super.toString() + "::PART TIME::Hourly Rate " + toDollars(hourlyRate) + "::Hours worked this period: " + hours;
    }


	/**
     * Checks if the two employees are the same
     * Can check against any object, but will only work with the part time employee class.
     *
     * @param obj Part time Employee object being compared against
     * @return a boolean indicating whether the part time Employee and the object have the same profile
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( !(obj instanceof Parttime) )
        {
            return false;
        }

        Parttime otherEmployee = ( Parttime ) obj;

        return otherEmployee.getProfile().equals(getProfile());
    }

}
