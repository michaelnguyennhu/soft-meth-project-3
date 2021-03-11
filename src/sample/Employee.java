package sample;

import java.text.DecimalFormat;

/**
 * Employee base class. Contains base employee data and helper methods.
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Employee
{
    protected float paymentAmount;
    private Profile profile;


	/**
     * Initiates the Employee with the supplied Profile
     *
     * @param employeeProfile              Profile for the employee
     */
    public Employee(Profile employeeProfile)
    {
        this.profile = employeeProfile;
    }

	/**
     * Template function used for determining the payment amount for an employee's payroll
	 * This function will be Overrided
     */
    public void calculatePayment()
    {
    }


	/**
     * Gets the Profile for the Employee
     *
     * @return  the Profile for the Employee
     */
    public Profile getProfile()
    {
        return profile;
    }

	/**
     * Converts a float to a string in the format of dollars
     *
	 * @param   amount 		float value of dollars
     * @return  a string in the format of dollars derived from the amount given
     */
    protected String toDollars(float amount)
    {
        float flooredAmount = ( float ) (Math.floor(Math.round(amount * 100)) / 100);

        DecimalFormat formatter = new DecimalFormat("'$'###,###,###,###,##0.00");
        formatter.setGroupingSize(3);


        return formatter.format(flooredAmount);
    }

    /**
     * Returns the calculate payment value in raw float, not formatted for currency.
     *
     * @return Payment value in float, not formatted.
     */
    public float getRawPaymentValue()
    {
        return paymentAmount;
    }


	/**
     * Converts an employee to a formatted one line string.
     * Format - profile::Payment Dollars 
     *
     * @return String of Employee's data
     */
    @Override
    public String toString()
    {
        return profile.toString() + "::Payment " + toDollars(paymentAmount);
    }

	
	/**
     * Checks if the two employees are the same
     * Can check against any object, but will only work with the employee class.
     *
     * @param obj Employee object being compared against
     * @return a boolean indicating whether the Employee and the object have the same profile
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( !(obj instanceof Employee) )
        {
            return false;
        }

        Employee otherEmployee = ( Employee ) obj;

        return otherEmployee.profile.equals(profile);
    }
}
