package gui;

import javax.swing.*;
import java.awt.*;

public class run {
    static JFrame frMain;
    static JMenuBar top;
    static JMenu mFile;
    static JMenu mView;

    public static void main(String[] args){

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

        gameView view = new runView();

        drawFrame(view);

    }

    public static void drawFrame(gameView g){
        //open running view by default then user can change to build view
        frMain = new JFrame("Gizmoball");
        frMain.setContentPane(g.getPanel());
        frMain.setJMenuBar(top);
        frMain.setVisible(true);
        frMain.setMinimumSize(new Dimension(400,400));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void extendMenu(){
        JMenu tools = new JMenu("tools");

        JMenu add = new JMenu("add a gizmo");
        JMenuItem shape = new JMenuItem("add a shape");
        JMenuItem ball = new JMenuItem("add a ball");
        JMenuItem absorber = new JMenuItem("add an absorber");

        add.add(shape);
        add.add(ball);
        add.add(absorber);

        JMenuItem rotate = new JMenuItem("rotate");
        JMenuItem edit = new JMenuItem("edit");

        JMenu settings = new JMenu("settings");
        JMenuItem gravity = new JMenuItem("change gravity");
        JMenuItem friction = new JMenuItem("change friction");
        settings.add(gravity);
        settings.add(friction);

        JMenuItem delete = new JMenuItem("delete");

        tools.add(add);
        tools.add(rotate);
        tools.add(edit);
        tools.add(delete);
        tools.addSeparator();
        tools.add(settings);

        top.add(tools);

    }
}
