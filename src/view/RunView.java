package view;

import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RunView implements GameView {

    private JFrame frame;
    private JPanel panGame;
    private JPanel panBoard;
    private MainController controller;
    private ArrayList<JButton> buttons = new ArrayList<>();

    RunView(JFrame frame, MainController controller, Board board){
        this.controller = controller;
        this.frame = frame;

        panGame = new JPanel();
        panGame.setBackground(Color.PINK);

        JPanel panControls = new JPanel();
        JButton stop = new JButton();
        JButton play = new JButton();
        stop.setIcon(new ImageIcon(getClass().getResource("/Images/fillPauseSmall.png")));
        play.setIcon(new ImageIcon(getClass().getResource("/Images/fillPlaySmall.png")));
        stop.setBorder(null);
        stop.setMargin(new Insets(0, 0, 0, 0));
        buttons.add(stop);

        stop.setContentAreaFilled(false);
        stop.setActionCommand("Stop");

        play.setBorder(null);
        play.setMargin(new Insets(0, 0, 0, 0));
        play.setContentAreaFilled(false);
        play.setActionCommand("Start");
        buttons.add(play);

        //Need to do this so that the key listener works properly.
        play.setFocusable(false);
        stop.setFocusable(false);

        panControls.setLayout(new FlowLayout());
        panControls.add(stop);
        panControls.add(play);
        panControls.setOpaque(false);

        JPanel panGrid = new JPanel();
        panGrid.add(board);
        panGrid.setOpaque(false);

        panBoard = panGrid;

        panGame.setLayout(new BorderLayout());
        panGame.add(panGrid, BorderLayout.CENTER);
        panGame.add(panControls, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panGame;
    }

    @Override
    public JPanel getBoard() {
        return panBoard;
    }

    public void setAllButtonListeners(){
        for(JButton b: buttons){
            b.addActionListener(controller.getActionListener(frame, "Button"));
        }
    }

    @Override
    public void setMessage(String m) {
        //
    }
}
