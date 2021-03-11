package sample;

/**
 * Manages and stores all supplied employees
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */


public class Company
{
    private Employee[] emplist;
    private int numEmployee;


	/**
     * Initiates the Company with 0 employees and an empty employee list
     */
    public Company()
    {
        emplist = new Employee[0];
        numEmployee = 0;
	}
	

	/**
     * Finds an employee in the company's emplist
     *
     * @param employee      Employee to find
	 * @return 		the index of the employee in the emplist. 
	 * 				returns -1 if the employee is not in the the emplist
     */
    private int find(Employee employee)
    {

        for ( int i = 0; i < numEmployee; i++ )
        {
            if ( emplist[i].getProfile().equals(employee.getProfile()) )
            {
                return i;
            }
        }

        return -1;
    }


	/**
     * Increases the size of emplist 
     */
    private void grow()
    {
        Employee[] increasedArr = new Employee[emplist.length + 4];

        for ( int i = 0; i < emplist.length; i++ )
        {
            increasedArr[i] = emplist[i];
        }

        emplist = increasedArr;
    }

	/**
     * Adds an employee to the emplist
     *
     * @param employee        employee to be added to emplist
	 * @return 		a boolean that indicates whether the employee was added to the emplist or not
	 * 				true if the employee was added
	 * 				false if the employee was not added
     */
    public boolean add(Employee employee)
    {
        if ( !(employee.getProfile().isNameValid()
                && employee.getProfile().isDepartmentValid()
                && employee.getProfile().getDateHired().isValid()) )
        {
            return false;
        }
        if ( find(employee) != -1 )
        {
            return false;
        }

        while ( numEmployee >= emplist.length )
        {
            grow();
        }

        numEmployee++;
        emplist[numEmployee - 1] = employee;

        return true;
    }


	/**
     * Removes an employee from the emplist
     *
     * @param employee        employee to be removed from the emplist
	 * @return 		a boolean that indicates whether the employee was removed from emplist
	 * 				True if the employee was removed
	 * 				False if the employee was not removed
     */
    public boolean remove(Employee employee)
    { //maintain the original sequence

        int index = find(employee);

        if ( index == -1 )
        {
            return false;
        }

        for ( int i = index + 1; i < numEmployee; i++ )
        {
            emplist[i - 1] = emplist[i];
        }

        numEmployee--;

        return true;
    }


    /**
     *
     * Set the hours for a parttime employee
     *
     * @param employee Employee must instance of Parttime, and have the hours already set.
     * @return a boolean that indicates whether the employee was found
     * 	 * 				True if the employee was found and set
     * 	 * 				False if the employee was not found
     */
    public boolean setHours(Employee employee)
    {

        if ( !(employee instanceof Parttime) )
        {
            return false;
        }

        if ( !(employee.getProfile().isNameValid()
                && employee.getProfile().isDepartmentValid()
                && employee.getProfile().getDateHired().isValid()) )
        {
            return false;
        }

        int index = find(employee);

        if ( index == -1 )
        {
            return false;
        }

        if(!(emplist[index] instanceof Parttime)){
            return false;
        }

        //Instead of overwriting the emplist index,
        //we have to manual insert the hourlyRate as to not get rid of other data that may be tied
        //This could be changed to work inside employee as a private variable, but for simplicity sake we do this.

        Parttime employeeInList = ( Parttime ) emplist[index];
        Parttime employeeGiven = ( Parttime ) employee;
        employeeInList.setHours(employeeGiven.getHours());

        return true;
    }

	/**
	 * Calculates the payment for each employee in the emplist
	 * This works for full time, part time, and management employee
	 */
    public void processPayments()
    {
        for ( int i = 0; i < numEmployee; i++ )
        {
            emplist[i].calculatePayment();
        }
    }


	/**
     * Sorts the emplist by department or by date
     *
     * @param sortType      0 to sort by department, 1 to sort by date
     */
    private void sort(int sortType)
    {

        //Bubble sort
        for ( int x = 0; x < numEmployee; x++ )
        {
            int pushIndex = x;

            for ( int y = x - 1; y >= 0; y-- )
            {
                int compare = 0;

                switch ( sortType )
                {
                    case 0: //Department
                        compare = emplist[x].getProfile().getDepartment().compareTo(emplist[y].getProfile().getDepartment());
                        break;
                    case 1: //Date
                        compare = emplist[x].getProfile().getDateHired().compareTo(emplist[y].getProfile().getDateHired());
                        break;
                }

                if ( compare < 0 || compare == 0 )
                {
                    pushIndex = y;
                }
            }

            Employee currentEmployee = emplist[x];

            //Push all current books back. The amount should not pass the capacity
            for ( int i = x - 1; i >= pushIndex; i-- )
            {
                emplist[i + 1] = emplist[i];
            }

            //Add book to index
            emplist[pushIndex] = currentEmployee;
        }


	}
	

	/**
     * Prints data for all employees in emplist
     */
    private void printAll()
    {
        for ( int i = 0; i < numEmployee; i++ )
        {
            System.out.println(emplist[i].toString());
        }
    }

	/**
     * Checks if there are employees to print data for 
	 * If there is then it will print all data for all employees
     */
    public void print()
    {

        if ( numEmployee == 0 )
        {
            System.out.println("Employee database is empty.");
            return;
        }

        System.out.println("--Printing earning statements for all employees--");
        printAll();
    }

	/**
     * Prints data for all employees ordered by department
     */
    public void printByDepartment()
    {

        if ( numEmployee == 0 )
        {
            System.out.println("Employee database is empty.");
            return;
        }

        sort(0);
        System.out.println("--Printing earning statements by department--");
        printAll();

    }

	/**
     * Prints data for all employees ordered by dateHired
     */
    public void printByDate()
    {

        if ( numEmployee == 0 )
        {
            System.out.println("Employee database is empty.");
            return;
        }

        sort(1);
        System.out.println("--Printing earning statements by date hired--");
        printAll();

    }

	/**
     * Checks if company has any employees
	 * 
	 * @return 		returns true if the company has 0 employees
	 * 				otherwise it returns false
     */
    public boolean isEmpty()
    {
        return numEmployee == 0;
    }
}
