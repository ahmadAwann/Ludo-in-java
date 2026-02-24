package ludoClasses;

import GUI.LudoGUI;

import javax.swing.*;
import java.awt.*;

public class LudoBoard extends GameBoards {
    Cell[][] board;
    ImageIcon redToken;
    ImageIcon blueToken;
    ImageIcon greenToken;
    ImageIcon yellowToken;
    // common safeSlots for all players, hence static...
    static SafeSlots[] safeSlotsArr = new SafeSlots[8];

    //char[][] board;
    // constructor for initializing ludoBoard...
    public LudoBoard() {
        //board = new char[10][10];
        board = new Cell[15][15];
        // icons
        redToken = new ImageIcon("Assets/redToken40.png");
        blueToken = new ImageIcon("Assets/blueToken40.png");
        yellowToken = new ImageIcon("Assets/yellowToken40.png");
        greenToken = new ImageIcon("Assets/greenToken40.png");
        // slots..
    }

    void initializeSlots(LudoGUI gui) {
        gui.slotsArr[0][0].setIcon(redToken);
        gui.slotsArr[0][5].setIcon(redToken);
        gui.slotsArr[5][0].setIcon(redToken);
        gui.slotsArr[5][5].setIcon(redToken);

        gui.slotsArr[0][9].setIcon(greenToken);
        gui.slotsArr[0][14].setIcon(greenToken);
        gui.slotsArr[5][9].setIcon(greenToken);
        gui.slotsArr[5][14].setIcon(greenToken);


        gui.slotsArr[9][0].setIcon(blueToken);
        gui.slotsArr[9][5].setIcon(blueToken);
        gui.slotsArr[14][0].setIcon(blueToken);
        gui.slotsArr[14][5].setIcon(blueToken);

        gui.slotsArr[9][9].setIcon(yellowToken);
        gui.slotsArr[9][14].setIcon(yellowToken);
        gui.slotsArr[14][9].setIcon(yellowToken);
        gui.slotsArr[14][14].setIcon(yellowToken);

    }

    @Override
    //public void populateBoard(Cell[] road) {
    public void populateBoard() {
        // Populate board logic
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if ((((i == 5) && (j <= 5)) || ((i == 5) && (j >= 9))
                        || (i == 9) && (j <= 5) || (i == 9) && (j >= 9))
                        || (((i <= 5) && (j == 5)) || ((i <= 5) && (j == 9))
                        || (i >= 9) && (j == 5) || (i >= 9) && (j == 9))) {
                    board[i][j] = new Cell('x');
                }
                //
                else if (i == 2 && j == 2)
                    board[i][j] = new Cell('R');
                else if (i == 2 && j == 12)
                    board[i][j] = new Cell('G');
                else if (i == 12 && j == 2)
                    board[i][j] = new Cell('B');
                else if (i == 12 && j == 12)
                    board[i][j] = new Cell('Y');
                    // slot safe points...
                else if ((i == 7 && ((j < 6 || j > 8) && (j > 0 && j < 14))) || ((j == 7 && (i < 6 || i > 8) && (i > 0 && i < 14)))) {
                    board[i][j] = new Cell('#');
                }
                // for center
                else if (i == 7 && j == 7)
                    board[i][j] = new Cell('o');
                else if ((i == 6 && (j >= 6 && j <= 8)) || (j == 6 && (i >= 6 && i <= 8))
                        || (i == 8 && (j >= 6 && j <= 8)) || (j == 8 && (i >= 6 && i <= 8))) {
                    board[i][j] = new Cell('-');
                } else if (i == 6 && j == 1)
                    board[i][j] = new Cell('#');
                else if (i == 6 && j == 12)
                    board[i][j] = new Cell('#');
                else if (i == 8 && j == 2)
                    board[i][j] = new Cell('#');
                else if (i == 8 && j == 13)
                    board[i][j] = new Cell('#');
                else if (j == 6 && i == 2)
                    board[i][j] = new Cell('#');
                else if (j == 6 && i == 13)
                    board[i][j] = new Cell('#');
                else if (j == 8 && i == 12)
                    board[i][j] = new Cell('#');
                else if (j == 8 && i == 1)
                    board[i][j] = new Cell('#');
                    // now connecting the player's road with this 2D array...
                else {
                    board[i][j] = new Cell(' ');
                }
            }

        }
    }

    @Override
    public void displayBoard(Player currentPlayer, LudoGUI gui) {
        // here connecting the 2D array with ludoBoard... i-e Overriding specific portion...
        // if Red Player
        if (currentPlayer.getColor().equals("Red")) {
            // loop for setting..
            int redIndex = 0;
            for (int col = 1; col < 6; col++) {
                //System.out.println("value at index " + redIndex + ":" + currentPlayer.playerRoad[redIndex]);
                //if(board[6][col].isCellEmpty(6,col) || currentPlayer.tokensArr[redIndex].getTokenSymbol() == 'r')
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[6][col].setIcon(redToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                    board[6][col].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            for (int row = 5; row >= 0; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[row][6].setIcon(redToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                    board[row][6].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            // single
            if (board[0][7].isCellEmpty(0, 7) || (board[0][7].symbol == 'r')) {
                if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                    gui.slotsArr[0][7].setIcon(redToken);
                else
                    gui.slotsArr[0][7].setIcon(null);
                board[0][7].setCell(currentPlayer.playerRoad[redIndex]);
            }
            redIndex++;
            for (int row = 0; row < 6; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[row][8].setIcon(redToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                    board[row][8].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            for (int col = 9; col < 15; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[6][col].setIcon(redToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                    board[6][col].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            // single
            if (board[7][14].isCellEmpty(7, 14) || (board[7][14].symbol == 'r')) {
                if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                    gui.slotsArr[7][14].setIcon(redToken);
                else
                    gui.slotsArr[7][14].setIcon(null);
                board[7][14].setCell(currentPlayer.playerRoad[redIndex]);
            }
            redIndex++;
            for (int col = 14; col > 8; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[8][col].setIcon(redToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                    board[8][col].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            for (int row = 9; row < 15; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[row][8].setIcon(redToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                    board[row][8].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            // single
            if (board[14][7].isCellEmpty(14, 7) || ((board[14][7].symbol == 'r'))) {
                if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                    gui.slotsArr[14][7].setIcon(redToken);
                else
                    gui.slotsArr[14][7].setIcon(null);
                board[14][7].setCell(currentPlayer.playerRoad[redIndex]);
            }
            redIndex++;
            for (int row = 14; row > 8; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[row][6].setIcon(redToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                    board[row][6].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            for (int col = 5; col >= 0; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'r')) {
                    if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                        gui.slotsArr[8][col].setIcon(redToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                    board[8][col].setCell(currentPlayer.playerRoad[redIndex]);
                }
                redIndex++;
            }
            // one deleted...
            if (board[7][0].isCellEmpty(7, 0) || ((board[7][0].symbol == 'r'))) {
                if (currentPlayer.playerRoad[redIndex].symbol == 'r')
                    gui.slotsArr[7][0].setIcon(redToken);
                else
                    gui.slotsArr[7][0].setIcon(null);
                board[7][0].setCell(currentPlayer.playerRoad[redIndex]);
            }
            redIndex++;
            for (int col = 1; col < 6; col++) {
                // check condition...
                ////System.out.println("|"+currentPlayer.playerRoad[redIndex].symbol+"|" + "index = "+redIndex);
                if (currentPlayer.playerRoad[redIndex].symbol == ' ') {
                    board[7][col].setSymbol('#');
                    gui.slotsArr[7][col].setIcon(null);
                } else if (currentPlayer.playerRoad[redIndex].symbol == 'r') {
                    //if(board[7][col].isCellEmpty(7,col))
                    board[7][col].setCell(currentPlayer.playerRoad[redIndex]);
                    gui.slotsArr[7][col].setIcon(redToken);
                }

                redIndex++;
            }
        } else if (currentPlayer.getColor().equals("Green")) {
            // loop for setting..
            int greenIndex = 0;
            for (int row = 1; row < 6; row++) {
                //System.out.println("value at index " + greenIndex + ":" + currentPlayer.playerRoad[greenIndex]);
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[row][8].setIcon(greenToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                    board[row][8].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            for (int col = 9; col < 15; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[6][col].setIcon(greenToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                    board[6][col].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            // single
            if (board[7][14].isCellEmpty(7, 14) || (board[7][14].symbol == 'g')) {
                if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                    gui.slotsArr[7][14].setIcon(greenToken);
                else
                    gui.slotsArr[7][14].setIcon(null);
                board[7][14].setCell(currentPlayer.playerRoad[greenIndex]);
            }
            greenIndex++;
            for (int col = 14; col > 8; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[8][col].setIcon(greenToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                    board[8][col].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            for (int row = 9; row < 15; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[row][8].setIcon(greenToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                    board[row][8].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            // single
            if (board[14][7].isCellEmpty(14, 7) || (board[14][7].symbol == 'g')) {
                if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                    gui.slotsArr[14][7].setIcon(greenToken);
                else
                    gui.slotsArr[14][7].setIcon(null);
                board[14][7].setCell(currentPlayer.playerRoad[greenIndex]);
            }
            greenIndex++;
            for (int row = 14; row > 8; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[row][6].setIcon(greenToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                    board[row][6].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            for (int col = 5; col >= 0; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[8][col].setIcon(greenToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                    board[8][col].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            // single
            if (board[7][0].isCellEmpty(7, 0) || (board[7][0].symbol == 'g')) {
                if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                    gui.slotsArr[7][0].setIcon(greenToken);
                else
                    gui.slotsArr[7][0].setIcon(null);
                board[7][0].setCell(currentPlayer.playerRoad[greenIndex]);
            }
            greenIndex++;
            for (int col = 0; col < 6; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[6][col].setIcon(greenToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                    board[6][col].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            for (int row = 5; row >= 0; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'g')) {
                    if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                        gui.slotsArr[row][6].setIcon(greenToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                    board[row][6].setCell(currentPlayer.playerRoad[greenIndex]);
                }
                greenIndex++;
            }
            // one deleted...
            if (board[0][7].isCellEmpty(0, 7) || ((board[0][7].symbol == 'g'))) {
                if (currentPlayer.playerRoad[greenIndex].symbol == 'g')
                    gui.slotsArr[0][7].setIcon(greenToken);
                else
                    gui.slotsArr[0][7].setIcon(null);
                board[0][7].setCell(currentPlayer.playerRoad[greenIndex]);
            }
            greenIndex++;
            for (int row = 1; row < 6; row++) {
                if (currentPlayer.playerRoad[greenIndex].symbol == ' ') {
                    board[row][7].setSymbol('#');
                    gui.slotsArr[row][7].setIcon(null);
                } else if (currentPlayer.playerRoad[greenIndex].symbol == 'g') {
                    //if(board[row][7].isCellEmpty(row,7))
                    board[row][7].setCell(currentPlayer.playerRoad[greenIndex]);
                    gui.slotsArr[row][7].setIcon(greenToken);
                }

                greenIndex++;
            }
        } else if (currentPlayer.getColor().equals("Yellow")) {
            // loop for setting..
            int yellowIndex = 0;
            for (int col = 13; col > 8; col--) {
                //System.out.println("value at index " + yellowIndex + ":" + currentPlayer.playerRoad[yellowIndex]);
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'y')) {
                    board[8][col].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[8][col].setIcon(yellowToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                }
                yellowIndex++;
            }
            for (int row = 9; row < 15; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'y')) {
                    board[row][8].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[row][8].setIcon(yellowToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                }
                yellowIndex++;
            }
            // single
            if (board[14][7].isCellEmpty(14, 7) || (board[14][7].symbol == 'y')) {
                board[14][7].setCell(currentPlayer.playerRoad[yellowIndex]);
                if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                    gui.slotsArr[14][7].setIcon(yellowToken);
                else
                    gui.slotsArr[14][7].setIcon(null);
            }
            yellowIndex++;
            for (int row = 14; row > 8; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'y')) {
                    board[row][6].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[row][6].setIcon(yellowToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                }
                yellowIndex++;
            }
            for (int col = 5; col >= 0; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'y')) {
                    board[8][col].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[8][col].setIcon(yellowToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                }
                yellowIndex++;
            }
            // single
            if (board[7][0].isCellEmpty(7, 0) || (board[7][0].symbol == 'y')) {
                board[7][0].setCell(currentPlayer.playerRoad[yellowIndex]);
                if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                    gui.slotsArr[7][0].setIcon(yellowToken);
                else
                    gui.slotsArr[7][0].setIcon(null);
            }
            yellowIndex++;
            for (int col = 0; col < 6; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'y')) {
                    board[6][col].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[6][col].setIcon(yellowToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                }
                yellowIndex++;
            }
            for (int row = 5; row >= 0; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'y')) {
                    board[row][6].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[row][6].setIcon(yellowToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                }
                yellowIndex++;
            }
            // single
            if (board[0][7].isCellEmpty(0, 7) || (board[0][7].symbol == 'y')) {
                board[0][7].setCell(currentPlayer.playerRoad[yellowIndex]);
                if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                    gui.slotsArr[0][7].setIcon(yellowToken);
                else
                    gui.slotsArr[0][7].setIcon(null);
            }
            yellowIndex++;
            for (int row = 0; row < 6; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'y')) {
                    board[row][8].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[row][8].setIcon(yellowToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                }
                yellowIndex++;
            }
            for (int col = 9; col < 15; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'y')) {
                    board[6][col].setCell(currentPlayer.playerRoad[yellowIndex]);
                    if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                        gui.slotsArr[6][col].setIcon(yellowToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                }
                yellowIndex++;
            }
            // one deleted...
            if (board[7][14].isCellEmpty(7, 14) || (board[7][14].symbol == 'y')) {
                board[7][14].setCell(currentPlayer.playerRoad[yellowIndex]);
                if (currentPlayer.playerRoad[yellowIndex].symbol == 'y')
                    gui.slotsArr[7][14].setIcon(yellowToken);
                else
                    gui.slotsArr[7][14].setIcon(null);
            }
            yellowIndex++;
            for (int col = 13; col > 8; col--) {
                if (currentPlayer.playerRoad[yellowIndex].symbol == ' ') {
                    board[7][col].setSymbol('#');
                    gui.slotsArr[7][col].setIcon(null);
                } else if (currentPlayer.playerRoad[yellowIndex].symbol == 'y') {
                    //if(board[7][col].isCellEmpty(7,col))
                    board[7][col].setCell(currentPlayer.playerRoad[yellowIndex]);
                    gui.slotsArr[7][col].setIcon(yellowToken);
                }

                yellowIndex++;

            }

        } else if (currentPlayer.getColor().equals("Blue")) {
            // loop for setting..
            int blueIndex = 0;
            for (int row = 13; row > 8; row--) {
                //System.out.println("value at index " + blueIndex + ":" + currentPlayer.playerRoad[blueIndex]);
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[row][6].setIcon(blueToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                    board[row][6].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            for (int col = 5; col >= 0; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[8][col].setIcon(blueToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                    board[8][col].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            // single
            if (board[7][0].isCellEmpty(7, 0) || (board[7][0].symbol == 'b')) {
                if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                    gui.slotsArr[7][0].setIcon(blueToken);
                else
                    gui.slotsArr[7][0].setIcon(null);
                board[7][0].setCell(currentPlayer.playerRoad[blueIndex]);
            }
            blueIndex++;
            for (int col = 0; col < 6; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[6][col].setIcon(blueToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                    board[6][col].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            for (int row = 5; row >= 0; row--) {
                if (board[row][6].isCellEmpty(row, 6) || (board[row][6].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[row][6].setIcon(blueToken);
                    else
                        gui.slotsArr[row][6].setIcon(null);
                    board[row][6].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            // single
            if (board[0][7].isCellEmpty(0, 7) || (board[0][7].symbol == 'b')) {
                if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                    gui.slotsArr[0][7].setIcon(blueToken);
                else
                    gui.slotsArr[0][7].setIcon(null);
                board[0][7].setCell(currentPlayer.playerRoad[blueIndex]);

            }
            blueIndex++;
            for (int row = 0; row < 6; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[row][8].setIcon(blueToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                    board[row][8].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            for (int col = 9; col < 15; col++) {
                if (board[6][col].isCellEmpty(6, col) || (board[6][col].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[6][col].setIcon(blueToken);
                    else
                        gui.slotsArr[6][col].setIcon(null);
                    board[6][col].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            // single
            if (board[7][14].isCellEmpty(7, 14) || (board[7][14].symbol == 'b')) {
                if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                    gui.slotsArr[7][14].setIcon(blueToken);
                else
                    gui.slotsArr[7][14].setIcon(null);
                board[7][14].setCell(currentPlayer.playerRoad[blueIndex]);

            }
            blueIndex++;
            for (int col = 14; col > 8; col--) {
                if (board[8][col].isCellEmpty(8, col) || (board[8][col].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[8][col].setIcon(blueToken);
                    else
                        gui.slotsArr[8][col].setIcon(null);
                    board[8][col].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            for (int row = 9; row < 15; row++) {
                if (board[row][8].isCellEmpty(row, 8) || (board[row][8].symbol == 'b')) {
                    if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                        gui.slotsArr[row][8].setIcon(blueToken);
                    else
                        gui.slotsArr[row][8].setIcon(null);
                    board[row][8].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
            // one deleted...
            if (board[14][7].isCellEmpty(14, 7) || (board[14][7].symbol == 'b')) {
                if (currentPlayer.playerRoad[blueIndex].symbol == 'b')
                    gui.slotsArr[14][7].setIcon(blueToken);
                else
                    gui.slotsArr[14][7].setIcon(null);
                board[14][7].setCell(currentPlayer.playerRoad[blueIndex]);

            }
            blueIndex++;
            for (int row = 13; row > 8; row--) {
                if (currentPlayer.playerRoad[blueIndex].symbol == ' ') {
                    board[row][7].setSymbol('#');
                    gui.slotsArr[row][7].setIcon(null);
                } else if (currentPlayer.playerRoad[blueIndex].symbol == 'b') {
                    //if(board[row][7].isCellEmpty(row,7))
                    gui.slotsArr[row][7].setIcon(blueToken);
                    board[row][7].setCell(currentPlayer.playerRoad[blueIndex]);
                }

                blueIndex++;
            }
        }
        // display safe slots here....
        settingSafeSlots(gui);
        // Display board logic
        //System.out.println("----------------------------------");
        for (int i = 0; i < 15; i++) {
            //System.out.print((char) ('A' + i) + " |");
            //System.out.print( i +" |");
            for (int j = 0; j < 15; j++) {
                //System.out.print("" + board[i][j]);
                //System.out.print("|");
            }
            //System.out.println("");
            ////System.out.println("----------------------------------");
        }
        //System.out.println("   0 1 2 3 4 5 6 7 8 9 1011121314");
        //
//        for (int i = 0; i < 56; i++) {
//            if (currentPlayer.playerRoad[i].symbol != ' ')
//                //System.out.println("Index :" + i + ":" + currentPlayer.playerRoad[i]);
//        }
    }

    void settingSafeSlots(LudoGUI gui) {
        /* safe slots...
        ((row == 6 && col == 1) || (row == 6 && col == 12) || (row == 8 && col == 2) || (row == 8 && col == 13)
        || (row == 2 && col == 6) || (row == 13 && col == 6) || (row == 1 && col == 8) || (row == 12 && col == 8))
        */

        //board[6][1].setSymbol(Player.safeSlotsArr[0].safeSlotList.get(Player.safeSlotsArr[0].safeSlotList-1)); manual method...
        board[6][1].setSymbol(LudoBoard.safeSlotsArr[0].safeSlotList.getLast()); // in java 21+ getLast() is supported.. for getting item from the last index of list...
        gui.slotsArr[6][1].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[0].safeSlotList.getLast()));
        board[6][12].setSymbol(LudoBoard.safeSlotsArr[1].safeSlotList.getLast());
        gui.slotsArr[6][12].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[1].safeSlotList.getLast()));
        board[8][2].setSymbol(LudoBoard.safeSlotsArr[2].safeSlotList.getLast());
        gui.slotsArr[8][2].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[2].safeSlotList.getLast()));
        board[8][13].setSymbol(LudoBoard.safeSlotsArr[3].safeSlotList.getLast());
        gui.slotsArr[8][13].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[3].safeSlotList.getLast()));
        board[2][6].setSymbol(LudoBoard.safeSlotsArr[4].safeSlotList.getLast());
        gui.slotsArr[2][6].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[4].safeSlotList.getLast()));
        board[13][6].setSymbol(LudoBoard.safeSlotsArr[5].safeSlotList.getLast());
        gui.slotsArr[13][6].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[5].safeSlotList.getLast()));
        board[1][8].setSymbol(LudoBoard.safeSlotsArr[6].safeSlotList.getLast());
        gui.slotsArr[1][8].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[6].safeSlotList.getLast()));
        board[12][8].setSymbol(LudoBoard.safeSlotsArr[7].safeSlotList.getLast());
        gui.slotsArr[12][8].setIcon(tokenSafeSlotsGUI(LudoBoard.safeSlotsArr[7].safeSlotList.getLast()));


        //System.out.println("1:" + LudoBoard.safeSlotsArr[0].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[0].safeSlotList.size());
        //System.out.println("2:" + LudoBoard.safeSlotsArr[1].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[1].safeSlotList.size());
        //System.out.println("3:" + LudoBoard.safeSlotsArr[2].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[2].safeSlotList.size());
        //System.out.println("4:" + LudoBoard.safeSlotsArr[3].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[3].safeSlotList.size());
        //System.out.println("5:" + LudoBoard.safeSlotsArr[4].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[4].safeSlotList.size());
        //System.out.println("6:" + LudoBoard.safeSlotsArr[5].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[5].safeSlotList.size());
        //System.out.println("7:" + LudoBoard.safeSlotsArr[6].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[6].safeSlotList.size());
        //System.out.println("8:" + LudoBoard.safeSlotsArr[7].safeSlotList.getLast() + " size:" + LudoBoard.safeSlotsArr[7].safeSlotList.size());
    }

    // method for adding the tokens on the safe slots using row and col as parameters...
    public void addRemoveFromSafeSlot(int row, int col, char currentChar, boolean addOrRemove) {
        /*
        * ((row == 6 && col == 1) || (row == 6 && col == 12) || (row == 8 && col == 2) || (row == 8 && col == 13)
        || (row == 2 && col == 6) || (row == 13 && col == 6) || (row == 1 && col == 8) || (row == 12 && col == 8))
        * */
        if (row == 6 && col == 1) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[0].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[0].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 6 && col == 12) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[1].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[1].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 8 && col == 2) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[2].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[2].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 8 && col == 13) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[3].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[3].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 2 && col == 6) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[4].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[4].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 13 && col == 6) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[5].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[5].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 1 && col == 8) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[6].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[6].safeSlotList.remove(Character.valueOf(currentChar));
        }
        if (row == 12 && col == 8) {
            if (addOrRemove)
                LudoBoard.safeSlotsArr[7].safeSlotList.add(currentChar);
            else
                LudoBoard.safeSlotsArr[7].safeSlotList.remove(Character.valueOf(currentChar));
        }

    }

    // method for displaying the token images on safeslots...
    private ImageIcon tokenSafeSlotsGUI(char lastChar) {
        if (lastChar == 'r')
            return redToken;
        else if (lastChar == 'g')
            return greenToken;
        else if (lastChar == 'y')
            return yellowToken;
        else if (lastChar == 'b')
            return blueToken;
        else
            return null; // for hash
    }

    // checking the bulk slots after every method, for gui...
    public void setBulkSlotsGUI(Player player, LudoGUI gui, boolean action) {
        int tokensCountOnSlot;
        for (int i = 0, j; i < 4; i++) {

            tokensCountOnSlot = 1;
            if (!player.tokensArr[i].isTokenOpen) {
                continue;
            }
            int row = player.tokensArr[i].row;
            int col = player.tokensArr[i].col;
            if (board[row][col].isSafeCell(row, col))
                continue;
            for (j = 0; j < 4; j++) {
                if (i != j) {
                    if (player.tokensArr[i].getPosition() == player.tokensArr[j].getPosition() && player.tokensArr[j].isTokenOpen) {
                        tokensCountOnSlot++;
                    }
                }
            }
            if (tokensCountOnSlot > 1 && action) {
                //System.out.println("Tokens Count:" + tokensCountOnSlot);
                //System.out.println("Token :" + i + " Positions:" + player.tokensArr[i].row + "," + player.tokensArr[i].col);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setText("" + tokensCountOnSlot);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setOpaque(true);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setBackground(Color.DARK_GRAY);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setForeground(Color.WHITE);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setFont(new Font("Montserrat", Font.PLAIN, 8));
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setHorizontalAlignment(SwingConstants.CENTER);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setVerticalAlignment(SwingConstants.CENTER);
                //gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setBounds(10,2,10,10);
            //} else if (tokensCountOnSlot == 1 && !action) {
            } else if (tokensCountOnSlot == 1 && !action) {
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setOpaque(false);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setText(null);
                gui.tokensCount[player.tokensArr[i].row][player.tokensArr[i].col].setBackground(null);
            }
        }
    }

    // count for lists...
    void setSafeSlotsGUI(LudoGUI gui) {
        int red = 0, green = 0, blue = 0, yellow = 0;
        for (int i = 0; i < safeSlotsArr.length; i++) {
            red = 0;
            green = 0;
            blue = 0;
            yellow = 0;
            for (int j = 0; j < safeSlotsArr[i].safeSlotList.size(); j++) {
                if (safeSlotsArr[i].safeSlotList.get(j) == 'r')
                    red++;
                else if (safeSlotsArr[i].safeSlotList.get(j) == 'g')
                    green++;
                else if (safeSlotsArr[i].safeSlotList.get(j) == 'y')
                    yellow++;
                else if (safeSlotsArr[i].safeSlotList.get(j) == 'b')
                    blue++;
            }

            // setting the safe slot...
            // red...
            if (red >= 1) {
                gui.redSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setText("" + red);
                gui.redSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setOpaque(true);
                gui.redSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setBackground(new Color(139, 0, 0));
                gui.redSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setForeground(Color.WHITE);
            }

            // green...
            if (green >= 1) {
                gui.tokensCount[safeSlotsArr[i].row][safeSlotsArr[i].col].setText("" + green);
                gui.tokensCount[safeSlotsArr[i].row][safeSlotsArr[i].col].setOpaque(true);
                gui.tokensCount[safeSlotsArr[i].row][safeSlotsArr[i].col].setBackground(new Color(0, 100, 0));
                gui.tokensCount[safeSlotsArr[i].row][safeSlotsArr[i].col].setForeground(Color.WHITE);
            }
            // yellow...
            if (yellow >= 1) {
                gui.yellowSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setText("" + yellow);
                gui.yellowSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setOpaque(true);
                gui.yellowSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setBackground(new Color(204, 153, 0));
                gui.yellowSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setForeground(Color.WHITE);
            }
            // blue...
            if (blue >= 1) {
                gui.blueSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setText("" + blue);
                gui.blueSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setOpaque(true);
                gui.blueSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setBackground(new Color(10, 216, 230));
                gui.blueSlotLabel[safeSlotsArr[i].row][safeSlotsArr[i].col].setForeground(Color.WHITE);
            }

        }
    }

    // for clearing the previous labels...
    void clearAllSafeSlotLabels(LudoGUI gui) {
        for (int i = 0; i < safeSlotsArr.length; i++) {
            int row = safeSlotsArr[i].row;
            int col = safeSlotsArr[i].col;

            gui.redSlotLabel[row][col].setText(null);
            gui.redSlotLabel[row][col].setOpaque(false);
            gui.redSlotLabel[row][col].setBackground(null);

            gui.tokensCount[row][col].setText(null);
            gui.tokensCount[row][col].setOpaque(false);
            gui.tokensCount[row][col].setBackground(null);

            gui.yellowSlotLabel[row][col].setText(null);
            gui.yellowSlotLabel[row][col].setOpaque(false);
            gui.yellowSlotLabel[row][col].setBackground(null);

            gui.blueSlotLabel[row][col].setText(null);
            gui.blueSlotLabel[row][col].setOpaque(false);
            gui.blueSlotLabel[row][col].setBackground(null);
        }
    }

    //
    public void updateTokenHomeGUI(Player player, LudoGUI gui,
                                   ImageIcon redToken, ImageIcon greenToken,
                                   ImageIcon blueToken, ImageIcon yellowToken) {
        //System.out.println("Reached the update token gui...");
        int[][] redHomes = {{0, 0}, {0, 5}, {5, 0}, {5, 5}};
        int[][] greenHomes = {{0, 9}, {0, 14}, {5, 9}, {5, 14}};
        int[][] blueHomes = {{9, 0}, {14, 0}, {9, 5}, {14, 5}};
        int[][] yellowHomes = {{9, 9}, {9, 14}, {14, 9}, {14, 14}};

        int[][] homeSlots = null;
        ImageIcon icon = null;

        String color = player.getColor().toLowerCase();

        switch (color) {
            case "red":
                homeSlots = redHomes;
                icon = redToken;
                break;
            case "green":
                homeSlots = greenHomes;
                icon = greenToken;
                break;
            case "blue":
                homeSlots = blueHomes;
                icon = blueToken;
                break;
            case "yellow":
                homeSlots = yellowHomes;
                icon = yellowToken;
                break;
        }

        // Clear previous icons from that player's home...

        for(int i= 0;i<4;i++){
            int row = homeSlots[i][0];
            int col = homeSlots[i][1];
            gui.slotsArr[row][col].setIcon(null);
        }

        // Set token icons for tokens still at home... that are not open...
        for (int i = 0; i < 4; i++) {
            Token token = player.tokensArr[i];
            if (!token.isTokenOpen && !token.isTokenWon) {
                int row = homeSlots[i][0];
                int col = homeSlots[i][1];
                gui.slotsArr[row][col].setIcon(icon);
            }
        }
    }


}
