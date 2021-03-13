package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/sample.fxml"));
        GridPane root = loader.load();

        Controller controller = loader.getController();
        controller.start(primaryStage);
        Controller._instance = controller;

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Payroll Processing System");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
