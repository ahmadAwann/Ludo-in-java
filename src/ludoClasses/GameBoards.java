package ludoClasses;

import GUI.LudoGUI;

abstract public class GameBoards{
    //Cell[][] board;

    //abstract public void populateBoard(Cell[] road);
    abstract public void populateBoard();
        // Populate board logic

    public abstract void displayBoard(Player currentplayer, LudoGUI gui);
        // Display board logic
}
