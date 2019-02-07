package GUI;

import AI.AI;
import AI.Board;
import AI.Cell;
import AI.GameState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.util.HashMap;



public class View extends Application{

    private GridPane grid;
    private AI player2=AI.getInstance();
    private Board board=Board.getInstance();

    private void printBoard(){
        for(int i = 0;i < Board.ROWS; ++i) {
            for (int j = 0; j < Board.COLUMNS; ++j) {
                if(board.getCell(i,j) == Cell.PLAYER1)
                    System.out.print("X ");
                else if(board.getCell(i,j) == Cell.PLAYER2)
                    System.out.print("O ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
    }

    private void updateGrid(GridPane gridPane){

        Board board=Board.getInstance();

        int idx=0;

        for(int i = 0;i < Board.ROWS; ++i) {
            for (int j = 0; j < Board.COLUMNS; ++j) {

                ImageView x=new ImageView("GUI/IMG/x.png");
                ImageView o=new ImageView("GUI/IMG/o.png");

                if(board.getCell(i,j) == Cell.PLAYER1)
                    ((Button)gridPane.getChildren().get(idx++)).setGraphic(x);


                else if(board.getCell(i,j) == Cell.PLAYER2)
                    ((Button)gridPane.getChildren().get(idx++)).setGraphic(o);

                else
                    ((Button)gridPane.getChildren().get(idx++)).setGraphic(null);
            }
        }
    }

    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        //adding style
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("StyleSheet.css").toExternalForm());


        // Header Text: null
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage){

        //Grid hashmap
        HashMap<Integer,Pair<Integer,Integer>> move=new HashMap<>();
        int k = 0;
        for(int i = 0;i < Board.ROWS; ++i) {
            for (int j = 0; j < Board.COLUMNS; ++j) {
                move.put(k++, new Pair<>(i, j));
            }
        }

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
        grid=new GridPane();
        grid.setHgap(7);
        grid.setVgap(7);
        grid.setAlignment(Pos.CENTER);

        Button button[]=new Button[9];

        for(int i=0;i<9;++i){

            button[i]=new Button();
            button[i].getStyleClass().addAll("center-section");
            button[i].setId("btn-"+(i+1));
            button[i].setOnAction(e -> {
                GameState state=player2.gameState();

                if(player2.gameState() == null) {
                    ((Button) e.getSource())
                            .setGraphic(new ImageView("GUI/IMG/x.png"));

                    Integer y = Integer.parseInt(((Button) e.getSource())
                            .getId().charAt(4) + "") - 1;

                    board.setCell(move.get(y).getKey(),
                            move.get(y).getValue(), Cell.PLAYER1);

                    new Thread(new AsyncFindMoveTask()).start();
                }else{

                }
            });

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

        restartBtn.getStyleClass().add("btn");
        clearBtn.getStyleClass().add("btn");

        restartBtn.setOnAction(e -> {
            showAlert("Title","ok?");
        });

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

    class AsyncFindMoveTask extends Task<Void>{

        @Override
        protected Void call() {
            player2.playOptimally();
            printBoard();
            Platform.runLater(() -> updateGrid(grid));
            return null;
        }
    }
}
