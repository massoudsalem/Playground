package ai;

/**
 * @author Shimaa Hamdy
 */

import java.util.Random;
import java.util.ArrayList;

public class Puzzle implements Comparable<Puzzle> {

    private int[] puzz;   // puzzle need to be solved
    private final int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};  // final goal
    private final int[] INDEX_I = {0, 0, 0, 1, 1, 1, 2, 2, 2}; // get i index in puzzle grid by using its position in normal array
    private final int[] INDEX_J = {0, 1, 2, 0, 1, 2, 0, 1, 2}; //get j index in puzzle grid by using its position in normal array
    private final int GX;  // distance away from original puzzle used in a star
    private final int WX; // total weight of puzzle

    // constructior used in a star//
    public Puzzle(int[] puzz, int GX) {
        this.puzz = puzz;
        this.GX = GX;
        WX = this.GX + getFx();  // calc total weight


    }

    // constructor used in bfs //
    public Puzzle(int[] puzz) {
        this.puzz = puzz;
        GX = 0;  // we dont need GX , WX,FX functions in bfs
        WX = 0;
    }

    // test if puzzle is our Goal
    public boolean isGoal() {
        for (int i = 0; i < 9; ++i) {
            if (puzz[i] != GOAL[i]) return false;
        }
        return true;
    }

    // compare puzzle to goal to calc how far its differnt 
    private int getFx() {
        int h = 0;  // varable store number of non matched boxes
        for (int i = 0; i < 9; ++i) {
            if (puzz[i] == 0) continue;
            if (puzz[i] != GOAL[i]) ++h;
        }
        return h;
    }

    // return puzzle //
    public int[] getPuzzle() {
        return puzz;
    }

    // check where is the blank box
    private int getZeroPosition() {
        for (int i = 0; i < 9; ++i) {
            if (puzz[i] == 0) return i;
        }
        return -1;
    }

    // create new puzzle if we move blank box up
    private int[] getUpChild(int i, int j, int index) {

        int site = (i - 1) * 3 + j;
        int[] temp = new int[9];
        for (int k = 0; k < 9; ++k) temp[k] = puzz[k];
        temp[index] = puzz[site];
        temp[site] = puzz[index];
        return temp;

    }

    // create new puzzle if we move blank box down
    private int[] getDownChild(int i, int j, int index) {

        int site = (i + 1) * 3 + j;
        int[] temp = new int[9];
        for (int k = 0; k < 9; ++k) temp[k] = puzz[k];
        temp[index] = puzz[site];
        temp[site] = 0;
        return temp;


    }

    // create new puzzle if we move blank box left
    private int[] getLeftChild(int i, int j, int index) {

        int site = i * 3 + (j - 1);
        int[] temp = new int[9];
        for (int k = 0; k < 9; ++k) temp[k] = puzz[k];
        temp[index] = puzz[site];
        temp[site] = 0;
        return temp;

    }

    // create new puzzle if we move blank box right
    private int[] getRightChild(int i, int j, int index) {

        int site = i * 3 + (j + 1);
        int[] temp = new int[9];
        for (int k = 0; k < 9; ++k) temp[k] = puzz[k];
        temp[index] = puzz[site];
        temp[site] = 0;
        return temp;

    }

    // create childrens of current puzzle  with astar using weight
    public ArrayList<Puzzle> getPuzzleChildrensAstar() {
        ArrayList<Puzzle> childrens = new ArrayList<>();  // saving new childrens of puzzle 
        int index = getZeroPosition(); // zero pos of current puzzle
        int i = INDEX_I[index]; // get its row index  in grid 
        int j = INDEX_J[index];  // get its colum index in grid

        if (i > 0) childrens.add(new Puzzle(getUpChild(i, j, index), this.GX + 1));
        if (i < 2) childrens.add(new Puzzle(getDownChild(i, j, index), this.GX + 1));
        if (j > 0) childrens.add(new Puzzle(getLeftChild(i, j, index), this.GX + 1));
        if (j < 2) childrens.add(new Puzzle(getRightChild(i, j, index), this.GX + 1));

        return childrens;
    }

    //create childrens of current puzzle  with bfs without using weight
    public ArrayList<Puzzle> getPuzzleChildrensBfs() {
        ArrayList<Puzzle> childrens = new ArrayList<>();
        int index = getZeroPosition();
        int i = INDEX_I[index];
        int j = INDEX_J[index];

        if (i > 0) childrens.add(new Puzzle(getUpChild(i, j, index)));
        if (i < 2) childrens.add(new Puzzle(getDownChild(i, j, index)));
        if (j > 0) childrens.add(new Puzzle(getLeftChild(i, j, index)));
        if (j < 2) childrens.add(new Puzzle(getRightChild(i, j, index)));

        return childrens;
    }

    // create random puzzle
    public void getRandomPuzzle() {
        // fill puzzle with solvable one 
        puzz[0] = 1;
        puzz[1] = 0;
        puzz[2] = 8;
        puzz[3] = 5;
        puzz[4] = 6;
        puzz[5] = 3;
        puzz[6] = 4;
        puzz[7] = 2;
        puzz[8] = 7;
        // move blank space in random way so we create a new one 
        Random rand = new Random();
        int iterations = rand.nextInt(50) + 30; // random number of iteraion betwwen 30 to 50
        for (int k = 0; k < iterations; ++k) {

            int index = getZeroPosition();
            int i = INDEX_I[index];
            int j = INDEX_J[index];

            int choice = rand.nextInt(4); // choose between 4 directions
            if (choice == 0 && i > 0) puzz = getUpChild(i, j, index);
            else if (choice == 1 && i < 2) puzz = getDownChild(i, j, index);
            else if (choice == 2 && j > 0) puzz = getLeftChild(i, j, index);
            else if (j < 2) puzz = getRightChild(i, j, index);
        }
    }


    @Override
    public int compareTo(Puzzle c) {
        return this.WX - c.WX;
    }

    public int[] getPuzz() {
        return puzz;
    }

    @Override
    public String toString() {
        return "puzzle:\n" + puzz[0] + ' ' + puzz[1] + ' ' + puzz[2] + '\n' +
                puzz[3] + ' ' + puzz[4] + ' ' + puzz[5] + '\n' +
                puzz[6] + ' ' + puzz[7] + ' ' + puzz[8] + '\n';
    }
}
