package AI;

import javafx.util.*;

import java.util.HashMap;
import java.util.Scanner;

public class ConsoleTest {

     private static void printBoard(Board board){
        for(int i = 0;i < Board.ROWS; ++i) {
            for (int j = 0; j < Board.COLUMNS; ++j) {
                if(board.getCell(i,j) == Cell.PLAYER1)
                    System.out.print("O ");
                else if(board.getCell(i,j) == Cell.PLAYER2)
                    System.out.print("X ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        HashMap<Integer,Pair<Integer,Integer>> move=new HashMap<>();
        int x = 0;
        for(int i = 0;i < Board.ROWS; ++i) {
            for (int j = 0; j < Board.COLUMNS; ++j) {
                move.put(++x, new Pair<>(i, j));
            }
        }

        AI computer=AI.getInstance();
        Board board= Board.getInstance();

        Scanner input=new Scanner(System.in);
        while (!board.isFull()){
            printBoard(board);
            x=input.nextInt();
            board.setCell(move.get(x).getKey(),move.get(x).getValue(),Cell.PLAYER1);
            board=computer.playOptimally(board);
        }
    }
}
