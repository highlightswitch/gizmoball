package gui;

import javax.swing.*;
import java.awt.*;

public class runView {
    JFrame frMain;
    JPanel panGame;
    JMenuBar top;
    JMenu mFile;
    JMenu mView;

    public runView(){
        frMain = new JFrame("Gizmoball");
        top = new JMenuBar();
        mFile = new JMenu("File");
        mView = new JMenu("View");

        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem run = new JMenuItem("Run View");
        JMenuItem build = new JMenuItem("Build View");

        mFile.add(load);
        mFile.add(save);
        mFile.add(quit);

        mView.add(run);
        mView.add(build);

        top.add(mFile);
        top.add(mView);

        panGame = new JPanel();
        panGame.setBackground(Color.PINK);

        JPanel panControls = new JPanel();
        JButton stop = new JButton("Stop");
        JButton play = new JButton("Play");


        panControls.setLayout(new FlowLayout());
        panControls.add(stop);
        panControls.add(play);
        panControls.setOpaque(false);

        JPanel panGrid = new JPanel();
        panGrid.setOpaque(false);

        panGame.setLayout(new BorderLayout());
        panGame.add(panGrid, BorderLayout.CENTER);
        panGame.add(panControls, BorderLayout.SOUTH);

        frMain.setContentPane(panGame);
        frMain.setJMenuBar(top);
        frMain.setVisible(true);
        frMain.setMinimumSize(new Dimension(400,400));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
