package Controller;
import GUI.*;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        View view=new View();
        view.start(primaryStage);
    }
}
