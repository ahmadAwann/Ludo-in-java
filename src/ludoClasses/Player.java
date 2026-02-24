package ludoClasses;

import GUI.LudoGUI;

import java.util.Scanner;

public class Player {
    private String playerName;
    private String color;
    private int score;
    int tokensOnBoard;
    int tokensWon;
    boolean isOpen;
    boolean isTokenWon;
    boolean isTokenKilled;
    public boolean isPlayerWon;
    char playerSymbol;
    // list or array of tokens here...
    Token[] tokensArr = new Token[4];
    public Cell[] playerRoad = new Cell[56];

    public Player(String color){
        this.color = color;
        initializePlayerRoadCells();
        // initialize tokens here
        for(int i=0;i<4;i++){
            //tokensArr[i].initializeToken(tokensArr[i]);
            tokensArr[i] = new Token();
        }
        // setting the players tokens here
        //setTokenSymbol();

    }
    // dummy road...
    //public char[] road = new char[10];
    //public Cell[] road = new Cell[10];
    public void playerTurn() {
        // Player turn logic
    }
    // getters and setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // cell initialize method...
    private void initializePlayerRoadCells(){
//        int index1 =0,index2 = 0;
//        for(int i = 0 ; i < 56 ; i++){
//            if(i < 26)
//                playerRoad[i] = new Cell((char)(i+65));
//            else if(i < 52)
//                playerRoad[i] = new Cell((char)('A'+index1++));
//            else
//                playerRoad[i] = new Cell((char)('A'+index2++));
//        }
        for(int i = 0 ;i<56;i++){
            playerRoad[i] = new Cell();
            //if(!playerRoad[i].isSafeCell(i)){
                //if(i < 51)
                    playerRoad[i].setSymbol(' ');
//                else
//                    playerRoad[i].setSymbol(' ');
            //}
            //else{
                //playerRoad[i].setSymbol('#');
            //}
            // have to change this...
        }
    }
    // set Token Symbol...
    public void setTokenSymbol(){
        for(int i = 0;i<4;i++){
            if(this.color.equals("Red"))
                tokensArr[i].setTokenSymbol('r');
            else if(this.color.equals("Green"))
                tokensArr[i].setTokenSymbol('g');
            else if(this.color.equals("Yellow"))
                tokensArr[i].setTokenSymbol('y');
            else if(this.color.equals("Blue"))
                tokensArr[i].setTokenSymbol('b');
        }
    }
    // move function for player...
    public boolean isSlotValid(int row,int col){
        for(int i = 0;i<4;i++){
            //System.out.println("Token score:"+tokensArr[i].row+","+tokensArr[i].col+","+tokensArr[i].getPosition());
            if(tokensArr[i].row == row && tokensArr[i].col == col) {
                return true;
            }
        }
        return false;
    }
    // if valid move then return this token...
    // by using this token, we can set the position of token on playerRoad Array...
    public Token tokenForMove(int row,int col){
        for(int i = 0;i<4;i++){
            if(tokensArr[i].row == row && tokensArr[i].col == col) {
                //System.out.println("token returned...");
                return tokensArr[i];
            }
        }
        return null; // null will never be returned because we will check the validity of a move... and if found false...
        // we will not be in this method...
    }
//    //
//    void makeMoves(int diceRollScore, Player player, LudoBoard board, Scanner in,Player[] playersArr,LudoGUI gui){
//        if(player.tokensOnBoard == 1 || player.tokensWon == 3){
//            for(int i=0;i<4;i++){
//                if(player.tokensArr[i].isTokenOpen){
//                    if(player.tokensArr[i].getPosition()+diceRollScore<=56){
//                        player.tokensArr[i].move(diceRollScore,player,board,playersArr,gui);
//                        break;
//                    }
//                    else{
//                        //System.out.println("Cannot move token, Turn Discarded!!");
//                    }
//
//                }
//            }
//
//        }
//        else{
//            //Scanner in = new Scanner(System.in);
//            while(true){
//                String input;
//                //String input = "";
//                do{
//                    // set the lintener to this point...
//                    //System.out.println("Enter the indexes of The token you want to move (eg A6):");
//                    input = in.nextLine();
//                }while(input.isEmpty()); // for if  direct \n entry...
//                int rowIndex = input.charAt(0); // for row , no matter if character or integer datatype
//                String[] parts = input.split(Character.toString(input.charAt(0)));
//                int colIndex = Integer.parseInt(parts[1]); // for column // gave error on this line...
//                // mapping the Alphabetic row index with numerical...
//                //System.out.println("Before\nRow index :"+rowIndex+",Col index:"+colIndex);
//                for(int i=0;i<15;i++){
//                    if(rowIndex-65 == i){
//                        rowIndex = i;
//                        break;
//                    }
//                }
//                //System.out.println("After\nRow index :"+rowIndex+",Col index:"+colIndex);
//                if(player.isSlotValid(rowIndex,colIndex)){
//                    if(board.board[rowIndex][colIndex].tokensOnBulkSlot(board.board,rowIndex,colIndex,player,playersArr) > 1
//                     && (diceRollScore%board.board[rowIndex][colIndex].tokensOnSlot == 0 &&
//                            (player.tokenForMove(rowIndex,colIndex).getPosition() + diceRollScore/board.board[rowIndex][colIndex].tokensOnSlot <=56)
//                   // && board.board[rowIndex][colIndex].isBulkMoveValid(board,rowIndex,colIndex,player,diceRollScore))){
//                    )){
//                        // here last condition added is new if got any error...
//                        //System.out.println("Score % tokens:"+diceRollScore%board.board[rowIndex][colIndex].tokensOnSlot+
//                                "Tokens on Slot:"+board.board[rowIndex][colIndex].tokensOnSlot);
//                        //System.out.println("Before Bulk Method\nScore / tokensOnSlot :"+board.board[rowIndex][colIndex].tokensOnSlot);
//                        // here check if the entered tokens are kill or not...
//
//                        Scanner sc = new Scanner(System.in);
//                        int num;
//                            do{
//                                //System.out.println("Press 1 if you want to move one token and 2 for moving all Tokens:");
//                                num = sc.nextInt();
//                                if(num !=2 && num!=1){
//                                    //System.out.println("Invalid Input!");
//                                }
//                            }while(num!=1 && num !=2);
//                            sc.nextLine();// for last \n on buffer...
//                            // moving the bulk...
//                            if(num == 1){
//                                if(player.tokenForMove(rowIndex,colIndex).getPosition() + diceRollScore <= 56){
//                                    player.tokenForMove(rowIndex,colIndex).move(diceRollScore,player,board,playersArr,gui);
//                                    break; // or return;
//                                }
//                                else if(player.canAnotherTokenMove(diceRollScore)){
//                                    //System.out.println("Can't Move this Token, Move Another...!");
//                                }
//                                else{
//                                    //System.out.println("Can't move any token!! Turn Discarded!");
//                                    break; // or return;
//                                }
//                            }
//                            else {
//                                bulkMove(diceRollScore,board,rowIndex,colIndex,player,playersArr,gui);
//                                break;
//
//                            }
//
//                    }
//                    else{
//                        // can be done using the tokenForMove Function...
//                        if(player.tokenForMove(rowIndex,colIndex).getPosition() + diceRollScore <= 56){
//                            player.tokenForMove(rowIndex,colIndex).move(diceRollScore,player,board,playersArr,gui);
//                            break; // or return;
//                        }
//                        else if(player.canAnotherTokenMove(diceRollScore)){
//                            //System.out.println("Can't Move this Token, Move Another...!");
//                        }
//                        else{
//                            //System.out.println("Can't move any token!! Turn Discarded!");
//                            break; // or return;
//                        }
//                    }
//
//
//                }
//                else{
//                    //System.out.println("Please Enter a Valid Slot!!");
//                }
//            }
//        }
//    }
    // method for checking if other player's tokens can move on this diceRollScore...
    boolean canAnotherTokenMove(int score) {
        for (int i = 0; i < 4; i++) {
            if(this.tokensArr[i].isTokenOpen && this.tokensArr[i].getPosition() + score <=56){
                //System.out.println("Six Request score :" + score);
                return true;
            }
        }
        return false;
    }

    public void bulkMove(int diceRollScore, LudoBoard ludoboard, int row, int col , Player currentPlayer, Player[] players, LudoGUI gui){
        //int count = 0;
        //
        //int repeatedPos = 0;
        for(int i =0 ;i<4;i++){
            if(currentPlayer.tokensArr[i].row == row && currentPlayer.tokensArr[i].col == col){
                //repeatedPos = currentPlayer.tokensArr[i].getPosition();
                currentPlayer.tokenForMove(row,col).move(diceRollScore/ludoboard.board[row][col].tokensOnSlot,currentPlayer,ludoboard,players,gui);
                //break;
                //System.out.println("in Bulk Method\nScore / tokensOnSlot :"+ludoboard.board[row][col].tokensOnSlot);
            }
        }

    }

}
