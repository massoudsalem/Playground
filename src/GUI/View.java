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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.util.HashMap;



public class View extends Application{

    private GridPane grid;
    private AI player2=AI.getInstance();
    private Board board=Board.getInstance();

    private void updateGrid(GridPane gridPane){


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

        //Remove icon
        alert.initStyle(StageStyle.UTILITY);

        //adding style
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("StyleSheet.css").toExternalForm());


        // Header Text: null and inner Icon:null
        alert.setHeaderText(null);
        alert.setGraphic(null);

        //Set msg
        alert.setContentText(msg);

        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage){

        //Grid HashMap
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

        Label xScore= new Label();

        Label xImgLabel = new Label();
        Label oImgLabel = new Label();

        Label oScore = new Label();

        ImageView x=new ImageView("GUI/IMG/x.png");
        ImageView o=new ImageView("GUI/IMG/o.png");


        //Adding labels properties.
        x.setFitWidth(50);
        x.setFitHeight(50);

        o.setFitWidth(60);
        o.setFitHeight(60);

        xImgLabel.setAlignment(Pos.CENTER);
        oImgLabel.setAlignment(Pos.CENTER);

        xImgLabel.setGraphic(x);
        oImgLabel.setGraphic(o);

        xImgLabel.getStyleClass().add("top-section-op");
        oImgLabel.getStyleClass().add("top-section-op");

        xImgLabel.setId("current-player");

        xScore.getStyleClass().add("top-section-score");
        oScore.getStyleClass().add("top-section-score");

        xScore.setText("0");
        oScore.setText("0");

        xScore.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK,4,0,0,0));
        oScore.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK,4,0,0,0));

        //Stick components to
        topSection.getChildren().addAll(xScore,xImgLabel,oImgLabel,oScore);



        //Creating Tic-Tac-Toc grid
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

                /*
                 * +If the cell occupied do nothing.
                 * +Set cell graphic "image" to X.
                 * +Parse button number.
                 *   and Set Cell occupied in the board obj.
                 * +Run a background thread to get the AI Move
                 *   and wait till it update board obj.
                 * +Someone win (which is the AI BTW) or draw
                 *   MsgBox a state of the game and update score.
                 */

                Button me=(Button)e.getSource();

                if(me.getGraphic()!=null)
                    return;


                me.setGraphic(new ImageView("GUI/IMG/x.png"));

                Integer y = Integer.parseInt(me.getId().charAt(4) + "") - 1;
                board.setCell(move.get(y).getKey(),
                            move.get(y).getValue(), Cell.PLAYER1);


                Thread findMove= new Thread(new AsyncFindMoveTask());
                findMove.start();
                try {
                    findMove.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                GameState state = player2.gameState();

                if(state != null){
                    if(state == GameState.LOSE) {
                        showAlert("MessageBox",
                                "Unfortunately, You lose the game.");

                        oScore.setText(""+
                                (Integer.parseInt(oScore.getText())+1));
                    }
                    else if(state == GameState.WON) {
                        showAlert("MessageBox",
                                "Congrats, You won the game.");

                        xScore.setText(""+
                                (Integer.parseInt(xScore.getText())+1));
                    }else{
                        showAlert("MessageBox",
                                "Draw.");
                    }
                    board.clearGrid();
                    updateGrid(grid);
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
            board.clearGrid();
            updateGrid(grid);
            showAlert("Restart","Grid is cleared but score is still.");
        });
        clearBtn.setOnAction(e -> {
            board.clearGrid();
            updateGrid(grid);
            xScore.setText("0");
            oScore.setText("0");
            showAlert("Restart","Score is cleared.");
        });

        bottomSection.getChildren().addAll(restartBtn,clearBtn);


        //separate nodes using VBox
        VBox root = new VBox();

        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        root.getChildren().addAll(topSection,grid,bottomSection);


        //Set Dimensions of the scene
        Scene scene=new Scene(root,300,450);

        //Adding the CSS Stylesheet
        scene.getStylesheets().add("GUI/StyleSheet.css");

        //Add scene to the Stage and bind the title text to it
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toc");
        //Action for X button of the window
        primaryStage.setOnCloseRequest((WindowEvent event) ->{
                Platform.exit();
                System.exit(0);
        });

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("GUI/IMG/icon.png"));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    class AsyncFindMoveTask extends Task<Void>{

        @Override
        protected Void call() {
            player2.playOptimally();
            Platform.runLater(() -> updateGrid(grid));
            return null;
        }
    }
}
