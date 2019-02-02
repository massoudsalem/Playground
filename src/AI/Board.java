package AI;

public class Board {
    private Cell boardGrid[][];
    public static final int ROWS=3;
    public static final int COLUMNS=3;

    Board(){
        boardGrid = new Cell[ROWS][COLUMNS];
        clearGrid();
    }

    public void setCell(int row,int column,Cell cell){
        boardGrid[row][column] = cell;
    }

    public Cell getCell(int row,int column){
        return boardGrid[row][column];
    }

    public void clearGrid(){
        for(int i = 0;i < ROWS; ++i){
            for(int j = 0;j < COLUMNS; ++j){
                boardGrid[i][j] = Cell.EMPTY;
            }
        }
    }

    public boolean isFull(){
        for(int i = 0;i < ROWS; ++i){
            for(int j = 0;j < COLUMNS; ++j){
                if(boardGrid[i][j] == Cell.EMPTY)
                    return false;
            }
        }
        return true;
    }


}
