package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LudoGUI extends JFrame {
    public JButton rollDiceBtn;
    public JButton[][] slotsArr;
    public JLabel[][] tokensCount;
    public JLabel currentScoreValue;
    public JLabel playerColor;
    public JLabel scoresStr;
    public JPanel blueBackground;
    public BackPanel ludoBoardImg;
    public JLabel redTokensWonNum;
    public JLabel greenTokensWonNum;
    public JLabel yellowTokensWonNum;
    public JLabel blueTokensWonNum;
    public JLabel redTokensWon;
    public JLabel greenTokensWon;
    public JLabel yellowTokensWon;
    public JLabel blueTokensWon;
    // safe slots token count labels...
    public JLabel[][] redSlotLabel;
    public JLabel[][] yellowSlotLabel;
    public JLabel[][] blueSlotLabel;

    // will use tokensCount label as green's...
    //
    public LudoGUI() {


        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackPanel backgroundImg = new BackPanel("Assets/backImg.png");
        //backgroundImg.setBounds(0,0,1400,730);
        backgroundImg.setLayout(null);
        // creating the backround image of ludoboard...
        ludoBoardImg = new BackPanel("Assets/LudoboardWiki.png");
        ludoBoardImg.setLayout(null);
        // setting bounds of ludoboard...
        ludoBoardImg.setBounds(400, 0, 600, 600);
        // here connecting the 2d Button Array with board...
        // first a panel to add on the board...
        JPanel ludoBoardPanel = new JPanel();
        //
        ludoBoardPanel.setLayout(null); // manual placement of buttons...
        ludoBoardPanel.setOpaque(false);
        ludoBoardPanel.setBounds(0, 0, 600, 600); // matches the container's(images size)...
        // now a 2d array of buttons...
        slotsArr = new JButton[15][15];
        // initializing the slots...
        initializeSlots(slotsArr, ludoBoardPanel);
        // now adding ludoBoardPanel to it's container...
        ludoBoardImg.add(ludoBoardPanel);

        // here creating another 2d array for jPanel for storing the tokensOnSlot Counter...
        tokensCount = new JLabel[15][15];
        redSlotLabel = new JLabel[15][15];
        yellowSlotLabel = new JLabel[15][15];
        blueSlotLabel = new JLabel[15][15];
        // inititialize the array...
        initializeTokenLabel(tokensCount, ludoBoardImg);
        // adding ludoBoardContainer into main frame container...
        backgroundImg.add(ludoBoardImg);

        // creating a continer for button
        //BackPanel blueBackground = new BackPanel("Assets/darkPurpleBack.jpg");
        //BackPanel blueBackground = new BackPanel();
        blueBackground = new JPanel();
        blueBackground.setOpaque(false);
        blueBackground.setLayout(null);
        blueBackground.setBounds(413, 588, 675, 150);
        // adding things to this panel...
        // playerName label...
        playerColor = new JLabel("Player ");
        playerColor.setBounds(10, 40, 300, 30);
        // styling the text...
        playerColor.setFont(new Font("Montserrat", Font.BOLD, 18));
        playerColor.setForeground(Color.WHITE);
        blueBackground.add(playerColor);
        JLabel rollDiceAskText = new JLabel("Please roll dice:");
        // setting bounds to jLabel
        rollDiceAskText.setBounds(75, 40, 300, 30);
        // styling the text...
        rollDiceAskText.setFont(new Font("Montserrat", Font.BOLD, 18));
        rollDiceAskText.setForeground(Color.WHITE);
        blueBackground.add(rollDiceAskText);
        // adding a rollDice Button
        rollDiceBtn = new JButton("Roll Dice");
        rollDiceBtn.setBounds(250, 35, 150, 40);
        rollDiceBtn.setFont(new Font("Montserrat", Font.BOLD, 18));
        //rollDiceBtn.setBackground(Color.DARK_GRAY);
        // adding the button to the container...
        blueBackground.add(rollDiceBtn);
        // adding another JLabel for scores...
        JLabel youGotText = new JLabel("You Got:");
        youGotText.setBounds(440, 45, 70, 30);
        youGotText.setFont(new Font("Montserrat", Font.BOLD, 16));
        youGotText.setForeground(Color.white);
        // also the scores array text...
        scoresStr = new JLabel("0 0 0");
        scoresStr.setBounds(440, 70, 70, 30);
        scoresStr.setFont(new Font("Montserrat", Font.BOLD, 16));
        scoresStr.setForeground(Color.white);
        // adding current score label...
        JLabel currentScore = new JLabel("Current Score:");
        currentScore.setBounds(440, 20, 140, 30);
        currentScore.setFont(new Font("Montserrat", Font.BOLD, 16));
        currentScore.setForeground(Color.white);
        // another score box...
        currentScoreValue = new JLabel("0");
        currentScoreValue.setBounds(570, 20, 140, 30);
        currentScoreValue.setFont(new Font("Montserrat", Font.BOLD, 16));
        currentScoreValue.setForeground(Color.white);
        // adding both labels to container...
        blueBackground.add(currentScoreValue);
        blueBackground.add(currentScore);
        blueBackground.add(youGotText);
        blueBackground.add(scoresStr);


        initializeInfoLabels(backgroundImg);

        backgroundImg.add(blueBackground);

        // adding main container to this to frame...
        add(backgroundImg, BorderLayout.CENTER);

        setVisible(true);

        backgroundImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("Clicked at: x=" + e.getX() + ", y=" + e.getY());
            }
        });

    }

    // method for initializing the slots...
    private void initializeSlots(JButton[][] slotsArr, JPanel ludoBoardPanel) {
        // here specifying the size of one button in px...
        int slotSize = 40; // total size of container is 600, so 600/15 for every slot...
        // nested for loops
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // first initialize the button...
                slotsArr[i][j] = new JButton();
                slotsArr[i][j].setBounds(j * 40, i * 40, 40, 40); // i is row, and j is col...
                // so values of row and col multiply 40 with set the initial bounds for each button...
                // setting it to transparent...
                slotsArr[i][j].setOpaque(false);
                slotsArr[i][j].setContentAreaFilled(false); // for inside color
                slotsArr[i][j].setBorderPainted(false); // for border color

                ludoBoardPanel.add(slotsArr[i][j]); // will happen for each and every button inside loop...
            }
        }
    }

    // method for  initializing the tokensCount array...
    private void initializeTokenLabel(JLabel[][] tokensCount, BackPanel ludoBoardImg) {
        int labelSize = 13; // in px...
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // green portion
                tokensCount[i][j] = new JLabel("");
                tokensCount[i][j].setBounds((j * 40) + 29, i * 40, 11, 11);
                //tokensCount[i][j].setOpaque(true); // for transparent , put null (means no self paint)
                tokensCount[i][j].setOpaque(false); // for transparent , put null (means no self paint)
                //tokensCount[i][j].setBackground(Color.GREEN);
                tokensCount[i][j].setBackground(null);
                // adding it to the container...
                ludoBoardImg.add(tokensCount[i][j]);

                // red portion
                redSlotLabel[i][j] = new JLabel("");
                redSlotLabel[i][j].setBounds((j * 40), i * 40, 11, 11);
                //redSlotLabel[i][j].setOpaque(true); // for transparent , put null (means no self paint)
                redSlotLabel[i][j].setOpaque(false); // for transparent , put null (means no self paint)
                //redSlotLabel[i][j].setBackground(Color.RED);
                redSlotLabel[i][j].setBackground(null);
                // adding it to the container...
                ludoBoardImg.add(redSlotLabel[i][j]);

                // yellow portion...
                yellowSlotLabel[i][j] = new JLabel("");
                yellowSlotLabel[i][j].setBounds((j * 40) + 29, (i * 40) + 29, 11, 11);
                //yellowSlotLabel[i][j].setOpaque(true); // for transparent , put null (means no self paint)
                yellowSlotLabel[i][j].setOpaque(false); // for transparent , put null (means no self paint)
                //yellowSlotLabel[i][j].setBackground(Color.yellow);
                yellowSlotLabel[i][j].setBackground(null);
                // adding it to the container...
                ludoBoardImg.add(yellowSlotLabel[i][j]);

                // blue portion...
                blueSlotLabel[i][j] = new JLabel("");
                blueSlotLabel[i][j].setBounds((j * 40), (i * 40) + 29, 11, 11);
                //blueSlotLabel[i][j].setOpaque(true); // for transparent , put null (means no self paint)
                blueSlotLabel[i][j].setOpaque(false); // for transparent , put null (means no self paint)
                //blueSlotLabel[i][j].setBackground(Color.BLUE);
                blueSlotLabel[i][j].setBackground(null);
                // adding it to the container...
                ludoBoardImg.add(blueSlotLabel[i][j]);
            }
        }
    }

    // method for initializing labels...
    private void initializeInfoLabels(BackPanel backGroundImg) {
        // red
        redTokensWon = new JLabel("Red Tokens won:");
        redTokensWonNum = new JLabel("0");
        redTokensWon.setBounds(225, 52, 150, 25);
        redTokensWon.setForeground(Color.white);
        redTokensWon.setFont(new Font("Montserrat", Font.BOLD, 16));
        redTokensWonNum.setBounds(375, 52, 70, 25);
        redTokensWonNum.setForeground(Color.white);
        redTokensWonNum.setFont(new Font("Montserrat", Font.BOLD, 16));
        // green
        greenTokensWon = new JLabel("Green Tokens won:");
        greenTokensWonNum = new JLabel("0");
        greenTokensWon.setBounds(1010, 52, 170, 25);
        greenTokensWon.setForeground(Color.white);
        greenTokensWon.setFont(new Font("Montserrat", Font.BOLD, 16));
        greenTokensWonNum.setBounds(1180, 52, 70, 25);
        greenTokensWonNum.setForeground(Color.white);
        greenTokensWonNum.setFont(new Font("Montserrat", Font.BOLD, 16));
        // yellow
        yellowTokensWon = new JLabel("Yellow Tokens won:");
        yellowTokensWonNum = new JLabel("0");
        yellowTokensWon.setBounds(1010, 474, 170, 25);
        yellowTokensWon.setForeground(Color.white);
        yellowTokensWon.setFont(new Font("Montserrat", Font.BOLD, 16));
        yellowTokensWonNum.setBounds(1180, 474, 70, 25);
        yellowTokensWonNum.setForeground(Color.white);
        yellowTokensWonNum.setFont(new Font("Montserrat", Font.BOLD, 16));
        // blue
        blueTokensWon = new JLabel("Blue Tokens won:");
        blueTokensWonNum = new JLabel("0");
        blueTokensWon.setBounds(210, 474, 150, 25);
        blueTokensWon.setForeground(Color.white);
        blueTokensWon.setFont(new Font("Montserrat", Font.BOLD, 16));
        blueTokensWonNum.setBounds(375, 474, 70, 25);
        blueTokensWonNum.setForeground(Color.white);
        blueTokensWonNum.setFont(new Font("Montserrat", Font.BOLD, 16));

        backGroundImg.add(redTokensWon);
        backGroundImg.add(redTokensWonNum);
        backGroundImg.add(greenTokensWon);
        backGroundImg.add(greenTokensWonNum);
        backGroundImg.add(yellowTokensWon);
        backGroundImg.add(yellowTokensWonNum);
        backGroundImg.add(blueTokensWon);
        backGroundImg.add(blueTokensWonNum);
    }
}