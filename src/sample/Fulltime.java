package sample;

/**
 * Based on employee class. Specifies fulltime employee
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Fulltime extends Employee
{
    private final float annualSalary;

    /**
     * Initates a fulltime employee with a profile and annual salary.
     *
     * @param employeeProfile Employee details
     * @param annualSalary The amount of money they should be making in a year
     */
    public Fulltime(Profile employeeProfile, float annualSalary)
    {
        super(employeeProfile);

        if( annualSalary < 0){
            throw new RuntimeException("Fulltime annual salary can not be negative");
        }

        this.annualSalary = annualSalary;
    }


	/**
     * Calculates the payment for fulltime employees and saves it as 
	 * the paymentAmount for the employee
     *
     * Calculation based on annualSalary and two-week pay periods.
     */
    @Override
    public void calculatePayment()
    {
        this.paymentAmount = annualSalary / 26.0f;
    }

	/**
     * Converts a full time employee to a formatted one line string.
     * Format - profile::Payment Dollars::FULL TIME::Annual Salary Dollars
     *
     * @return String of full time Employee's data
     */
    @Override
    public String toString()
    {
        return super.toString() + "::FULL TIME::Annual Salary " + toDollars(annualSalary);
    }


	/**
     * Checks if the two employees are the same
     * Can check against any object, but will only work with the fulltime employee class.
     *
     * @param obj Full time Employee object being compared against
     * @return a boolean indicating whether the full time Employee and the object have the same profile
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( !(obj instanceof Fulltime) )
        {
            return false;
        }

        Fulltime otherEmployee = ( Fulltime ) obj;

        return otherEmployee.getProfile().equals(getProfile());
    }
}
