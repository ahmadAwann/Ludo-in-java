package ludoClasses;

import GUI.LudoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
    public Player[] playersArray;
    private LudoBoard ludoBoard;
    // class level objects for gui...
    private int diceRollScore;
    int[] turnStore = new int[3];
    //ArrayList<Integer> turnStore = new ArrayList<>();
    int turnScoreIndex = 0, sixCount = 0;
    //token move number storing list...
    ArrayList<Integer> scoreStorer = new ArrayList<>();

    //SafeSlots[] safeSlotsArr;
    //public GameLogic(Player[] playersArray, LudoBoard ludoBoard,SafeSlots[] safeSlotsArr) {
    public GameLogic(Player[] playersArray, LudoBoard ludoBoard) {
        this.playersArray = playersArray;
        this.ludoBoard = ludoBoard;
        //this.safeSlotsArr = safeSlotsArr;
    }

    public GameLogic() {
        // default...
    }

    public void initializeBoardAndPlayers() {
        //System.out.println("Players initialized...");
        // Initialize board and players
        ludoBoard.populateBoard();
        playersArray[0] = new Player("Red");
        // setting the symbols as well...
        playersArray[0].setTokenSymbol();
        playersArray[0].playerSymbol = 'r';
        playersArray[1] = new Player("Green");
        playersArray[1].setTokenSymbol();
        playersArray[1].playerSymbol = 'g';
        playersArray[2] = new Player("Yellow");
        playersArray[2].setTokenSymbol();
        playersArray[2].playerSymbol = 'y';
        playersArray[3] = new Player("Blue");
        playersArray[3].setTokenSymbol();
        playersArray[3].playerSymbol = 'b';

        LudoBoard.safeSlotsArr[0] = new SafeSlots(6, 1);
        LudoBoard.safeSlotsArr[1] = new SafeSlots(6, 12);
        LudoBoard.safeSlotsArr[2] = new SafeSlots(8, 2);
        LudoBoard.safeSlotsArr[3] = new SafeSlots(8, 13);
        LudoBoard.safeSlotsArr[4] = new SafeSlots(2, 6);
        LudoBoard.safeSlotsArr[5] = new SafeSlots(13, 6);
        LudoBoard.safeSlotsArr[6] = new SafeSlots(1, 8);
        LudoBoard.safeSlotsArr[7] = new SafeSlots(12, 8);


    }

    public void playerTurn(Player player, LudoBoard board, LudoGUI gui) {


        // for every time the turn starts, isTokenWon is false, and if token won in any turn, will be set true...
        Dice dice = new Dice();
        player.isTokenWon = false;
        player.isTokenKilled = false;
        scoreStorer.clear();
        turnScoreIndex = 0;
        sixCount = 0;
        diceRollScore = 0;
        gui.currentScoreValue.setText("" + 0);
        gui.scoresStr.setText("" + 0);
        for (int i = 0; i < 3; i++) {
            turnStore[i] = 0;
        }
        //turnStore.clear();
        // clearing old listeners...
        for (ActionListener al : gui.rollDiceBtn.getActionListeners()) {
            gui.rollDiceBtn.removeActionListener(al);
        }
        gui.rollDiceBtn.setEnabled(true);
        Scanner in = new Scanner(System.in);
        //gui.playerColor.setText(player.getColor());
        //while (true) {
        gui.playerColor.setText(player.getColor());
        gui.rollDiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//            System.out.print("\nPlayer " + player.getColor() + ", press Enter for Dice roll:");
//            in.nextLine(); //  for receiving the \n
                String scores = "";
                //diceRollScore = (int) ((Math.random() * 6) + 1);
                diceRollScore = dice.roll();
                gui.currentScoreValue.setText("" + diceRollScore);
                scoreStorer.add(diceRollScore);
//                if (diceRollScore != 6)
//                    gui.rollDiceBtn.setEnabled(false);
                //diceRollScore = (int) ((Math.random() * 6) + 1);
                //System.out.println("Player " + player.getColor() + " Your Score is:" + diceRollScore);
                // setting with gui...
                if (diceRollScore != 6) {
                    turnStore[turnScoreIndex++] = diceRollScore;
                    //turnStore.add(diceRollScore);
                    for (int i = 0; i < turnScoreIndex; i++) {
                        //for (int i = 0; i < turnStore.size(); i++) {
                        //System.out.print(turnStore[i]+" ");
                        scores = scores + turnStore[i] + " ";
                        //scores = scores + turnStore.get(i) + " ";
                    }
                    ////System.out.println(scores); // also connect this string with jLabel ...
                    gui.rollDiceBtn.setEnabled(false);
                    gui.scoresStr.setText(scores); // also connect this string with jLabel ...
                    guiMove(player, board, gui, in);
                    //     }
                    //});

                } else {
                    turnStore[turnScoreIndex++] = diceRollScore;
                    //turnStore.add(diceRollScore);
                    //for (int i = 0; i < turnStore.size(); i++) {
                    for (int i = 0; i < turnScoreIndex; i++) {
                        //System.out.print(turnStore[i]+" ");
                        scores = scores + turnStore[i] + " ";
                        //scores = scores + turnStore.get(i) + " ";
                    }
                    ////System.out.println(scores); // also connect this string with jLabel ...
                    gui.scoresStr.setText(scores); // also connect this string with jLabel ...
                    // if reaching here... it means sixCount ++
                    sixCount++;
                    if (sixCount == 3) {
                        ////System.out.println("Discarded! Three Consecutive Sixes...");
                        JOptionPane.showMessageDialog(gui.blueBackground, "Discarded! Three Consecutive Sixes...");
                        //return;
                        // move next method
                        moveNextTurn(player, board, gui);
                    }
                }
            }
        });
    }

    // here i need to end this method and create a new one for movement logic...
    public void guiMove(Player player, LudoBoard board, LudoGUI gui, Scanner in) {
        // adding int socres to arraylist for popup...
//        for (int i = 0; i < sixCount + 1; i++) {
//            scoreStorer.add(turnStore[i]);
//        }
        //System.out.println("Gui move reached");
        if (player.tokensOnBoard > 1 && player.tokensOnBoard <= 4) {
            for (int k = 0; k < sixCount + 1; k++) {
                diceRollScore = turnStore[k];
                //diceRollScore = turnStore.get(k);
                if (diceRollScore == 6 && player.tokensOnBoard + player.tokensWon < 4) {
                    //System.out.println("choice of move or make token out...");
                    int choice = askPlayerForSix(player, gui, diceRollScore);
                    while (choice == -1) {
                        choice = askPlayerForSix(player, gui, diceRollScore);
                        //System.out.println("Ask Again");
                    }
                    if (choice == 0) {
                        // move token out...
                        for (int i = 0; i < 4; i++) {
                            if (!player.tokensArr[i].isTokenOpen && !player.tokensArr[i].isTokenWon) {
                                player.tokensArr[i].isTokenOpen = true;
                                player.tokensArr[i].move(0, player, ludoBoard, playersArray, gui);
                                break;
                            }
                        }
                        player.tokensOnBoard++;
                        //System.out.println("List after removing six: when token = 2" + scoreStorer);
                        scoreStorer.remove(Integer.valueOf(6));
                        //System.out.println("List after removing six: when token = 2" + scoreStorer);
                        //ludoBoard.displayBoard(player, gui);
                        //System.out.println("Moved token Out...");
                        ludoBoard.updateTokenHomeGUI(player, gui, ludoBoard.redToken, ludoBoard.greenToken, ludoBoard.blueToken,
                                ludoBoard.yellowToken);
                    } else {
                        //System.out.println("diceScore:" + diceRollScore + " list = " + scoreStorer);
                        //player.makeMoves(diceRollScore, player, board, in, playersArray);
                        moveTokensGUI(player, diceRollScore, board, playersArray, gui);
                        //ludoBoard.displayBoard(player, gui);
                        // moving to next player...
                        //moveNextTurn(player, board, gui);
                    }
                } else {
                    //player.makeMoves(diceRollScore, player, board, in, playersArray);
                    moveTokensGUI(player, diceRollScore, board, playersArray, gui);
                    //moveNextTurn(player,board,gui);
                }
                ludoBoard.displayBoard(player, gui);
            }
        } else {
            // condition for if a player has no token on board till now...
            for (int j = 0; j < sixCount + 1; j++) {
                diceRollScore = turnStore[j];
                //diceRollScore = turnStore.get(j);
                if (!player.isOpen && diceRollScore == 6 && player.tokensOnBoard == 0) {
                    //System.out.println("first condition");
                    player.isOpen = true;
                    //  a function for moving the token out...
                    for (int i = 0; i < 4; i++) {
                        if (!player.tokensArr[i].isTokenOpen && !player.tokensArr[i].isTokenWon) {
                            player.tokensArr[i].isTokenOpen = true;
                            player.tokensArr[i].move(0, player, ludoBoard, playersArray, gui);
                            break;
                        }
                    }
                    player.tokensOnBoard++;
                    // removing 6 from array...
                    scoreStorer.remove(Integer.valueOf(6));
                    //System.out.println("player tokens on board:" + player.tokensOnBoard);
                    //player.makeMoves(0, player, ludoBoard, in, playersArray);
                    ludoBoard.updateTokenHomeGUI(player, gui, ludoBoard.redToken, ludoBoard.greenToken, ludoBoard.blueToken,
                            ludoBoard.yellowToken);
                    ludoBoard.displayBoard(player, gui);
                    // before movements, we detected a movement of safe slot, the first move will be on the safe slot...

                }
                // if the tokens on board are 1...
                else if (player.isOpen && !player.isPlayerWon) {
                    //System.out.println("Reached player open");
                    if (diceRollScore == 6) {
                        // make decisions according to the clicked row and col, if clicked token is not open... then
                        // make move for movement, and if is open, then move on board...
                        //System.out.println("choice of move or make token out...");
                        int choice = askPlayerForSix(player, gui, diceRollScore);
                        while (choice == -1) {
                            choice = askPlayerForSix(player, gui, diceRollScore);
                            //System.out.println("Ask Again");
                        }
                        if (choice == 0) {
                            // move token out...
                            for (int i = 0; i < 4; i++) {
                                if (!player.tokensArr[i].isTokenOpen && !player.tokensArr[i].isTokenWon) {
                                    player.tokensArr[i].isTokenOpen = true;
                                    player.tokensArr[i].move(0, player, ludoBoard, playersArray, gui);
                                    break;
                                }
                            }
                            player.tokensOnBoard++;
                            //System.out.println("List before removing six: when token = 1" + scoreStorer);
                            // removing 6 from array...
                            scoreStorer.remove(Integer.valueOf(6));
                            //System.out.println("List after removing six: when token = 1" + scoreStorer);
                            //System.out.println();
                            ludoBoard.updateTokenHomeGUI(player, gui, ludoBoard.redToken, ludoBoard.greenToken, ludoBoard.blueToken,
                                    ludoBoard.yellowToken);
                            ludoBoard.displayBoard(player, gui);
                            //System.out.println("Moved token Out...");
                        } else {
                            //System.out.println("Error Detected!,Roll Dice Score is:" + diceRollScore);
                            moveTokensGUI(player, diceRollScore, board, playersArray, gui);

                        }
                    } else {
                        // automovements...
                        if (player.tokensOnBoard == 1) {
                            moveTokensGUI(player, diceRollScore, board, playersArray, gui);
                            // moving to next player...
                            //moveNextTurn(player, board, gui);
                        } else {
                            gui.currentScoreValue.setText("" + diceRollScore);
                            moveTokensGUI(player, diceRollScore, board, playersArray, gui);
                        }

                    }
                } else { // when player is not open...
                    //ludoBoard.displayBoard(player,gui);
                    moveNextTurn(player, board, gui);
                }
            }

            // displaying the board...
            ludoBoard.displayBoard(player, gui);
            // here displaying the tokens at home for each player...
            displayTokensAtHome();
            //System.out.println("Players token on board:" + player.tokensOnBoard);
            //index++;
            //turnScoreIndex--;

        }
        //System.out.println("Is Win:" + player.isTokenWon + " is kill:" + player.isTokenKilled);
        // here we can do recursion for token win condition...
        if (player.isTokenKilled || (player.isTokenWon && player.tokensWon != 4)) {
            gui.playerColor.setText(player.getColor());
            ////System.out.println(player.isTokenWon ? "Extra turn for Token Win" : "Extra turn for Token kill"); // ternary Operator
            JOptionPane.showMessageDialog(gui.ludoBoardImg, player.isTokenWon ? "Extra turn for Token Win" : "Extra turn for Token kill"); // ternary Operator
            // recursion...
            playerTurn(player, ludoBoard, gui);
        }
        // moving to next player...
        //moveNextTurn(player, board, gui);

    }

    // move next method
    private void moveNextTurn(Player player, LudoBoard board, LudoGUI gui) {
        // delay
        new javax.swing.Timer(  1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((javax.swing.Timer) e.getSource()).stop(); // stops timer...
                GameRunner.turn++;
                if (GameRunner.turn == 4)
                    GameRunner.turn = 0;

                if (isGameOver(player, gui)) {
                    //JOptionPane.showMessageDialog(gui.blueBackground, "Game Over!");
                    // declare winner...
                    String[] options = {"OKAY"};
                    int choice = JOptionPane.showOptionDialog(
                            gui.ludoBoardImg,
                            "Game Over",
                            "Game Over Message",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    if (choice == 0 || choice == -1) {
                        System.exit(0);// terminates the program...
                    }
                    // optiondialog box or use choice here....
                    return;
                }
                while (playersArray[GameRunner.turn].isPlayerWon) {
                    //System.out.println("player " + player.getColor() + " won move forward");
                    GameRunner.turn++;
                }
                // setting the player name...
                gui.playerColor.setText(playersArray[GameRunner.turn].getColor());
                // calling the player turn function...
                playerTurn(playersArray[GameRunner.turn], board, gui);
            }
        }).start();

    }


    // Store the values of dice roll in an array... if equals to six, if consecutive three sixes, discard...
    // else continue the turn

    public boolean isGameOver(Player player, LudoGUI gui) {

        // demo
        //return (player.isTokenKilled || player.isPlayerWon);
        // Game over condition
        if (player.tokensWon == 4) {
            player.isPlayerWon = true;
            JOptionPane.showMessageDialog(gui.ludoBoardImg, "Player :" + player.getColor() + " Won!");
        }
        // for checking how many players won...
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (this.playersArray[i].isPlayerWon)
                count++;
        }
        return (count == 3);
        //return false;
    }

    // method for displaying the tokens at home for all the players...
    private void displayTokensAtHome() {
        //System.out.println("Tokens at Home for each Player:");
        //System.out.println("Red:" + (4 - playersArray[0].tokensOnBoard) + " | Green:" + (4 - playersArray[1].tokensOnBoard) + " | Yellow:"
               // + (4 - playersArray[2].tokensOnBoard) + " | Blue:" + (4 - playersArray[3].tokensOnBoard));
    }

    // function for moving the tokens out...
    public int askPlayerForSix(Player player, LudoGUI gui, int diceRollScore) {
        int choice;
        if (player.canAnotherTokenMove(diceRollScore)) {
            String[] options = {"Bring Token Out", "Move Existing One"};
            choice = JOptionPane.showOptionDialog(gui.ludoBoardImg, "You rolled six!,What would you like to do",
                    "Choose Action", JOptionPane.DEFAULT_OPTION
                    , JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        } else {
            String[] options = {"Bring Token Out"};
            choice = JOptionPane.showOptionDialog(gui.ludoBoardImg, "Can't Move Any Token ! Bring One Out",
                    "Choose Action", JOptionPane.DEFAULT_OPTION
                    , JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }

        return choice;
    }

    // method for moving token using a listener...
    void moveTokensGUI(Player player, int diceRollScore, LudoBoard board, Player[] playersArr, LudoGUI gui) {
        int row = 0, col = 0;
        if (player.tokensOnBoard == 1 || player.tokensWon == 3) {
            // make automovements...
            for (int i = 0; i < 4; i++) {
                if(!player.tokensArr[i].isTokenOpen)
                    continue;
                row = player.tokensArr[i].row;
                col = player.tokensArr[i].col;
                //if (player.tokensArr[i].isTokenOpen && board.board[row][col].isMoveValid(board, row, col, player, diceRollScore, false, playersArr)) {
                if (player.tokensArr[i].getPosition() + diceRollScore <= 56) {
                if(!player.tokensArr[i].isTokenOpen && board.board[row][col].isMoveValid(board, row, col, player, diceRollScore, false, playersArr)){
                    //System.out.println("Mooove not valid!!");
                    JOptionPane.showMessageDialog(gui.ludoBoardImg, "Cannot Move any token! Turn Discarded..");
                    moveNextTurn(player, board, gui);
                }

                    player.tokensArr[i].move(diceRollScore, player, board, playersArr, gui);
                    ludoBoard.displayBoard(player, gui);

                    // now apply your conditional logic
                    if (diceRollScore == 6 || player.isTokenWon || player.isTokenKilled) {
                        //System.out.println("Roll Six or Token Event: Stay in turn.");
                        return; // keep the turn
                    } else {
                        //System.out.println("Roll non-six: Move to next turn.");
                        moveNextTurn(player, board, gui);
                    }

                    break; // done with movement
                }
                else {
                    JOptionPane.showMessageDialog(gui.ludoBoardImg, "Cannot move token, Turn Discarded!!");
                    moveNextTurn(player, board, gui);
                }
                //}
//                else{
//                    JOptionPane.showMessageDialog(gui.ludoBoardImg, "Cannot Move any token! Turn Discarded..");
//                    moveNextTurn(player, board, gui);
//                }
            }
        } else {
            if (!canAnyTokenMove(player, board, diceRollScore, playersArr, scoreStorer)) {
                //System.out.println("Discared !");
                JOptionPane.showMessageDialog(gui.ludoBoardImg, "Cannot Move any token! Turn Discarded..");
                moveNextTurn(player, board, gui);
            }

            animateTokensOnBoard(player, gui); // for animation of button...
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    // Remove old listeners first!
                    for (ActionListener al : gui.slotsArr[i][j].getActionListeners()) {
                        gui.slotsArr[i][j].removeActionListener(al);
                    }
                    int rowIndex = i, colIndex = j;
                    gui.slotsArr[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            // string for popup...
                            //String scoreDisplay = "";
                            String[] options = new String[scoreStorer.size()];
                            for (int i = 0; i < scoreStorer.size(); i++) {
                                options[i] = scoreStorer.get(i).toString();
                                //scoreDisplay = scoreDisplay + scoreStorer.get(i).toString();
                            }
                            // setting to ui...
                            //gui.scoresStr.setText(scoreDisplay);
                            if (player.isSlotValid(rowIndex, colIndex)) {
                                if (board.board[rowIndex][colIndex].tokensOnBulkSlot(board.board, rowIndex, colIndex, player, playersArr) > 1
                                        //&& (player.tokenForMove(rowIndex, colIndex).getPosition() + diceRollScore / board.board[rowIndex][colIndex].tokensOnSlot <= 56)
                                        //&& (diceRollScore % board.board[rowIndex][colIndex].tokensOnSlot == 0 &&
                                        && board.board[rowIndex][colIndex].isMoveValid(board, rowIndex, colIndex, player, diceRollScore, true, playersArr)) {
                                    //)) {
                                    String message = "Slot have " + board.board[rowIndex][colIndex].tokensOnSlot + " tokens";
                                    String[] bulkOptions = {"Move all tokens", "Move One Token"};
                                    int choose = JOptionPane.showOptionDialog(
                                            gui.ludoBoardImg,                            // parent component
                                            message,  // message
                                            "Bulk Options",                      // title
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            bulkOptions,
                                            bulkOptions[1]
                                    );
                                    if (choose == 0) {
                                        // move bulk...
                                        int choice = JOptionPane.showOptionDialog(
                                                gui.ludoBoardImg,                            // parent component
                                                "Choose score to use for this token move:",  // message
                                                "Available Dice Rolls",                      // title
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[0]
                                        );
                                        if (choice >= 0 && choice < scoreStorer.size()) {
                                            // check weather move is possible or not...
                                            if ((scoreStorer.get(choice) % board.board[rowIndex][colIndex].tokensOnSlot == 0 &&
                                                    (player.tokenForMove(rowIndex, colIndex).getPosition() + scoreStorer.get(choice) /
                                                            board.board[rowIndex][colIndex].tokensOnSlot <= 56))) {
                                                player.bulkMove(scoreStorer.get(choice), board, rowIndex, colIndex, player, playersArr, gui);
                                                makingButtonsTransparent(player, gui, rowIndex, colIndex, true);
                                                board.displayBoard(player, gui);
                                                scoreStorer.remove(choice);
                                                if (scoreStorer.isEmpty() && !(player.isTokenWon || player.isTokenKilled)) {
                                                    moveNextTurn(player, board, gui);
                                                } else if ((player.isTokenWon || player.isTokenKilled) && scoreStorer.isEmpty()) {
                                                    JOptionPane.showMessageDialog(gui.ludoBoardImg, "Extra turn for token kill!");
                                                    playerTurn(player, board, gui);
                                                } else {
                                                    makingButtonsTransparent(player, gui, rowIndex, colIndex, true);
                                                    //moveNextTurn(player, board, gui); // for making transparent again;
                                                    // now setting the current slot...
                                                    animateTokensOnBoard(player, gui);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(gui.ludoBoardImg, "Bulk Move not possible! Move single token or Other token");
                                            }
                                        }

                                    } else {
                                        // make normal moves...
                                        int choice = JOptionPane.showOptionDialog(
                                                gui.ludoBoardImg,                            // parent component
                                                "Choose score to use for this token move:",  // message
                                                "Available Dice Rolls",                      // title
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[0]
                                        );
                                        if (choice >= 0 && choice < scoreStorer.size()) {
                                            int rollScore = scoreStorer.get(choice);
                                            // can be done using the tokenForMove Function...
                                            if (player.tokenForMove(rowIndex, colIndex).getPosition() + rollScore <= 56) {
                                                player.tokenForMove(rowIndex, colIndex).move(rollScore, player, board, playersArr, gui);
                                                scoreStorer.remove(choice);
                                                board.displayBoard(player, gui);
                                                if (scoreStorer.isEmpty()) {
                                                    // also reset the listener here...
                                                    makingButtonsTransparent(player, gui, rowIndex, colIndex, true); // for making transparent again;
                                                    if (scoreStorer.isEmpty() && (player.isTokenWon || player.isTokenKilled)) {
                                                        //System.out.println("TokenMOve Or Kill true");
                                                        makingButtonsTransparent(player, gui, rowIndex, colIndex, true);
                                                        moveNextTurn(player, board, gui); // for making transparent again;
                                                        JOptionPane.showMessageDialog(gui.ludoBoardImg, player.isTokenWon ? "Extra turn for win!" : "Extra Turn for Kill!");
                                                        playerTurn(player, board, gui);
                                                        return;
                                                    } else
                                                        moveNextTurn(player, board, gui);
                                                } else {
                                                    // updating rowindex and col index for transparent background...
                                                    makingButtonsTransparent(player, gui, rowIndex, colIndex, false);
                                                    // now setting the current slot...
                                                    animateTokensOnBoard(player, gui);
                                                }
                                                //return true;
                                            } else if (player.canAnotherTokenMove(rollScore)) {
                                                ////System.out.println("Can't Move this Token, Move Another...!");
                                                JOptionPane.showMessageDialog(gui.ludoBoardImg, "Can't Move this Token, Move Another...!");
                                                //return false;
                                            } else {
                                                ////System.out.println("Can't move any token!! Turn Discarded!");
                                                JOptionPane.showMessageDialog(gui.ludoBoardImg, "Can't move any token!! Turn Discarded!");
                                                //return true;
                                                makingButtonsTransparent(player, gui, rowIndex, colIndex, true); // for making transparent again;
                                                moveNextTurn(player, board, gui);
                                            }
                                        } else {
                                            //System.out.println("No selection made...");
                                        }
                                    }

                                } else if (board.board[rowIndex][colIndex].isMoveValid(board, rowIndex, colIndex, player, diceRollScore, false, playersArr)
                                        || player.tokenForMove(rowIndex, colIndex).getPosition() > 50 && player.tokenForMove(rowIndex, colIndex).getPosition() + diceRollScore <= 56) {

                                    int choice = JOptionPane.showOptionDialog(
                                            gui.ludoBoardImg,                            // parent component
                                            "Choose score to use for this token move:",  // message
                                            "Available Dice Rolls",                      // title
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            options,
                                            options[0]
                                    );
                                    if (choice >= 0 && choice < scoreStorer.size()) {
                                        int rollScore = scoreStorer.get(choice);
                                        // can be done using the tokenForMove Function...
                                        if (player.tokenForMove(rowIndex, colIndex).getPosition() + rollScore <= 56) {
                                            player.tokenForMove(rowIndex, colIndex).move(rollScore, player, board, playersArr, gui);
                                            scoreStorer.remove(choice);
                                            //System.out.println("List size:" + scoreStorer.size());
                                            //System.out.println("List contents" + scoreStorer);
                                            board.displayBoard(player, gui);
                                            if (scoreStorer.isEmpty() && !(player.isTokenWon || player.isTokenKilled)) {
                                                // also reset the listener here...
                                                makingButtonsTransparent(player, gui, rowIndex, colIndex, true);
                                                moveNextTurn(player, board, gui); // for making transparent again;
                                            } else if (scoreStorer.isEmpty() && (player.isTokenWon || player.isTokenKilled)) {
                                                //System.out.println("Extra turn confirmed");
                                                //JOptionPane.showMessageDialog(gui.);
                                                makingButtonsTransparent(player, gui, rowIndex, colIndex, true);
                                                moveNextTurn(player, board, gui); // for making transparent again;
                                                JOptionPane.showMessageDialog(gui.ludoBoardImg, player.isTokenWon ? "Extra turn for win!" : "Extra Turn for Kill!");
                                                playerTurn(player, board, gui);
                                            }
//                                            else if(!canAnyTokenMove(player,board,rollScore,playersArr) && !scoreStorer.isEmpty()){
//                                                // if no token can move...
//                                                JOptionPane.showMessageDialog(gui.ludoBoardImg,"Move Another token , or Try Another Score");
//                                                //moveNextTurn(player,board,gui);
//                                            }
                                            else {
                                                // updating rowindex and col index for transparent background...
                                                makingButtonsTransparent(player, gui, rowIndex, colIndex, false);
                                                // now setting the current slot...
                                                animateTokensOnBoard(player, gui);
                                            }
                                            return;
                                            //return true;
                                        } else if (player.canAnotherTokenMove(rollScore)) {
                                            ////System.out.println("Can't Move this Token, Move Another...!");
                                            JOptionPane.showMessageDialog(gui.ludoBoardImg, "Can't Move this Token, Move Another...!");
                                            //return false;
                                        } else {
                                            ////System.out.println("Can't move any token!! Turn Discarded!");
                                            JOptionPane.showMessageDialog(gui.ludoBoardImg, "Can't move any token!! Turn Discarded!");
                                            //return true;
                                            makingButtonsTransparent(player, gui, rowIndex, colIndex, true); // for making transparent again;
                                            moveNextTurn(player, board, gui);
                                        }
                                    } else {
                                        //System.out.println("No selection made...");
                                    }

                                } else {
                                    JOptionPane.showMessageDialog(gui.ludoBoardImg, "Move Not Valid here, Move Another token!");
                                }


                            } else {
                                //System.out.println("Please Enter a Valid Slot!!");
                            }
                        }
                    });
                }
            }
            // because if game is stuck when multiple tokens cannot move, then this will sort  that out...
            // by checking if any tokens can move or not...
            if (!canAnyTokenMove(player, board, diceRollScore, playersArr, scoreStorer)) {
                //System.out.println("Discared !");
                JOptionPane.showMessageDialog(gui.ludoBoardImg, "Cannot Move any token! Turn Discarded..");
                moveNextTurn(player, board, gui);
            }
        }

    }

    // function for tokens effect available for moving
    void animateTokensOnBoard(Player player, LudoGUI gui) {
        // only tokens that are open will have this effect...
        for (int i = 0; i < 4; i++) {
            if (player.tokensArr[i].isTokenOpen) {
                gui.slotsArr[player.tokensArr[i].row][player.tokensArr[i].col].setOpaque(true);
                gui.slotsArr[player.tokensArr[i].row][player.tokensArr[i].col].setContentAreaFilled(true);
                gui.slotsArr[player.tokensArr[i].row][player.tokensArr[i].col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    // for making the button transparent again...
    void makingButtonsTransparent(Player player, LudoGUI gui, int rowIndex, int colIndex, boolean action) {
        // if true make all transparent, else make only one transparent...
        gui.slotsArr[rowIndex][colIndex].setOpaque(false);
        gui.slotsArr[rowIndex][colIndex].setContentAreaFilled(false);
        gui.slotsArr[rowIndex][colIndex].setBackground(null);
        if (action) {
            for (int i = 0; i < 4; i++) {
                gui.slotsArr[player.tokensArr[i].row][player.tokensArr[i].col].setOpaque(false);
                gui.slotsArr[player.tokensArr[i].row][player.tokensArr[i].col].setContentAreaFilled(false);
                gui.slotsArr[player.tokensArr[i].row][player.tokensArr[i].col].setBackground(null);
            }
        }

    }

    //
    public boolean canAnyTokenMove(Player player, LudoBoard board, int diceRollScore, Player[] playersArr, ArrayList<Integer> scoreStorer) {
        int row = 0, col = 0;
        for (int i = 0; i < 4; i++) {
            if (!player.tokensArr[i].isTokenOpen)
                continue;
            row = player.tokensArr[i].row;
            col = player.tokensArr[i].col;
            for (int j = 0; j < scoreStorer.size(); j++) {
                diceRollScore = scoreStorer.get(j);
                if (board.board[row][col].tokensOnBulkSlot(board.board, row, col, player, playersArr) > 1) {
                    if (board.board[row][col].isMoveValid(board, row, col, player, diceRollScore, true, playersArr) && player.tokensArr[i].getPosition() +
                            (diceRollScore / board.board[row][col].tokensOnSlot) <= 56) {
                        //System.out.println("True for bulk");
                        return true;
                    }
                }
                if (board.board[row][col].isMoveValid(board, row, col, player, diceRollScore, false, playersArr) && player.tokensArr[i].getPosition() + diceRollScore <= 56) {
                    //System.out.println("True for simple");
                    return true;
                }

            }


        }
        //System.out.println("Falseee! canot move");
        return false;
    }
}

