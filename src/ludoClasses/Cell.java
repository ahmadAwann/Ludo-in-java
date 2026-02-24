package ludoClasses;

import org.w3c.dom.ls.LSOutput;

public class Cell {

    // getters and setter methods for a single cell...
    char symbol;
    int tokensOnSlot;

    public Cell() {

    }

    public Cell(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    //
    public void setCell(Cell cell) {
        this.symbol = cell.symbol;
    }

    @Override
    public String toString() {
        return Character.toString(this.symbol);
    }

    public boolean isSafeCell(int row, int col) {
        // Logic to determine safe cell
        if ((row == 6 && col == 1) || (row == 6 && col == 12) || (row == 8 && col == 2) || (row == 8 && col == 13)
                || (row == 2 && col == 6) || (row == 13 && col == 6) || (row == 1 && col == 8) || (row == 12 && col == 8))
            return true;
        return false;
    }

    // function Overloading
    public boolean isSafeCell(int index) {
        if (index == 0 || index == 8 || index == 13 || index == 21 || index == 26 || index == 34 || index == 39
                || index == 47)
            return true;
        return false;
    }

    public boolean isCellEmpty(int row, int col) {
        if (!isSafeCell(row, col)) {
            return this.symbol == ' ';
        } else if (isSafeCell(row, col)) {
            return true;
        }
        return false;
    }

    // method for checking if the slot is a kill or bulk...
    public boolean isCellKill(Cell[][] board, int row, int col, char currentSymbol) {
        //if(!(board[row][col].isSafeCell(row,col)) && (board[row][col].getSymbol() != currentSymbol))
        if ((board[row][col].getSymbol() != currentSymbol) && (board[row][col].getSymbol() != '#') && (board[row][col].getSymbol() != ' '))
            return true;
        else
            return false;
    }

    public boolean isCellBulk(Cell[][] board, int row, int col, Player currentPlayer) {
        if (board[row][col].getSymbol() == currentPlayer.playerSymbol && board[row][col].getSymbol() != ' ') {
            // here calling the function of calculating tokens on slot if it is a bulk...
            return true;
        }
        return false;
    }

    //    public int tokensOnBulkSlot(Cell[][] board,int row,int col ,Player player) {
//        // Logic for bulk (multiple tokens)
//        // here logic for hashmap as well...
//        // board[row][col].tokensOnSlot++;
//        board[row][col].tokensOnSlot = 1;
//        for(int i=0;i<3;i++){
//            if((player.tokensArr[i].row == player.tokensArr[i+1].row && player.tokensArr[i].col == player.tokensArr[i+1].col
//            && player.tokensArr[i].isTokenOpen && player.tokensArr[i+1].isTokenOpen && player.tokensArr[i].row == row
//            && player.tokensArr[i].col == col)){
//                board[row][col].tokensOnSlot++;
//                //System.out.println("Token "+i+" row,col:"+player.tokensArr[i].row+","+player.tokensArr[i].col);
//                //System.out.println("Token "+(i+1)+" row,col:"+player.tokensArr[i+1].row+","+player.tokensArr[i+1].col);
//            }
//        }
//        // tokensOnSlot will be max 3, so the condition will be if cell . tokensOnSlot > 0 , show it on board as tokenOnSlot + 1
//        //char symbol = board[row][col].getSymbol();
//        return board[row][col].tokensOnSlot;
//    }
    // method for checking if the bulk kill is valid or not...
    public boolean isMoveValid(LudoBoard ludoBoard, int row, int col, Player currentPlayer, int diceRollScore, boolean action, Player[] playersArr) {
        // Logic for kill (enemy tokens)
        // temp row and col values...
        int tempRow = 0, tempCol = 0;
        int temp = 0;// for exchanging values...
        for (int i = 0; i < 4; i++) {
            if (currentPlayer.tokensArr[i].row == row && currentPlayer.tokensArr[i].col == col) {
                //currentPlayer.tokensArr[i].row = tempRow;
                //currentPlayer.tokensArr[i].col = tempCol;
                tempRow = currentPlayer.tokensArr[i].row;
                tempCol = currentPlayer.tokensArr[i].col;
                // now finding the row and col slot of token on board after movement...
                if (action) {
                    // for bulk
                    currentPlayer.tokensArr[i].setRowColumn(currentPlayer.tokensArr[i].getPosition() + (diceRollScore / ludoBoard.board[row][col].tokensOnSlot),
                            currentPlayer, ludoBoard);
                } else {
                    // for single...
                    currentPlayer.tokensArr[i].setRowColumn(currentPlayer.tokensArr[i].getPosition() + diceRollScore, currentPlayer, ludoBoard);
                }
                // now that we know the resultant slot, now count the number of tokens on that slot...
                // converting the row and col back
                temp = currentPlayer.tokensArr[i].row;
                currentPlayer.tokensArr[i].row = tempRow;
                tempRow = temp;
                // now for column...
                temp = currentPlayer.tokensArr[i].col;
                currentPlayer.tokensArr[i].col = tempCol;
                tempCol = temp;
                //System.out.println("");
                // calculating the tokens on resultant slot and inital slot...
                int resSlotTokens = ludoBoard.board[tempRow][tempCol].tokensOnResSlot(ludoBoard.board, tempRow, tempCol, currentPlayer, playersArr);
                int iniSlotTokens = ludoBoard.board[currentPlayer.tokensArr[i].row][currentPlayer.tokensArr[i].col].tokensOnBulkSlot(ludoBoard.board, currentPlayer.tokensArr[i].row, currentPlayer.tokensArr[i].col, currentPlayer, playersArr);
                //System.out.println("Ini tok = " + iniSlotTokens);
                //System.out.println("res tok = " + resSlotTokens);
                //if((ludoBoard.board[tempRow][tempCol].tokensOnSlot == ludoBoard.board[currentPlayer.tokensArr[i].row][currentPlayer.tokensArr[i].col].tokensOnSlot)){
                if ((iniSlotTokens == resSlotTokens)) {
                    //System.out.println("First Condition");
                    return true;
                } else if (ludoBoard.board[tempRow][tempCol].isSafeCell(tempRow, tempCol)) {
                    //else if(ludoBoard.board[tempRow][tempCol].tokensOnSlot == 0){
                    //System.out.println("Tokens or initial slot:" + ludoBoard.board[currentPlayer.tokensArr[i].row][currentPlayer.tokensArr[i].col].tokensOnSlot);
                    //System.out.println("Tokens or resultant slot:" + ludoBoard.board[tempRow][tempCol].tokensOnSlot);
                    return true;
                }
                else if(ludoBoard.board[tempRow][tempCol].isCellEmpty(tempRow, tempCol) || ludoBoard.board[tempRow][tempCol].tokensOnSlot == 0){
                    //System.out.println("Third condition");
                    return true;
                }
                else {
                    return false;
                }

            }
        }
        return false;
    }

    // tokens on bulk slot method...
    public int tokensOnResSlot(Cell[][] board, int row, int col, Player currentPlayer, Player[] playersArr) {
        char symbol = board[row][col].getSymbol();
        board[row][col].tokensOnSlot = 0;
        for (int i = 0; i < 4; i++) {
            if (symbol == playersArr[i].playerSymbol && symbol != currentPlayer.playerSymbol) {
                for (int j = 0; j < 4; j++) {
                    if (playersArr[i].tokensArr[j].row == row && playersArr[i].tokensArr[j].col == col) {
                        board[row][col].tokensOnSlot++;
                    }
                }
                break;
            }
        }

        return board[row][col].tokensOnSlot;
    }

    public int tokensOnBulkSlot(Cell[][] board, int row, int col, Player currentPlayer, Player[] playersArr) {
        board[row][col].tokensOnSlot = 0;
        for (int j = 0; j < 4; j++) {
            if (currentPlayer.tokensArr[j].row == row && currentPlayer.tokensArr[j].col == col) {
                board[row][col].tokensOnSlot++;
            }
        }
        //System.out.println("Tokens on bulk slot are:"+board[row][col].tokensOnSlot+" at "+row+","+col);
        return board[row][col].tokensOnSlot;
    }
}