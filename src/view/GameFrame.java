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

        JMenuItem circle = new JMenuItem("Circle");
        circle.setActionCommand("Circle");
        circle.addActionListener(controller.getActionListener("Menu"));

        JMenuItem square = new JMenuItem("Square");
        square.setActionCommand("Square");
        square.addActionListener(controller.getActionListener("Menu"));

        JMenuItem triangle = new JMenuItem("Triangle");
        triangle.setActionCommand("Triangle");
        triangle.addActionListener(controller.getActionListener("Menu"));

        shape.add(circle);
        shape.add(square);
        shape.add(triangle);

        JMenuItem ball = new JMenuItem("Add a Ball");
        ball.setActionCommand("Ball");
        ball.addActionListener(controller.getActionListener("Menu"));

        JMenuItem absorber = new JMenuItem("Add an Absorber");
        absorber.setActionCommand("Absorber");
        absorber.addActionListener(controller.getActionListener("Menu"));

        add.add(shape);
        add.add(ball);
        add.add(absorber);

        rotate = new JMenuItem("Rotate");
        rotate.setActionCommand("Rotate");
        rotate.addActionListener(controller.getActionListener("Menu"));

        edit = new JMenuItem("Edit");
        edit.setActionCommand("Edit");
        edit.addActionListener(controller.getActionListener("Menu"));

        settings = new JMenu("Settings");
        JMenuItem gravity = new JMenuItem("Change Gravity");
        gravity.setActionCommand("Gravity");
        gravity.addActionListener(controller.getActionListener("Menu"));

        JMenuItem friction = new JMenuItem("Change Friction");
        friction.setActionCommand("Friction");
        friction.addActionListener(controller.getActionListener("Menu"));

        settings.add(gravity);
        settings.add(friction);

        delete = new JMenuItem("Delete");
        delete.setActionCommand("Delete");
        delete.addActionListener(controller.getActionListener("Menu"));

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
