package gui;

import javax.swing.*;
import java.awt.*;

public class run {
    static JFrame frMain;
    static JMenuBar top;
    static JMenu mFile;
    static JMenu mView;

    public static void main(String[] args){
        //open running view by default then user can change to build view
        runView view = new runView();
        buildView view2 = new buildView(frMain);

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


        frMain = new JFrame("Gizmoball");
        frMain.setContentPane(view2.getPanel());
        frMain.setJMenuBar(top);
        frMain.setVisible(true);
        frMain.setMinimumSize(new Dimension(400,400));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
