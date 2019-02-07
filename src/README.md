# Tic-Tac-Toe
 Tic-tac-toe, noughts and crosses or Xs and Os, is a paper-and-pencil game for two players, X and O, who take turns marking the spaces in a 3×3 grid. The player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row wins the game..

<a href="https://appynation.helpshift.com/a/puzzle-page/?s=puzzle-instructions&f=os-and-xs">
  <img src="https://papergames.io/images/tic-tac-toe/thumbnail.png" alt="tic-tac-toc" style="width:225px;border:0;">
</a>
*Click for more details about the game.*

## Technolgies used:
  - *Java* : Backend (MiniMax Algorithm for the AI).
  - *JavaFX* : Frontend (All GUI and threads).
  - *CSS* : Styling the UI.
  - *Adobe Illustrutor* : Desgin images and icons.
 
## Design Pattern Used

  - **MVC** : (Model View Control): divides an application into three interconnected parts.
  - **Singlatone** : Ensure a class has only one instance, and provide a global point of access to it.

## Operations 
### GUI:


<img src="https://i.imgur.com/ZBz2LwN.png" alt="tic-tac-toc" style="width:225px;border:0;">

**Our user interface.**
   1. Controller take an action.
   2. Action sent to backend in AI.
   3. Backend is Called through Threads.
   4. Through view we can react with AI where it could be 
   Used board object to be control adding elements and icons  to the grid,
   whenever Gridpane used to add buttons to set elements on,So we used 
   function updateGrid to binding board with grid,
    also we bind AI backend with labels which scores stricks on,restart and clear buttons.


##  Future Work
- This project would be written again using FXML file.
- An algorithem would be implemented to determine levels would used to play between easy,medium,and difficult.
