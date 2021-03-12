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

    private Stage primaryStage;


    @FXML
    private RadioButton addButton;
    @FXML
    private RadioButton removeButton;
    @FXML
    private RadioButton setButton;
    @FXML
    private RadioButton reorderButton;
    @FXML
    private RadioButton calcButton;
    @FXML
    private AnchorPane firstSet;

    @FXML
    private AnchorPane chooseDepartment;
    @FXML
    private RadioButton CSButton;
    @FXML
    private RadioButton ITButton;
    @FXML
    private RadioButton ECEButton;

    @FXML
    private TextField nameField;
    @FXML
    private TextField dateField;
    @FXML
    private AnchorPane basicInfo;

    @FXML
    private RadioButton addPartTime;
    @FXML
    private RadioButton addFullTime;
    @FXML
    private RadioButton addManagement;
    @FXML
    private AnchorPane whichAdd;

    @FXML
    private AnchorPane hourlyRate;
    @FXML
    private TextField hourlyRateField;


    public void start(Stage primaryStage) throws Exception {
        company = new Company();

        this.primaryStage = primaryStage;
        chooseDepartment.setVisible(false);
        basicInfo.setVisible(false);
        whichAdd.setVisible(false);
        hourlyRate.setVisible(false);
    }

    public void quit(){
        primaryStage.close();
    }


    //first select button action event
    public void firstChoice(){
        if (addButton.isSelected()){
            System.out.println("add");
            addButton.setSelected(false);
            firstSet.setVisible(false);
            chooseDepartment.setVisible(true);

        }
        else if (removeButton.isSelected()){
            System.out.println("remove");
            removeButton.setSelected(false);

        }
        else if (setButton.isSelected()){
            System.out.println("set");
            setButton.setSelected(false);

        }
        else if (reorderButton.isSelected()){
            System.out.println("reorder");
            reorderButton.setSelected(false);

        }
        else if (calcButton.isSelected()){
            System.out.println("calc");
            calcButton.setSelected(false);

        }
    }

    //action event for department choice
    public void departmentChoice(){
        if (CSButton.isSelected()){
            department = CS;
            chooseDepartment.setVisible(false);
            basicInfo.setVisible(true);
        }
        else if (ITButton.isSelected()){
            department = IT;
            chooseDepartment.setVisible(false);
            basicInfo.setVisible(true);

        }
        else if (ECEButton.isSelected()){
            department = ECE;
            chooseDepartment.setVisible(false);
            basicInfo.setVisible(true);
        }
    }

    //action event for basic info e.g. name and date
    public void basicInfoContinue(){

        profile = new Profile(nameField.getText(), department, new Date(dateField.getText()));
        if (profile.isNameValid() && profile.getDateHired().isValid()){
            basicInfo.setVisible(false);
            whichAdd.setVisible(true);
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
            whichAdd.setVisible(false);
            hourlyRate.setVisible(true);
        }
        else if (addFullTime.isSelected()){
            System.out.println("full time employee");

        }
        else if (addManagement.isSelected()){
            System.out.println("management employee");

        }
    }

    //action event to set the hourly rate of a part time worker and add the employee to the company
    public void setHourlyRate(){
        float rate = Float.parseFloat(hourlyRateField.getText());
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


}
