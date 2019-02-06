package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class View extends Application{

   //create a function to set grid to set buttons on
    private GridPane createGrid(){
        GridPane grid=new GridPane();
        grid.setHgap(7);
        grid.setVgap(7);
        grid.setAlignment(Pos.CENTER);

        Button button[]=new Button[9];

        for(int i=0;i<9;++i){

            button[i]=new Button();
            button[i].getStyleClass().addAll("center-section");
            button[i].setId("btn-"+(i+1));

        }

        int curr=0;

        for(int i=0;i<3;++i){
            for(int j=0;j<3;++j){

                grid.add(button[curr++],j,i);

            }
        }
        return grid;

    }

    //A function to create horizontal buttons
    // to indicate numbers of each element
    private HBox topSection(){

        HBox hbox = new HBox();

        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        Label scoreLeft= new Label();

        Label labelLeft = new Label();
        Label labelRight = new Label();

        Label scoreRight = new Label();

        ImageView x=new ImageView("GUI/IMG/x.png");
        ImageView o=new ImageView("GUI/IMG/o.png");

        x.setFitWidth(50);
        x.setFitHeight(50);

        o.setFitWidth(60);
        o.setFitHeight(60);

        labelLeft.setAlignment(Pos.CENTER);
        labelRight.setAlignment(Pos.CENTER);

        labelLeft.setGraphic(x);
        labelRight.setGraphic(o);

        labelLeft.getStyleClass().add("top-section-op");
        labelRight.getStyleClass().add("top-section-op");

        labelLeft.setId("current-player");

        scoreLeft.getStyleClass().add("top-section-score");
        scoreRight.getStyleClass().add("top-section-score");

        scoreLeft.setText("0");
        scoreRight.setText("0");

        scoreLeft.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK,4,0,0,0));
        scoreRight.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK,4,0,0,0));

        hbox.getChildren().addAll(scoreLeft,labelLeft,labelRight,scoreRight);

        return hbox;

    }

    // Creating function of bottom section
    private HBox bottomSection(){
        HBox hBox1=new HBox();

        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);

        Button restartBtn=new Button("Restart");
        Button clearBtn=new Button("Clear score");

        restartBtn.requestFocus();

        restartBtn.getStyleClass().add("bottom-btn");
        clearBtn.getStyleClass().add("bottom-btn");

        hBox1.getChildren().addAll(restartBtn,clearBtn);

        return hBox1;
    }

    //Creating function of Scene
    private Scene createScene(){
        //separate nodes using VBox
        VBox root = new VBox();

        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        root.getChildren().addAll(topSection(),createGrid(),bottomSection());

        //Set Dimensions of the scene
        Scene scene=new Scene(root,300,450);

        //Run and Display scene
        scene.getStylesheets().add("GUI/StyleSheet.css");

        return scene;
    }

    @Override
    public void start(Stage primaryStage){


        //Add scene to the Stage and bind the title text to it
        primaryStage.setScene(createScene());

        //Action for X button of the window
        primaryStage.setOnCloseRequest((WindowEvent event) ->{
                Platform.exit();
                System.exit(0);
        });

        primaryStage.show();

    }
}
