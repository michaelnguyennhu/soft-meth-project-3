package main;

/**
 * Based on employee class. Specifies management employee
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Management extends Fulltime
{

    private static final int MANAGER = 1;
    private static final int DEPARTMENT_HEAD = 2;
    private static final int DIRECTOR = 3;

    private static final float MANAGER_COMPENSATION = 5000.0f / 26.0f;
    private static final float DEPARTMENT_HEAD_COMPENSATION = 9500.0f / 26.0f;
    private static final float DIRECTOR_COMPENSATION = 12000.0f / 26.0f;

    private final int role;

    /**
     * Initiates management employee with their profile, annual salary, and their management role.
     *
     * @param employeeProfile Employee profile
     * @param annualSalary    Annual Salary
     * @param managementRole  Role (1, 2, 3) - (Manager, Department Head, Director)
     */
    public Management(Profile employeeProfile, float annualSalary, int managementRole)
    {
        super(employeeProfile, annualSalary);

        this.role = managementRole;
    }


    /**
     * Get the role id of this management.
     *
     * @return ID of manager, 1-3
     */
    public int getRoleID()
    {
        return role;
    }

    /**
     * Calculates the payment based on a fulltime salary with management compensation added.
     * Saves it into paymentAmount
     * <p>
     * Overrides fulltime's calculate payment
     */
    @Override
    public void calculatePayment()
    {
        super.calculatePayment();
        this.paymentAmount += getCompensation();
    }

    /**
     * Get the compensation based on manager role type
     *
     * @return Amount of money for compensation
     */
    private float getCompensation()
    {

        switch ( role )
        {
            case MANAGER:
                return MANAGER_COMPENSATION;
            case DEPARTMENT_HEAD:
                return DEPARTMENT_HEAD_COMPENSATION;
            case DIRECTOR:
                return DIRECTOR_COMPENSATION;
        }

        throw new RuntimeException("Invalid role specified. Tried to get compensation but failed.");
    }


    /**
     * Gets the Role of a management employee as a String
     *
     * @return a String containing the role of the management employee
     */
    private String getRoleName()
    {
        switch ( role )
        {
            case MANAGER:
                return "Manager";
            case DEPARTMENT_HEAD:
                return "DepartmentHead";
            case DIRECTOR:
                return "Director";
        }

        throw new RuntimeException("Invalid role specified. Tried to parse name but failed.");
    }


    /**
     * Converts a management employee to a formatted one line string.
     * Format - profile::Payment Dollars::FULL TIME::Annual Salary Dollars::RoleName Compensation Dollars
     *
     * @return String of a management Employee's data
     */
    @Override
    public String toString()
    {
        return super.toString() + "::" + getRoleName() + " Compensation " + toDollars(getCompensation());
    }

    /**
     * Checks if the two employees are the same
     * Can check against any object, but will only work with the management employee class.
     *
     * @param obj management Employee object being compared against
     * @return a boolean indicating whether the management Employee and the object have the same profile and role
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( !(obj instanceof Management) )
        {
            return false;
        }

        Management otherEmployee = ( Management ) obj;

        return otherEmployee.getProfile().equals(getProfile()) && otherEmployee.role == role;
    }
}
