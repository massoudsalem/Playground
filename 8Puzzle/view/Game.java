package view;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ai.*;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.*;

import static view.Board.COLUMNS;
import static view.Board.ROWS;

public class Game extends Application implements Initializable {

    @FXML
    Button tile1,tile2,tile3,tile4,tile5,tile6,tile7,tile8,emptyTile;

    Button arrayTilesFXML[] = new Button[9];

    public static void main(String[] args) {
        launch(args);

    }
    Board board;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("8 Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = Board.getInstance();
        tileInit();
        boardInitiation();
    }

    private void reOrderGrid(Integer [] puzzle){
        tileInit();
        final int TILESIDE = board.TILESIDE;

        int number = 0, curX = 0, curY = 0;
        int intialState[] = new int[9];

        int index = 0;
        for (int i = 0; i < board.boardInt.length; i++) {
            for (int j = 0; j < board.boardInt.length; j++) {
                intialState[index++] = board.boardInt[i][j];
            }
        }

        for(int i = 0;i < ROWS; ++i){

            for(int j = 0; j < COLUMNS; ++j){
                arrayTilesFXML[puzzle[number]].setLayoutX(curX);
                arrayTilesFXML[puzzle[number]].setLayoutY(curY);
                board.boardInt[i][j]=puzzle[number];
                number += 1;
                curX += TILESIDE;
            }

            curY += TILESIDE;
            curX -= TILESIDE*3;

        }
    }

    private void boardInitiation() {
        int itr = 1;
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                if(itr == 9) break;
                arrayTilesFXML[itr].setText(Integer.toString(board.boardInt[i][j]));
                itr++;
            }
        }

    }

    public void moveTile(ActionEvent e){
        Button movmentSource = (Button) e.getSource();

        double emptyX = emptyTile.getLayoutX();
        double emptyY = emptyTile.getLayoutY();

        double sourceX = movmentSource.getLayoutX();
        double sourceY = movmentSource.getLayoutY();
        System.out.println("SourceX :"+ sourceX + " SourceY :" + sourceY);

        double xFromEmptyTile = Math.abs(emptyX - sourceX);
        double yFromEmptyTile = Math.abs(emptyY - sourceY);
        System.out.println("xFromEmptyTile :" + xFromEmptyTile);
        System.out.println("yFromEmptyTile :" + yFromEmptyTile);

        if(xFromEmptyTile == 60 && yFromEmptyTile == 0){
                movmentSource.setLayoutX(emptyX);
                emptyTile.setLayoutX(sourceX);
            int tileNumber = Integer.parseInt(movmentSource.getText());
            swapInt(tileNumber,0);

        }else if(xFromEmptyTile == 0 && yFromEmptyTile == 60){
                movmentSource.setLayoutY(emptyY);
                emptyTile.setLayoutY(sourceY);
            int tileNumber = Integer.parseInt(movmentSource.getText());
            swapInt(tileNumber,0);
        }
        System.out.println(board);
    }

    public void moveTile(Button e){
        Button movmentSource = (Button) e;

        double emptyX = emptyTile.getLayoutX();
        double emptyY = emptyTile.getLayoutY();

        double sourceX = movmentSource.getLayoutX();
        double sourceY = movmentSource.getLayoutY();
        System.out.println("SourceX :"+ sourceX + " SourceY :" + sourceY);

        double xFromEmptyTile = Math.abs(emptyX - sourceX);
        double yFromEmptyTile = Math.abs(emptyY - sourceY);
        System.out.println("xFromEmptyTile :" + xFromEmptyTile);
        System.out.println("yFromEmptyTile :" + yFromEmptyTile);

        if(xFromEmptyTile == 60 && yFromEmptyTile == 0){
                movmentSource.setLayoutX(emptyX);
                emptyTile.setLayoutX(sourceX);
            int tileNumber = Integer.parseInt(movmentSource.getText());
            swapInt(tileNumber,0);

        }else if(xFromEmptyTile == 0 && yFromEmptyTile == 60){
                movmentSource.setLayoutY(emptyY);
                emptyTile.setLayoutY(sourceY);
            int tileNumber = Integer.parseInt(movmentSource.getText());
            swapInt(tileNumber,0);
        }
    }

    private void tileInit(){
        arrayTilesFXML[0] = emptyTile;
        arrayTilesFXML[1] = tile1;
        arrayTilesFXML[2] = tile2;
        arrayTilesFXML[3] = tile3;
        arrayTilesFXML[4] = tile4;
        arrayTilesFXML[5] = tile5;
        arrayTilesFXML[6] = tile6;
        arrayTilesFXML[7] = tile7;
        arrayTilesFXML[8] = tile8;
    }

    public void animate(int [] moves) {
        int wait = 0;
        for(int i : moves) {
            PauseTransition play = new PauseTransition(Duration.millis(wait));
            play.setOnFinished((e) -> {
                moveTile(arrayTilesFXML[i]);
            });
            play.play();
            wait += 200;
        }
    }

    public void swapInt(int first, int second){
        int iFirst=0, jFirst=0, iSecond=0, jSecond=0;
        for(int i = 0;i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
              if(board.boardInt[i][j] == first){
                  iFirst=i;
                  jFirst=j;
              }
              if(board.boardInt[i][j] == second){
                    iSecond=i;
                    jSecond=j;
              }
            }
        }
        int temp = board.boardInt[iFirst][jFirst];
        board.boardInt[iFirst][jFirst]   = board.boardInt[iSecond][jSecond];
        board.boardInt[iSecond][jSecond] = temp;
    }

    public void randamize(ActionEvent e){
        int[] puzzle = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        // randomize puzzle
        Puzzle rand = new Puzzle(puzzle, 0);
        rand.getRandomPuzzle();
        Integer puzzleArray[] = Arrays.stream( rand.getPuzzle() ).boxed().toArray( Integer[]::new );
        reOrderGrid(puzzleArray);
        System.out.println(Arrays.deepToString(puzzleArray));
        System.out.println(board);
    }

    public void solveBFS(ActionEvent e){

        int intialState[] = new int[9];

        int index = 0;
        for (int i = 0; i < board.boardInt.length; i++) {
            for (int j = 0; j < board.boardInt.length; j++) {
                intialState[index++] = board.boardInt[i][j];
            }
        }

        // path save sol
        Map<String, int[]> path = new HashMap<>();
        // our intial state
        path=Solvers.bfs(intialState);
        animate(arrayTracePath(path,intialState));
    }

    public void solveAstar(ActionEvent e){

        int intialState[] = new int[9];

        int index = 0;
        for (int i = 0; i < board.boardInt.length; i++) {
            for (int j = 0; j < board.boardInt.length; j++) {
                intialState[index++] = board.boardInt[i][j];
            }
        }

        // path save sol
        Map<String, int[]> path = new HashMap<>();
        // our intial state
        path=Solvers.aStar(intialState);
        animate(arrayTracePath(path,intialState));
    }

    private int[] arrayTracePath(Map<String, int[]> path, int intialState[]){

        Stack solution = new Stack(); // stack save 2 postion to be swaped in grid
        final int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        // trace path to get right position to be swapped
        while (true) {
            int t = 0;
            int[] parent = path.get(Solvers.stringfy(GOAL));
            int[] child = GOAL;
            for (int i = 0; i < 9; ++i) {
                if (parent[i] == 0) {
                    solution.push(child[i]);
                }
                if (intialState[i] == parent[i]) ++t;
                GOAL[i] = parent[i];
            }
            if (t == 9) break;
        }
        int moves[] = new int[solution.size()];
        int i=0;
        while (!solution.isEmpty()) {
            moves[i++] = (int)solution.pop();
        }
        return moves;
    }

}
