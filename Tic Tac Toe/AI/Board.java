package AI;

public class Board {
    /**
     * +Implemented with Singleton design pattern
     *   insuring there is only one Board on the game.
     * +Board contains cells which
     *   by their turn contains grid values.
     */

    private Cell boardGrid[][];
    public static final int ROWS=3;
    public static final int COLUMNS=3;

    private static Board board = null;

    private Board(){}

    public static Board getInstance(){
        if(board == null){

            board=new Board();

            board.boardGrid = new Cell[ROWS][COLUMNS];

            for(int i = 0;i < ROWS; ++i){
                for(int j = 0;j < COLUMNS; ++j){
                    board.boardGrid[i][j] = Cell.EMPTY;
                }
            }
        }

        return board;
    }

    public void setCell(int row,int column,Cell cell){
        boardGrid[row][column] = cell;
    }

    public Cell getCell(int row,int column){
        return boardGrid[row][column];
    }

    public void clearGrid(){
        // +Fill the grid with empty cells (clearing it)

        for(int i = 0;i < ROWS; ++i){
            for(int j = 0;j < COLUMNS; ++j){
                boardGrid[i][j] = Cell.EMPTY;
            }
        }
    }

    public boolean isFull(){
        // +Check is the board full or not

        for(int i = 0;i < ROWS; ++i){
            for(int j = 0;j < COLUMNS; ++j){
                if(boardGrid[i][j] == Cell.EMPTY)
                    return false;
            }
        }
        return true;
    }


}
