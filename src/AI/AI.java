package AI;

public class AI {
    /**
     * +Implemented with Singleton design pattern
     *   insuring their is only one AI player.
     * +Using MiniMax to play Tic-Tac-Toc.
     */

    private final int WON_SCORE=10;
    private final int LOSE_SCORE=-10;
    private final int DRAW_SCORE=0;

    private static AI player = null;

    private AI(){}

    public static AI getInstance(){
        if(player == null)
            player = new AI();

        return player;
    }

    public void playOptimally(){
        Board board=Board.getInstance();

        int optimalValue=-1000;
        int row=0,column=0;
        for(int i = 0;i < Board.ROWS; ++i){
            for(int j = 0;j < Board.COLUMNS; ++j){

                if(board.getCell(i,j) == Cell.EMPTY){

                    board.setCell(i,j,Cell.PLAYER2);

                    int currentValue=runMiniMax(board,0,false);

                    board.setCell(i,j,Cell.EMPTY);

                    if (currentValue>optimalValue){
                        row=i;
                        column=j;
                        optimalValue=currentValue;
                    }
                }
            }
            System.out.println();
        }

        board.setCell(row,column,Cell.PLAYER2);
    }

    private int score(Cell cell){
        if(cell == Cell.PLAYER2)
            return WON_SCORE;
        else if(cell == Cell.PLAYER1)
            return LOSE_SCORE;
        return DRAW_SCORE;
    }

    private int calculateHeuristics() {

        // +Calculating heuristics of the current state.

        Board board = Board.getInstance();

        for(int i=0; i<Board.ROWS; ++i) {

            if(board.getCell(i,0) == board.getCell(i,1)
                    && board.getCell(i,1) == board.getCell(i,2)
                    && board.getCell(i,0) != Cell.EMPTY) {

                return score(board.getCell(i,0));

            }


            if(board.getCell(0,i) == board.getCell(1,i)
                    && board.getCell(1,i) == board.getCell(2,i)
                    && board.getCell(0,i) != Cell.EMPTY){

                return score(board.getCell(0,i));
            }
        }

        if(board.getCell(0,0) == board.getCell(1,1)
                && board.getCell(1,1) == board.getCell(2,2)
                && board.getCell(0,0) != Cell.EMPTY){

            return score(board.getCell(0,0));
        }

        if(board.getCell(2,0) == board.getCell(1,1)
                && board.getCell(1,1) == board.getCell(0,2)
                && board.getCell(2,0) != Cell.EMPTY){

            return score(board.getCell(2,0));
        }

        return DRAW_SCORE;
    }

    private int runMiniMax(Board board, int depth, boolean isMaximizer){
        /*
         * +Running Minimizer Maximizer algorithm.
         * +O(b*n) where b is number of legal move at each state
         *   and n in height of the tree.
         */

        int heuristics=calculateHeuristics();

        if(heuristics == WON_SCORE || heuristics == LOSE_SCORE)
            return heuristics;

        if(board.isFull())
            return DRAW_SCORE;

        int current;

        if (isMaximizer){
            current = -1000;

            for(int i = 0;i < Board.ROWS; ++i){
                for(int j = 0;j < Board.COLUMNS; ++j){

                    if(board.getCell(i,j) == Cell.EMPTY){

                        board.setCell(i,j,Cell.PLAYER2);

                        current =Integer.max(current,
                                runMiniMax(board,depth+1,false));

                        board.setCell(i,j,Cell.EMPTY);
                    }
                }
            }
            return current-depth;
        }else{
            current = 1000;

            for(int i = 0;i < Board.ROWS; ++i){
                for(int j = 0;j < Board.COLUMNS; ++j){

                    if(board.getCell(i,j) == Cell.EMPTY){

                        board.setCell(i,j,Cell.PLAYER1);

                        current =Integer.min(current,
                                runMiniMax(board,depth+1,true));

                        board.setCell(i,j,Cell.EMPTY);
                    }
                }
            }
            return current+depth;
        }
    }

    public GameState gameState(){
        int state=calculateHeuristics();

        if(state == WON_SCORE)
            return GameState.LOSE;

        else if(state == LOSE_SCORE)
            return GameState.WON;

        else if(Board.getInstance().isFull())
            return GameState.DRAW;

        return null;
    }
}
