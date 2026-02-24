package ludoClasses;

import GUI.LudoGUI;

import javax.swing.*;
import java.util.Scanner;
import java.util.jar.JarOutputStream;

public class Token implements GameTokens {
    private int position; // for playerRoad Array...
    private String color;
    private char tokenSymbol;
    private boolean isTokenHome;
    boolean isTokenWon;
    private boolean isKilled; // will make this true of the token killed...
    private boolean isInitialized;
    static int tokensOut = 0;
    boolean isTokenOpen;
    int row; // for board array index...
    int col;


    public void setTokenOpen(boolean tokenOpen) {
        isTokenOpen = tokenOpen;

    }

    public Token() {
    }

    public Token(char tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public char getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(char tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public boolean isHome() {
        return isTokenHome;
    }

    public boolean isWon() {
        return isTokenWon;
    }

    @Override
    public void move(int spaces, Player player, LudoBoard ludoBoard, Player[] playersArr, LudoGUI gui) {

        // here before moving, checking if the token is on safe slot then remove it from that list of safe slot...
        if (player.playerRoad[position].isSafeCell(this.row, this.col)) {
            //System.out.println("this row:" + this.row + " col:" + this.col + " safe:" + player.playerRoad[position].isSafeCell(this.row, this.col));
            ludoBoard.addRemoveFromSafeSlot(this.row, this.col, player.tokensArr[0].tokenSymbol, false);
            // false for removing from the function
            //System.out.println("Removed from safe slot list...");
        }
        // also checking if the token score is less than 56, allow move, else don't, if = 56, make it win...
        //if (this.position + spaces <= 56) {
            // if less, allowed to make move
            if (this.position + spaces == 56) {
                //System.out.println("Player:" + player.getColor() + ", Your Token Won!!");
                //JOptionPane.showMessageDialog(gui.ludoBoardImg,"Player:" + player.getColor() + ", Extra turn For token win");
                player.tokensWon++;
                player.tokensOnBoard--; // for auto movements
                if(player.tokensOnBoard == 0){
                    player.isOpen = false;
                }
                //int tokensWon = Integer.parseInt(gui.greenTokensWonNum.getText());
                //tokensWon++;
                if(player.getColor().equals("Red"))
                    gui.redTokensWonNum.setText(""+player.tokensWon);
                else if(player.getColor().equals("Green"))
                    gui.greenTokensWonNum.setText(""+player.tokensWon);
                else if(player.getColor().equals("Yellow"))
                    gui.yellowTokensWonNum.setText(""+player.tokensWon);
                else if(player.getColor().equals("Blue"))
                    gui.blueTokensWonNum.setText(""+player.tokensWon);
                player.isTokenWon = true;// for extra turn...
                //System.out.println("Player.istokenWon?:"+player.isTokenWon);
                // also reseting the playerRoad slot on which the token was...
                player.playerRoad[position].setSymbol(' ');
                resetToken();
                this.isTokenWon = true;
                return;
            }
            // else the movement will be made automatically...
        //}
//        else {
//            // check if any other token can move or not...
//            // method for checking...
//
//        }
        // if not won, then this code will run...
        this.position += spaces;
        ////System.out.println("Position:"+position + ",Symbol :" + this.tokenSymbol+",isTokenOpen:"+isTokenOpen);
        // here a loop for placing all the tokens for playerRoad array...
        //for(int i = 0;i<4;i++){
        if (this.isTokenOpen) {
            ////System.out.println("Token open is at index:"+i);
            // inner loop for putting spaces...
            for (int j = 0; j < 56; j++) {
                if (this.position == j) {
                    ////System.out.println("position matched at j:"+j);
                    player.playerRoad[this.position].setSymbol(this.tokenSymbol);
                    ////System.out.println("after setting symbol, Player road is:");
//                        for(int k =0;k<56;k++){
//                            //System.out.println("Index:"+k+", value:"+player.playerRoad[k]);
//                        }
                    // here i will call the method, setRowColumn...
                    setRowColumn(this.position, player, ludoBoard);
                    // here after setting the row and column, we check if it is a safe slot or not
                    // on which the token is placed...
                    // if safe , then add them to the safe slot of the row and column... else continue...
                    //break; // the inner loop...
                } else {
                    // if(!player.playerRoad[j].isSafeCell(j))
                    if (!isTokenExists(j, player) || (isTokenExists(j, player) && player.playerRoad[j].isSafeCell(j)))
                        player.playerRoad[j].setSymbol(' ');
                    // here we also need to imply the list logic for safe slot...
                }
            }
        }
        //System.out.println("Move reached !!");

        //}
        if (player.playerRoad[position].isSafeCell(this.row, this.col)) {
            // after setting the row and col values.... adding them to their respective lists if son safe slots....
            ludoBoard.addRemoveFromSafeSlot(this.row, this.col, player.tokensArr[0].tokenSymbol, true); // because if tokensArr[0] will always be initialized...
        }
        if(ludoBoard.board[this.row][this.col].isCellKill(ludoBoard.board, this.row,this.col,player.playerSymbol)
        && !player.playerRoad[position].isSafeCell(this.row, this.col)){
            //System.out.println("reaching Condition kill!!");
            // here we will check for kill cell
            this.killMethod(ludoBoard, this.row,this.col,player.playerSymbol,playersArr,player,gui);
            player.isTokenKilled = true ; // for extra turn...

            // setting the gui...


        }
        if(ludoBoard.board[this.row][this.col].tokensOnBulkSlot(ludoBoard.board, this.row,this.col,player,playersArr)>= 0){
            //System.out.println("Reaching bulk condition......");
            ludoBoard.setBulkSlotsGUI(player,gui,true);
            // now for removing previous slots gui...
            ludoBoard.setBulkSlotsGUI(player,gui,false);
            // clearing the previous safe labels...
            ludoBoard.clearAllSafeSlotLabels(gui);
            // setting the new labels....
            ludoBoard.setSafeSlotsGUI(gui);
        }
        // setting the safe slots...


    }
    // method for assigning the row and column values...
    public void setRowColumn(int position, Player currentPlayer, LudoBoard board) {
        // if Red Player
        if (currentPlayer.getColor().equals("Red")) {
            // loop for setting..
            int redIndex = 0;
            for (int col = 1; col < 6; col++) {
                if (position == redIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            for (int row = 5; row >= 0; row--) {
                if (position == redIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            // condition check for here as well...
            if (position == redIndex++) {
                this.row = 0;
                this.col = 7;
                return;
            }
            // single
            //board[0][7].setCell(currentPlayer.playerRoad[redIndex++]);
            for (int row = 0; row < 6; row++) {
                if (position == redIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            for (int col = 9; col < 15; col++) {
                if (position == redIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            //
            if (position == redIndex++) {
                this.row = 7;
                this.col = 14;
                return;
            }
            // single
            //board[7][14].setCell(currentPlayer.playerRoad[redIndex++]);
            for (int col = 14; col > 8; col--) {
                if (position == redIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            for (int row = 9; row < 15; row++) {
                if (position == redIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            //
            if (position == redIndex++) {
                this.row = 14;
                this.col = 7;
                return;
            }
            // single
            //board[14][7].setCell(currentPlayer.playerRoad[redIndex++]);
            for (int row = 14; row > 8; row--) {
                if (position == redIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            for (int col = 5; col >= 0; col--) {
                if (position == redIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[redIndex++]);
            }
            // one deleted...
            for (int col = 0; col < 6; col++) {
                if (position == redIndex++) {
                    this.row = 7;
                    this.col = col;
                    return;
                }
//                if(currentPlayer.playerRoad[redIndex].symbol == 'r')
//                    board[7][col].setCell(currentPlayer.playerRoad[redIndex]);
//                redIndex++;
            }
        } else if (currentPlayer.getColor().equals("Green")) {
            // loop for setting..
            int greenIndex = 0;
            for (int row = 1; row < 6; row++) {
                if (position == greenIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            for (int col = 9; col < 15; col++) {
                if (position == greenIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            //
            if (position == greenIndex++) {
                this.row = 7;
                this.col = 14;
                return;
            }
            // single
            //board[7][14].setCell(currentPlayer.playerRoad[greenIndex++]);
            for (int col = 14; col > 8; col--) {
                if (position == greenIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            for (int row = 9; row < 15; row++) {
                if (position == greenIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            //
            if (position == greenIndex++) {
                this.row = 14;
                this.col = 7;
                return;
            }
            // single
            //board[14][7].setCell(currentPlayer.playerRoad[greenIndex++]);
            for (int row = 14; row > 8; row--) {
                if (position == greenIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            for (int col = 5; col >= 0; col--) {
                if (position == greenIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            //
            if (position == greenIndex++) {
                this.row = 7;
                this.col = 0;
                return;
            }
            // single
            //board[7][0].setCell(currentPlayer.playerRoad[greenIndex++]);
            for (int col = 0; col < 6; col++) {
                if (position == greenIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            for (int row = 5; row >= 0; row--) {
                if (position == greenIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[greenIndex++]);
            }
            // one deleted...
            for (int row = 0; row < 6; row++) {
                if (position == greenIndex++) {
                    this.row = row;
                    this.col = 7;
                    return;
                }
            }
        } else if (currentPlayer.getColor().equals("Yellow")) {
            // loop for setting..
            int yellowIndex = 0;
            for (int col = 13; col > 8; col--) {
                if (position == yellowIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            for (int row = 9; row < 15; row++) {
                if (position == yellowIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            //
            if (position == yellowIndex++) {
                this.row = 14;
                this.col = 7;
                return;
            }
            // single
            //board[14][7].setCell(currentPlayer.playerRoad[yellowIndex++]);
            for (int row = 14; row > 8; row--) {
                if (position == yellowIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            for (int col = 5; col >= 0; col--) {
                if (position == yellowIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }

            if (position == yellowIndex++) {
                this.row = 7;
                this.col = 0;
                return;
            }
            // single
            //board[7][0].setCell(currentPlayer.playerRoad[yellowIndex++]);
            for (int col = 0; col < 6; col++) {
                if (position == yellowIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            for (int row = 5; row >= 0; row--) {
                if (position == yellowIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            if (position == yellowIndex++) {
                this.row = 0;
                this.col = 7;
                return;
            }
            // single
            //board[0][7].setCell(currentPlayer.playerRoad[yellowIndex++]);
            for (int row = 0; row < 6; row++) {
                if (position == yellowIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            for (int col = 9; col < 15; col++) {
                if (position == yellowIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[yellowIndex++]);
            }
            // one deleted...
            for (int col = 14; col > 8; col--) {
                if (position == yellowIndex++) {
                    this.row = 7;
                    this.col = col;
                    return;
                }

            }
        } else if (currentPlayer.getColor().equals("Blue")) {
            // loop for setting..
            int blueIndex = 0;
            for (int row = 13; row > 8; row--) {
                if (position == blueIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[blueIndex++]);
            }
            for (int col = 5; col >= 0; col--) {
                if (position == blueIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[blueIndex++]);
            }

            if (position == blueIndex++) {
                this.row = 7;
                this.col = 0;
                return;
            }
            // single
            //board[7][0].setCell(currentPlayer.playerRoad[blueIndex++]);
            for (int col = 0; col < 6; col++) {
                if (position == blueIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[blueIndex++]);
            }
            for (int row = 5; row >= 0; row--) {
                if (position == blueIndex++) {
                    this.row = row;
                    this.col = 6;
                    return;
                }
                //board[row][6].setCell(currentPlayer.playerRoad[blueIndex++]);
            }

            if (position == blueIndex++) {
                this.row = 0;
                this.col = 7;
                return;
            }
            // single
            //board[0][7].setCell(currentPlayer.playerRoad[blueIndex++]);
            for (int row = 0; row < 6; row++) {
                if (position == blueIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[blueIndex++]);
            }
            for (int col = 9; col < 15; col++) {
                if (position == blueIndex++) {
                    this.row = 6;
                    this.col = col;
                    return;
                }
                //board[6][col].setCell(currentPlayer.playerRoad[blueIndex++]);
            }

            if (position == blueIndex++) {
                this.row = 7;
                this.col = 14;
                return;
            }
            // single
            //board[7][14].setCell(currentPlayer.playerRoad[blueIndex++]);
            for (int col = 14; col > 8; col--) {
                if (position == blueIndex++) {
                    this.row = 8;
                    this.col = col;
                    return;
                }
                //board[8][col].setCell(currentPlayer.playerRoad[blueIndex++]);
            }
            for (int row = 9; row < 15; row++) {
                if (position == blueIndex++) {
                    this.row = row;
                    this.col = 8;
                    return;
                }
                //board[row][8].setCell(currentPlayer.playerRoad[blueIndex++]);
            }
            // one deleted...
            for (int row = 14; row > 8; row--) {
                if (position == blueIndex++) {
                    this.row = row;
                    this.col = 7;
                    return;
                }
            }
        }

    }

    // method for checking if the token exists at the specific position...
    boolean isTokenExists(int currentPosition, Player player) {
        for (int i = 0; i < 4; i++) {
            if (player.tokensArr[i].position == currentPosition) {
                return true;
            }
        }
        return false;
    }

    public int getPosition() {
        return position;
    }
    // method for reseting the token...
    void resetToken(){
        this.position = 0;
        this.isTokenOpen = false;
        this.row = 0;
        this.col = 0;
    }

    // if isKill true then call this method...
    public void killMethod(LudoBoard board,int row,int col ,char currentSymbol,Player[] players, Player player,LudoGUI gui) {
        // Logic for kill (enemy tokens)
        char symbol = board.board[row][col].getSymbol();
        for(int i=0;i<4;i++){
            // finding the player...
            if(symbol == players[i].playerSymbol){
                // finding the token...
                for(int j=0;j<4;j++){
                    if(players[i].tokensArr[j].row == row && players[i].tokensArr[j].col == col){
                        // now kill the token here...
                        // also placing the space in place of killed token in playerRoad Array...
                        players[i].playerRoad[players[i].tokensArr[j].position].setSymbol(' ');
                        players[i].tokensArr[j].resetToken();
                        players[i].tokensOnBoard--;
                        if(players[i].tokensOnBoard == 0)
                            players[i].isOpen = false;
                        board.updateTokenHomeGUI(players[i],gui,board.redToken,board.greenToken,board.blueToken,
                                board.yellowToken);
                        break;
                    }
                }
            }
        }
        board.board[row][col].setSymbol(currentSymbol);
    }

}