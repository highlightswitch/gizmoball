package view;

import controller.MainController;

import javax.swing.*;
import java.awt.*;

public class RunView implements GameView {
    JPanel panGame;

    public RunView(MainController c, Board b){

        panGame = new JPanel();
        panGame.setBackground(Color.PINK);

        JPanel panControls = new JPanel();
        JButton stop = new JButton();
        JButton play = new JButton();
        stop.setIcon(new ImageIcon("img/fillPauseSmall.png"));
        play.setIcon(new ImageIcon("img/fillPlaySmall.png"));
        stop.setBorder(null);
        stop.setMargin(new Insets(0, 0, 0, 0));
        stop.addActionListener(c.getActionListener("Button"));
        stop.setContentAreaFilled(false);
        stop.setActionCommand("Stop");

        play.setBorder(null);
        play.setMargin(new Insets(0, 0, 0, 0));
        play.setContentAreaFilled(false);
        play.setActionCommand("Start");
        play.addActionListener(c.getActionListener("Button"));

        //Need to do this so that the key listener works properly.
        play.setFocusable(false);
        stop.setFocusable(false);

        panControls.setLayout(new FlowLayout());
        panControls.add(stop);
        panControls.add(play);
        panControls.setOpaque(false);

        JPanel panGrid = new JPanel();
        panGrid.add(b);
        panGrid.setOpaque(false);

        panGame.setLayout(new BorderLayout());
        panGame.add(panGrid, BorderLayout.CENTER);
        panGame.add(panControls, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panGame;
    }
}
