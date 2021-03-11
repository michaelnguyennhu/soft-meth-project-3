package sample;

/**
 * Contains data that defines an employee. Name, department, date hired.
 * Profile Data includes an Employee's name, department, and date they were hired
 * 
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Profile
{
    private final String name; //employees name in the form "lastname,firstname"
    private final String department; //department code: CS, ECE, IT
	private final Date dateHired; //the date the employee was hired
	

	/**
     * Initiates the Profile with the supplied values
     *
     * @param name              Employee's name
     * @param department        Department the Employee is in
     * @param dateHired         Date they were hired
     */
    public Profile(String name, String department, Date dateHired)
    {
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
	}
	

	/**
     * Gets the name from the Profile
     *
     * @return  the name from the Profile
     */
    public String getName()
    {
        return this.name;
    }



	/**
     * Gets the Department from the Profile
     *
     * @return  the department from the Profile
     */	
    public String getDepartment()
    {
        return this.department;
    }


	/**
     * Gets the Date class from the Profile
     *
     * @return  Date object from the Profile
     */
    public Date getDateHired()
    {
        return this.dateHired;
    }


	/**
     * Checks if the name is in the valid format
     *
     * @return  a boolean which indicates whether the name is valid
     */
    public boolean isNameValid()
    {

        //Loosely check if there is a comma somewhere in the first and last name.
        //This can lead to improper name invalidations/validations. But just for a safety check, can be used for now.
        return name.indexOf(',') != -1;

    }


	/**
     * Checks if the Department is one of the three:
	 * CS, ECE, or IT
     *
     * @return  a boolean which indicates whether the department is valid
     */
    public boolean isDepartmentValid()
    {
        switch ( department )
        {
            case "CS":
            case "ECE":
            case "IT":
                return true;
        }

        return false;
    }


	 /**
     * Converts a Profile to formatted one line string.
     * Format - name::department::dateHired
     *
     * @return String of Profile's data.
     */
    @Override
    public String toString()
    {
        return this.name + "::" + this.department + "::" + this.dateHired.toString();
	}
	

	/**
     * Checks if the two profiles are the same
     * Can check against any object, but will only work with the Profile class.
     *
     * @param obj Profile object being compared against
     * @return a boolean indicating whether the profile and the object have the same
	 * 		   name, department, and dateHired
     */
    @Override
    public boolean equals(Object obj)
    {    //compare name, department, and dateHired
        if ( !(obj instanceof Profile) )
        {
            return false;
        }

        Profile profile2 = ( Profile ) obj;

        return this.name.equals(profile2.getName()) && this.department.equals(profile2.getDepartment()) && this.dateHired.compareTo(profile2.getDateHired()) == 0;
    }
}
