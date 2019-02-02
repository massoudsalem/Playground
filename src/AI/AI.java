package AI;

public class AI {
    /**
     * +Implemented with Singleton design pattern
     *   insuring their is only one AI player.
     */

    private final int WON_SCORE=100;
    private final int LOSE_SCORE=-100;
    private final int DRAW_SCORE=0;

    private static AI player = null;

    private AI(){}

    public static AI getInstance(){
        if(player == null)
            player = new AI();

        return player;
    }

    private int calculateHeuristics(Board board) {

        // +Calculating heuristics of the current state.

        for(int i=0; i<Board.ROWS; ++i){

            if(board.getCell(i,0) == board.getCell(i,1)
                    && board.getCell(i,1) == board.getCell(i,2)){

                if(board.getCell(i,0)== Cell.PLAYER2)
                    return WON_SCORE;
                else
                    return LOSE_SCORE;

            }
        }

        for(int i=0; i<Board.COLUMNS; ++i){

            if(board.getCell(0,i) == board.getCell(1,i)
                    && board.getCell(1,i) == board.getCell(2,i)){

                if(board.getCell(i,0)== Cell.PLAYER2)
                    return WON_SCORE;
                else
                    return LOSE_SCORE;

            }
        }

        if(board.getCell(0,0) == board.getCell(1,1)
                && board.getCell(1,1) == board.getCell(2,2)){

            if(board.getCell(0,0)== Cell.PLAYER2)
                return WON_SCORE;
            else
                return LOSE_SCORE;

        }

        if(board.getCell(0,2) == board.getCell(1,1)
                && board.getCell(1,1) == board.getCell(2,0)){

            if(board.getCell(0,0)== Cell.PLAYER2)
                return WON_SCORE;
            else
                return LOSE_SCORE;

        }


        return DRAW_SCORE;
    }

    private int runMiniMax(Board board, int depth, boolean isMaximizer){
        /*
         * +Running Minimizer Maximizer algorithm.
         * +O(b*n) where b is number of legal move at each state
         *   and n in height of the tree.
         */

        int heuristics=calculateHeuristics(board);

        if(heuristics == WON_SCORE)
            return heuristics;

        if(heuristics == LOSE_SCORE)
            return heuristics;

        if(board.isFull())
            return DRAW_SCORE;

        int current;

        if (isMaximizer){
            current = Integer.MIN_VALUE;

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

        }else{
            current = Integer.MAX_VALUE;

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
        }

        return current;
    }
}
