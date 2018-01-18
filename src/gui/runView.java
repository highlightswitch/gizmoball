package gui;

import javax.swing.*;
import java.awt.*;

public class runView {
    JPanel panGame;

    public runView(){

        panGame = new JPanel();
        panGame.setBackground(Color.PINK);

        JPanel panControls = new JPanel();
        JButton stop = new JButton();
        JButton play = new JButton();
        stop.setIcon(new ImageIcon("img/fillPauseSmall.png"));
        play.setIcon(new ImageIcon("img/fillPlaySmall.png"));
        stop.setBorder(null);
        stop.setMargin(new Insets(0, 0, 0, 0));
        stop.setContentAreaFilled(false);
        play.setBorder(null);
        play.setMargin(new Insets(0, 0, 0, 0));
        play.setContentAreaFilled(false);


        panControls.setLayout(new FlowLayout());
        panControls.add(stop);
        panControls.add(play);
        panControls.setOpaque(false);

        JPanel panGrid = new JPanel();
        panGrid.setOpaque(false);

        panGame.setLayout(new BorderLayout());
        panGame.add(panGrid, BorderLayout.CENTER);
        panGame.add(panControls, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panGame;
    }
}
