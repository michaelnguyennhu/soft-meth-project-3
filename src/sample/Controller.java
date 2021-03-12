package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

public class Controller {

    public Company company;
    private static final String ECE = "ECE";
    private static final String IT = "IT";
    private static final String CS = "CS";
    private String department;
    private Profile profile;
    private Employee employee;
    private boolean isManagement;
    private int managementType;
    private static final int MANAGER = 1;
    private static final int DEPARTMENT_HEAD = 2;
    private static final int DIRECTOR = 3;
    private int command;
    private static final int ADD = 0;
    private static final int REMOVE = 1;
    private static final int SET = 2;


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


    public void start(Stage primaryStage) throws Exception {
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

    public void quit(){
        primaryStage.close();
    }


    //first select button action event
    public void firstChoice(){
        if (addButton.isSelected()){
            System.out.println("add");
            command = ADD;
            addButton.setSelected(false);
            firstSet.setVisible(false);
            chooseDepartment.setVisible(true);

        }
        else if (removeButton.isSelected()){
            System.out.println("remove");
            command = REMOVE;
            removeButton.setSelected(false);
            firstSet.setVisible(false);
            chooseDepartment.setVisible(true);
        }
        else if (setButton.isSelected()){
            System.out.println("set");
            command = SET;
            setButton.setSelected(false);
            firstSet.setVisible(false);
            chooseDepartment.setVisible(true);


        }
        else if (calcButton.isSelected()){
            System.out.println("calc");
            calcButton.setSelected(false);

            if (company.isEmpty()){
                //output "employee database is empty"
            }
            else {
                company.processPayments();
                //output "calculation of employee payments is done"
            }
        }
    }

    //action event for department choice
    public void departmentChoice(){
        if (CSButton.isSelected()){
            department = CS;
            CSButton.setSelected(false);
            chooseDepartment.setVisible(false);
            basicInfo.setVisible(true);
        }
        else if (ITButton.isSelected()){
            department = IT;
            ITButton.setSelected(false);
            chooseDepartment.setVisible(false);
            basicInfo.setVisible(true);

        }
        else if (ECEButton.isSelected()){
            department = ECE;
            ECEButton.setSelected(false);
            chooseDepartment.setVisible(false);
            basicInfo.setVisible(true);
        }
    }

    //action event for basic info e.g. name and date
    public void basicInfoContinue(){

        profile = new Profile(nameField.getText(), department, new Date(dateField.getText()));
        if (profile.isNameValid() && profile.getDateHired().isValid()){
            if (command == ADD) {
                basicInfo.setVisible(false);
                whichAdd.setVisible(true);
            }
            else if (command == REMOVE){
                basicInfo.setVisible(false);
                employee = new Employee(profile);
                company.remove(employee); // this line needs to output if the employee was removed
                basicInfo.setVisible(false);
                firstSet.setVisible(true);

                company.print();
            }
            else if (command == SET){
                basicInfo.setVisible(false);
                hoursWorkedPane.setVisible(true);
            }
            nameField.clear();
            dateField.clear();
        }
        else if (profile.isNameValid() == false){
            nameField.setText("Name is invalid");
        }
        else if (profile.getDateHired().isValid() == false){
            dateField.setText("Date is invalid");
        }


    }

    //action event to choose which type of employee to add
    public void addChoice(){
        if (addPartTime.isSelected()){
            System.out.println("part time employee");
            addPartTime.setSelected(false);
            whichAdd.setVisible(false);
            hourlyRate.setVisible(true);
        }
        else if (addFullTime.isSelected()){
            System.out.println("full time employee");
            isManagement = false;
            addFullTime.setSelected(false);
            whichAdd.setVisible(false);
            salaryPane.setVisible(true);

        }
        else if (addManagement.isSelected()){
            System.out.println("management employee");
            isManagement = true;
            addManagement.setSelected(false);
            whichAdd.setVisible(false);
            managementTypePane.setVisible(true);
        }
    }

    //action event to set the hourly rate of a part time worker and add the employee to the company
    public void setHourlyRate(){
        rate = Float.parseFloat(hourlyRateField.getText());
        if (rate < 0){
            hourlyRateField.setText("Parttime hourly rate cannot be negative");
        }
        else {
            employee = new Parttime(profile, rate);
            company.add(employee);

            hourlyRateField.clear();
            hourlyRate.setVisible(false);
            firstSet.setVisible(true);


            company.print();
        }
    }

    //action event to select management type
    public void selectManagement(){
        if (addManagerButton.isSelected()){
            managementType = MANAGER;
            addManagerButton.setSelected(false);
            managementTypePane.setVisible(false);
            salaryPane.setVisible(true);
        }
        else if (addDepHeadButton.isSelected()){
            managementType = DEPARTMENT_HEAD;
            addDepHeadButton.setSelected(false);
            managementTypePane.setVisible(false);
            salaryPane.setVisible(true);
        }
        else if (addDirectorButton.isSelected()){
            managementType = DIRECTOR;
            addDirectorButton.setSelected(false);
            managementTypePane.setVisible(false);
            salaryPane.setVisible(true);
        }
    }

    //action event to set the salary for a full time worker
    public void setSalary(){
        salary = Float.parseFloat(salaryField.getText());
        if (salary < 0){
            salaryField.setText("Fulltime annual salary cannot be negative");
        }
        else {
            if (isManagement == false) {
                employee = new Fulltime(profile, salary);
            }
            else {
                employee = new Management(profile, salary, managementType);
            }

            company.add(employee);
            salaryField.clear();
            salaryPane.setVisible(false);
            firstSet.setVisible(true);
        }
    }

    public void setHours(){
        int hours = Integer.parseInt(hoursField.getText());
        if (hours < 0){
            hoursField.setText("Hours cannot be negative");
        }
        else if (hours > 100){
            hoursField.setText("Amount of hours cannot exceed 100 per pay period");
        }
        else {
            Parttime parttime = new Parttime(profile, 0);
            parttime.setHours(hours);
            company.setHours(parttime); //check if the hours have been set and output something
            hoursField.clear();
            hoursWorkedPane.setVisible(false);
            firstSet.setVisible(true);
            company.print();

        }
    }

    public void printEmployees(){

    }

    public void printByDepartment(){

    }

    public void printByDate(){

    }

    public void importDatabase(){

    }

    public void exportDatabase(){

    }

}
