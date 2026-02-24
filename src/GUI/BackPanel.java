package GUI;

import javax.swing.*;
import java.awt.*;

public class BackPanel extends JPanel {
    private Image backroundImg ;

    public BackPanel(String Imgpath){
        this.backroundImg = new ImageIcon(Imgpath).getImage();
        setOpaque(false);
        setLayout(null);
    }
    // override
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g ;
        //g2d.drawImage(backroundImg,0,0,1400,730,this);
        g2d.drawImage(backroundImg,0,0,getWidth(),getHeight(),this);
    }
}