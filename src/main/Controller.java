package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import java.io.File;

/**
 * Controls the javafx scene.
 *
 * @author Alexander Xie
 * @author Michael Nguyen
 */

public class Controller
{

    private static final String ECE = "ECE";
    private static final String IT = "IT";
    private static final String CS = "CS";
    private static final int MANAGER = 1;
    private static final int DEPARTMENT_HEAD = 2;
    private static final int DIRECTOR = 3;
    private static final int ADD = 0;
    private static final int REMOVE = 1;
    private static final int SET = 2;
    public static Controller _instance;
    private Company company;
    private String currentOutput = "";
    private String department;
    private Profile profile;
    private Employee employee;
    private boolean isManagement;
    private int managementType;
    private int command;
    private float rate;
    private float salary;

    private Stage primaryStage;

    @FXML
    private AnchorPane firstSet;
    @FXML
    private RadioButton addButton;
    @FXML
    private RadioButton removeButton;
    @FXML
    private RadioButton setButton;
    @FXML
    private RadioButton calcButton;


    @FXML
    private AnchorPane chooseDepartment;
    @FXML
    private RadioButton CSButton;
    @FXML
    private RadioButton ITButton;
    @FXML
    private RadioButton ECEButton;

    @FXML
    private AnchorPane basicInfo;
    @FXML
    private TextField nameField;
    @FXML
    private TextField dateField;

    @FXML
    private AnchorPane whichAdd;
    @FXML
    private RadioButton addPartTime;
    @FXML
    private RadioButton addFullTime;
    @FXML
    private RadioButton addManagement;


    @FXML
    private AnchorPane hourlyRate;
    @FXML
    private TextField hourlyRateField;

    @FXML
    private AnchorPane managementTypePane;
    @FXML
    private RadioButton addManagerButton;
    @FXML
    private RadioButton addDepHeadButton;
    @FXML
    private RadioButton addDirectorButton;

    @FXML
    private AnchorPane salaryPane;
    @FXML
    private TextField salaryField;

    @FXML
    private AnchorPane hoursWorkedPane;
    @FXML
    private TextField hoursField;

    @FXML
    private TextArea outputText;

    /**
     * Prints string with newline to text area.
     *
     * @param string - String to output
     */
    public static void print(String string)
    {
        if ( _instance == null ) return;

        int prevLength = 0;
        if ( !_instance.currentOutput.isEmpty() )
        {
            _instance.currentOutput += "\n";
            prevLength = _instance.currentOutput.length();
        }
        _instance.currentOutput += string;
        _instance.outputText.setText(_instance.currentOutput);
        _instance.outputText.positionCaret(prevLength);
    }

    /**
     * Prints string with newline to text are prepended with "Error: "
     *
     * @param string - String to output
     */
    public static void printError(String string)
    {
        if ( _instance == null ) return;
        int prevLength = 0;
        if ( !_instance.currentOutput.isEmpty() )
        {
            _instance.currentOutput += "\n";
            prevLength = _instance.currentOutput.length();
        }
        _instance.currentOutput += "Error: " + string;
        _instance.outputText.setText(_instance.currentOutput);
        _instance.outputText.positionCaret(prevLength);
    }

    /**
     * Sets up the different panels for the program.
     *
     * @param primaryStage - Stage for the fx.
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception
    {
        company = new Company();

        this.primaryStage = primaryStage;

        chooseDepartment.setVisible(false);
        basicInfo.setVisible(false);
        whichAdd.setVisible(false);
        hourlyRate.setVisible(false);
        managementTypePane.setVisible(false);
        salaryPane.setVisible(false);
        hoursWorkedPane.setVisible(false);
    }

    /**
     * Closes the primary stage
     */
    public void quit()
    {
        primaryStage.close();
    }

    /**
     * The first stage of selections that user can make (add/remove/set)
     */
    public void firstChoice()
    {
        try
        {

            if ( addButton.isSelected() )
            {
                command = ADD;
                addButton.setSelected(false);
                firstSet.setVisible(false);
                chooseDepartment.setVisible(true);

            } else if ( removeButton.isSelected() )
            {
                if ( company.isEmpty() )
                {
                    //output "employee database is empty"
                    print("Employee database is empty");
                    return;
                }
                command = REMOVE;
                removeButton.setSelected(false);
                firstSet.setVisible(false);
                chooseDepartment.setVisible(true);
            } else if ( setButton.isSelected() )
            {
                if ( company.isEmpty() )
                {
                    //output "employee database is empty"
                    print("Employee database is empty");
                    return;
                }
                command = SET;
                setButton.setSelected(false);
                firstSet.setVisible(false);
                chooseDepartment.setVisible(true);
            } else if ( calcButton.isSelected() )
            {
                calcButton.setSelected(false);

                if ( company.isEmpty() )
                {
                    //output "employee database is empty"
                    print("Employee database is empty");
                } else
                {
                    company.processPayments();
                    //output "calculation of employee payments is done"
                    print("Calculation of employee payments is done");
                }
            }

        } catch ( Exception e )
        {
            printError("Unexpected Error for firstChoice");
        }
    }

    /**
     * Action when department choice is required
     */
    public void departmentChoice()
    {
        try
        {
            if ( CSButton.isSelected() )
            {
                department = CS;
                CSButton.setSelected(false);
                chooseDepartment.setVisible(false);
                basicInfo.setVisible(true);
            } else if ( ITButton.isSelected() )
            {
                department = IT;
                ITButton.setSelected(false);
                chooseDepartment.setVisible(false);
                basicInfo.setVisible(true);

            } else if ( ECEButton.isSelected() )
            {
                department = ECE;
                ECEButton.setSelected(false);
                chooseDepartment.setVisible(false);
                basicInfo.setVisible(true);
            }
        } catch ( Exception e )
        {
            printError("Unexpected Error for departmentChoice");
        }
    }

    /**
     * Action to set up for basic info retrieval of the user.
     */
    public void basicInfoContinue()
    {
        try
        {
            profile = new Profile(nameField.getText(), department, new Date(dateField.getText()));
            if ( profile.isNameValid() && profile.getDateHired().isValid() )
            {
                if ( command == ADD )
                {
                    basicInfo.setVisible(false);
                    whichAdd.setVisible(true);
                } else if ( command == REMOVE )
                {
                    basicInfo.setVisible(false);
                    employee = new Employee(profile);
                    if ( company.remove(employee) )
                    {
                        print("Employee was removed");
                    } else
                    {
                        print("Employee could not be removed");
                    }
                    basicInfo.setVisible(false);
                    firstSet.setVisible(true);

                } else if ( command == SET )
                {
                    basicInfo.setVisible(false);
                    hoursWorkedPane.setVisible(true);
                }
                nameField.clear();
                dateField.clear();
            }
            if ( profile.isNameValid() == false )
            {
                print("Name is invalid");
            }
            if ( profile.getDateHired().isValid() == false )
            {
                print("Date is invalid");
            }

        } catch ( Exception e )
        {
            printError("Unexpected Error for basicInfoContinue");
        }

    }

    /**
     * Displays the panel after employee type is selected
     */
    public void addChoice()
    {
        try
        {
            if ( addPartTime.isSelected() )
            {
                addPartTime.setSelected(false);
                whichAdd.setVisible(false);
                hourlyRate.setVisible(true);
            } else if ( addFullTime.isSelected() )
            {
                isManagement = false;
                addFullTime.setSelected(false);
                whichAdd.setVisible(false);
                salaryPane.setVisible(true);

            } else if ( addManagement.isSelected() )
            {
                isManagement = true;
                addManagement.setSelected(false);
                whichAdd.setVisible(false);
                managementTypePane.setVisible(true);
            }
        } catch ( Exception e )
        {
            printError("Unexpected Error for addChoice");
        }
    }

    /**
     * Action event to set the hourly rate of a part time worker and add the employee to the company
     */
    public void setHourlyRate()
    {
        try
        {
            try
            {
                rate = Float.parseFloat(hourlyRateField.getText());
            } catch ( Exception e )
            {
                print("Hourly rate must be a number");
                return;
            }
            if ( rate < 0 )
            {
                print("Parttime hourly rate cannot be negative");
            } else
            {
                employee = new Parttime(profile, rate);
                company.add(employee);

                hourlyRateField.clear();
                hourlyRate.setVisible(false);
                firstSet.setVisible(true);
                print("Parttime employee added");


            }
        } catch ( Exception e )
        {
            printError("Unexpected Error for setHourly");
        }
    }

    /**
     * Action event to select management type
     */
    public void selectManagement()
    {
        try
        {
            if ( addManagerButton.isSelected() )
            {
                managementType = MANAGER;
                addManagerButton.setSelected(false);
                managementTypePane.setVisible(false);
                salaryPane.setVisible(true);
            } else if ( addDepHeadButton.isSelected() )
            {
                managementType = DEPARTMENT_HEAD;
                addDepHeadButton.setSelected(false);
                managementTypePane.setVisible(false);
                salaryPane.setVisible(true);
            } else if ( addDirectorButton.isSelected() )
            {
                managementType = DIRECTOR;
                addDirectorButton.setSelected(false);
                managementTypePane.setVisible(false);
                salaryPane.setVisible(true);
            }
        } catch ( Exception e )
        {
            printError("Unexpected Error for selectManagement");
        }
    }

    /**
     * Action event to set the salary for a full time worker
     */
    public void setSalary()
    {
        try
        {
            try
            {
                salary = Float.parseFloat(salaryField.getText());
            } catch ( Exception e )
            {
                print("Salary must be a number");
                return;
            }
            if ( salary < 0 )
            {
                print("Annual salary cannot be negative");
            } else
            {
                if ( isManagement == false )
                {
                    employee = new Fulltime(profile, salary);
                } else
                {
                    employee = new Management(profile, salary, managementType);
                }

                company.add(employee);
                salaryField.clear();
                salaryPane.setVisible(false);
                firstSet.setVisible(true);
                if ( isManagement == false )
                {
                    print("Fulltime employee added");
                } else
                {
                    if ( managementType == MANAGER )
                    {
                        print("Manager added");
                    } else if ( managementType == DEPARTMENT_HEAD )
                    {
                        print("Department head added");
                    } else if ( managementType == DIRECTOR )
                    {
                        print("Director added");
                    }
                }
            }
        } catch ( Exception e )
        {
            printError("Unexpected Error for setSalary");
        }
    }

    /**
     * Action event for setting hours of a part time employee
     */
    public void setHours()
    {
        try
        {
            int hours;
            try
            {
                hours = Integer.parseInt(hoursField.getText());
            } catch ( Exception e )
            {
                print("Hours must be a number");
                return;
            }
            if ( hours < 0 )
            {
                print("Hours cannot be negative");
            } else if ( hours > 100 )
            {
                print("Amount of hours cannot exceed 100 per pay period");
            } else
            {
                Parttime parttime = new Parttime(profile, 0);
                parttime.setHours(hours);
                company.setHours(parttime); //check if the hours have been set and output something
                hoursField.clear();
                hoursWorkedPane.setVisible(false);
                firstSet.setVisible(true);

                print("Hours set to " + hours);
            }
        } catch ( Exception e )
        {
            printError("Unexpected Error for setHours");
        }
    }

    /**
     * Prints all the employees to text area
     */
    public void printEmployees()
    {
        try
        {
            company.print();
        } catch ( Exception e )
        {
            printError("Unexpected Error for printEmployees");
        }
    }

    /**
     * Prints all the employees sorted by department to the text area.
     */
    public void printByDepartment()
    {
        try
        {
            company.printByDepartment();
        } catch ( Exception e )
        {
            printError("Unexpected Error for printByDepartment");
        }
    }

    /**
     * Prints all the employees sorted by date to the text area.
     */
    public void printByDate()
    {
        try
        {
            company.printByDate();
        } catch ( Exception e )
        {
            printError("Unexpected Error for printByDate");
        }
    }

    /**
     * Imports the database using database utility and file chooser
     *
     * @param action - Used to grab stage to show filechooser
     */
    public void importDatabase(ActionEvent action)
    {
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select database to import");

            Stage stage = ( Stage ) (( Node ) action.getSource()).getScene().getWindow();
            File file = chooser.showOpenDialog(stage);

            if ( file == null )
            {
                print("Import aborted");
                return;
            }

            DatabaseUtility.Import(file, company);
        } catch ( Exception e )
        {
            printError("Unexpected Error for importDatabase");
        }
    }

    /**
     * Exports the database using database utility and file chooser
     *
     * @param action - Used to grab stage to show filechooser
     */
    public void exportDatabase(ActionEvent action)
    {
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select database export file");

            Stage stage = ( Stage ) (( Node ) action.getSource()).getScene().getWindow();

            File file = chooser.showSaveDialog(stage);

            if ( file == null )
            {
                print("Export aborted");
                return;
            }

            DatabaseUtility.Export(file, company);
        } catch ( Exception e )
        {
            printError("Unexpected Error for exportDatabase");
        }
    }

}
