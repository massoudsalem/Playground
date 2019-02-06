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

    @Override
    public void start(Stage primaryStage){

        //Top section
        HBox topSection = new HBox();

        topSection.setSpacing(10);
        topSection.setAlignment(Pos.CENTER);

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

        topSection.getChildren().addAll(scoreLeft,labelLeft,labelRight,scoreRight);



        //set Grid and buttons
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


        //Bottom section
        HBox bottomSection=new HBox();

        bottomSection.setSpacing(10);
        bottomSection.setAlignment(Pos.CENTER);

        Button restartBtn=new Button("Restart");
        Button clearBtn=new Button("Clear score");

        restartBtn.requestFocus();

        restartBtn.getStyleClass().add("bottom-btn");
        clearBtn.getStyleClass().add("bottom-btn");

        bottomSection.getChildren().addAll(restartBtn,clearBtn);


        //separate nodes using VBox
        VBox root = new VBox();

        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        root.getChildren().addAll(topSection,grid,bottomSection);

        //Set Dimensions of the scene
        Scene scene=new Scene(root,300,450);

        //Run and Display scene
        scene.getStylesheets().add("GUI/StyleSheet.css");


        //Add scene to the Stage and bind the title text to it
        primaryStage.setScene(scene);

        //Action for X button of the window
        primaryStage.setOnCloseRequest((WindowEvent event) ->{
                Platform.exit();
                System.exit(0);
        });

        primaryStage.show();

    }
}
