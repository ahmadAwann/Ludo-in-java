package ludoClasses;

import GUI.LudoGUI;

public class GameRunner {

    static int turn;
    public void runGame(){
        LudoBoard board = new LudoBoard();

        // now a proper game structure...
        Player[] players = new Player[4];
        //SafeSlots[] safeSlotsArr = new SafeSlots[8];
        //loop for repeated player turns...
        turn = (int)(Math.random() * 4);
        //GameLogic gameLogic = new GameLogic(players,board,safeSlotsArr);
        GameLogic gameLogic = new GameLogic(players,board);
        // initializing board and players...
        gameLogic.initializeBoardAndPlayers();
        // gui
        LudoGUI gui = new LudoGUI();
        board.initializeSlots(gui);
        gui.playerColor.setText(gameLogic.playersArray[turn].getColor());
        gameLogic.playerTurn(gameLogic.playersArray[turn],board,gui);

    }
}
