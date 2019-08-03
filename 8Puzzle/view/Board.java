package view;

import java.util.Arrays;

public class Board {
    /**
     * +Implemented with Singleton design pattern
     *   insuring there is only one Board on the game.
     * +Board contains tiles which
     *   by their turn contains number values.
     */

    int boardInt [][];
    static final int ROWS = 3;
    static final int COLUMNS = 3;
    static final int TILESIDE = 60;

    private static Board board = null;

    private Board(){}

    public static Board getInstance(){
        if(board == null){
            board = new Board();

            board.boardInt = new int[ROWS][COLUMNS] ;
            int number = 1;

            for(int i = 0;i < ROWS; ++i){
                for(int j = 0;j < COLUMNS; ++j){

                    if (number == 9){
                        board.boardInt[i][j]= 0;
                        break ;
                    }

                    board.boardInt[i][j]= number;
                    number += 1;

                }
            }
        }
        return board;
    }

    @Override
    public String toString() {
        String strBoard = "";

        strBoard = strBoard.concat(" "+boardInt[0][0]+' '+boardInt[0][1]+' '+boardInt[0][2]+'\n'+
                                    ' '+boardInt[1][0]+' '+boardInt[1][1]+' '+boardInt[1][2]+'\n'+
                                    ' '+boardInt[2][0]+' '+boardInt[2][1]+' '+boardInt[2][2]+'\n');

        return strBoard;
    }

}
