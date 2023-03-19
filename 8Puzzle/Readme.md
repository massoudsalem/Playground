# 8Puzzles
8-Puzzle is a sliding puzzle that consists of a frame of numbered square tiles in random order with one tile missing.
The puzzle also exists in other sizes, particularly the bigger 15-puzzle.The object of the puzzle is to place the tiles in order
by making sliding moves that use the empty space.

![1_YxeZJzfhW4kn5O5wAGbkIg](https://user-images.githubusercontent.com/53627971/62878654-cbae0900-bd29-11e9-98fc-ad604f9cc7b4.gif)

For more information [Click here](https://en.wikipedia.org/wiki/15_puzzle)
,also for Playing the game by yourself [8-puzzle](https://murhafsousli.github.io/8puzzle/#/)
## Built with
  - *Java* : Backend (Astar and BFS Algorithm for the AI).
  - *JavaFX* : Frontend (All GUI and threads).
  - *CSS* : Styling the UI.
  - *Adobe Illustrutor* : Desgin images and icons.
 
## Design Pattern Used

  - **MVC** : (Model View Control): divides an application into three interconnected parts.
  - **Singlatone** : Ensure a class has only one instance, and provide a global point of access to it.
## Authors
Mohammad Massoud , Shimaa hamdy

## Operations 
### GUI:
![8puzzle](https://user-images.githubusercontent.com/53627971/66707374-b8c2af80-ed3f-11e9-9ea1-0b076a373a4c.gif)



**Our user interface.**
   1. Controller take an action.
   2. Action sent to backend in AI.
   3. Backend is Called through Threads.
   4. Through view we can react with AI,SO in View we could 
   Use random bottom and way you want puzzle to be solved(bfs/a*)
   
   
