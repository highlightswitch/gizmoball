package view;

import controller.MainController;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
     private JFrame frMain;
     private JMenuBar top;
     private JMenu mTools;
     private JMenu add;
     private JMenuItem rotate;
     private JMenuItem edit;
     private JMenu settings;
     private JMenuItem delete;
     private Model model;
     private MainController controller;
     private Board board;

    public GameFrame(Model m){
        model = m;
        board = new Board(500, 500, model);
        controller = new MainController(model, this, board);
        frMain = new JFrame("Gizmoball");
        mTools = new JMenu("Tools");
        top = new JMenuBar();

        JMenu mFile = new JMenu("File");
        JMenu mView = new JMenu("View");

        JMenuItem load = new JMenuItem("Load");
        load.setActionCommand("Load");
        load.addActionListener(controller.getActionListener("Menu"));

        JMenuItem save = new JMenuItem("Save");
        save.setActionCommand("Save");
        save.addActionListener(controller.getActionListener("Menu"));

        JMenuItem quit = new JMenuItem("Quit");
        quit.setActionCommand("Quit");
        quit.addActionListener(controller.getActionListener("Menu"));

        JMenuItem run = new JMenuItem("Run View");
        run.setActionCommand("Run");
        run.addActionListener(controller.getActionListener("Menu"));

        JMenuItem build = new JMenuItem("Build View");
        build.setActionCommand("Build");
        build.addActionListener(controller.getActionListener("Menu"));

        add = new JMenu("Add a Gizmo");
        JMenu shape = new JMenu("Add a Shape");
        JMenuItem circle = new JMenuItem("circle");
        JMenuItem square = new JMenuItem("square");
        JMenuItem triangle = new JMenuItem("triangle");
        shape.add(circle);
        shape.add(square);
        shape.add(triangle);

        JMenuItem ball = new JMenuItem("add a ball");
        JMenuItem absorber = new JMenuItem("add an absorber");

        add.add(shape);
        add.add(ball);
        add.add(absorber);

        rotate = new JMenuItem("rotate");
        edit = new JMenuItem("edit");

        settings = new JMenu("settings");
        JMenuItem gravity = new JMenuItem("change gravity");
        JMenuItem friction = new JMenuItem("change friction");
        settings.add(gravity);
        settings.add(friction);

        delete = new JMenuItem("delete");

        mFile.add(load);
        mFile.add(save);
        mFile.add(quit);

        mView.add(run);
        mView.add(build);

        top.add(mFile);
        top.add(mView);

        GameView view = new RunView(controller, board);

        drawFrame(view);
    }

    public void setModel(Model model){
        this.model = model;
    }


    public void drawFrame(GameView g){
        //open running view by default then user can change to build view
        frMain.setContentPane(g.getPanel());
        frMain.setJMenuBar(top);
        frMain.setVisible(true);
        frMain.addKeyListener(controller.getKeyListener());
        frMain.setMinimumSize(new Dimension(550,650));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void extendMenu(){
        mTools.add(add);
        mTools.add(rotate);
        mTools.add(edit);
        mTools.add(delete);
        mTools.addSeparator();
        mTools.add(settings);
        top.add(mTools);
    }

    public void compressMenu(){
        top.remove(mTools);
    }

    public JFrame getFrame() {
        return frMain;
    }

}
