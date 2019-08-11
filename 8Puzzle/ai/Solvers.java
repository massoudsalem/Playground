package ai;

/**
 * @author Shimaa Hamdy
 */

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.PriorityQueue;

public class Solvers {
    // convert puzzle to string 
    public static String stringfy(int[] puzzle) {
        char[] code = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'}; // code used to convert puzzle
        String coded = ""; // new string
        for (int i = 0; i < 9; ++i) {
            coded += code[puzzle[i]];
        }
        return coded;


    }

    // solve puzzle with bfs 
    public static Map<String, int[]> bfs(int[] a) {
        Queue<Puzzle> q = new LinkedList<>(); // q to save current puzzle to traced
        Map<String, int[]> path = new HashMap<>(); // saving path
        Map<String, Boolean> visted = new HashMap<>(); // test if an puzzle was visted before

        q.add(new Puzzle(a));
        visted.put(stringfy(a), Boolean.TRUE);
        while (!q.isEmpty()) {

            Puzzle current = q.poll(); // get first puzzle in q

            if (current.isGoal()) break;


            ArrayList<Puzzle> childrens = current.getPuzzleChildrensBfs(); // create childrens
            for (int i = 0; i < childrens.size(); ++i) {
                if (!visted.containsKey(stringfy(childrens.get(i).getPuzzle()))) // check if it wasnt visted
                {
                    visted.put(stringfy(childrens.get(i).getPuzzle()), Boolean.TRUE);
                    path.put(stringfy(childrens.get(i).getPuzzle()), current.getPuzzle());
                    q.add(childrens.get(i)); // add new children in q
                }
            }
        }
        return path;

    }

    //solve puzzle with a star
    public static Map<String, int[]> aStar(int[] a) {

        PriorityQueue<Puzzle> open = new PriorityQueue<>(); // reorder puzzle acooeding to weight
        Map<String, Boolean> visted = new HashMap<>();
        Map<String, int[]> path = new HashMap<>();

        open.add(new Puzzle(a, 0));
        visted.put(stringfy(a), Boolean.TRUE);
        while (!open.isEmpty()) {
            Puzzle x = open.poll();

            if (x.isGoal()) break;
            ArrayList<Puzzle> childrens = x.getPuzzleChildrensAstar();
            for (int i = 0; i < childrens.size(); ++i) {
                if (!visted.containsKey(stringfy(childrens.get(i).getPuzzle()))) {
                    visted.put(stringfy(childrens.get(i).getPuzzle()), Boolean.TRUE);
                    path.put(stringfy(childrens.get(i).getPuzzle()), x.getPuzzle());
                    open.add(childrens.get(i));
                }
            }
        }
        return path;

    }

}
